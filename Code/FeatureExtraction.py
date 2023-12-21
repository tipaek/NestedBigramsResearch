import pandas as pd
import numpy as np

# for files
import os
import chardet
from pathlib import Path

# for bigrams and divergence
from kmedoids import KMedoids

from Helpers import get_bigrams, get_features, bigram_freq, bigram_to_str, calculate_kl_divergence, \
    get_bigrams_in_range, get_nodes_in_range, encode_nodes_with_BERT, encode_nodes_with_BERT_weighted
from javalang.parse import parse
from javalang.tree import *

# for clustering
from scipy.cluster.hierarchy import dendrogram, linkage
from sklearn.cluster import AgglomerativeClustering
from sklearn.mixture import GaussianMixture
from sklearn.cluster import KMeans
#from sklearn_extra.cluster import KMedoids

from scipy.stats import entropy

from multiprocessing import Pool

from transformers import BertTokenizer, BertModel, DistilBertTokenizer, DistilBertModel
import torch
def readFilesWithBERT(file_path, groupLength, eps=1e-9):
    data = []
    bert_columns = set()
    code_group_lengths = []
    tokenizer = DistilBertTokenizer.from_pretrained('distilbert-base-uncased')
    model = DistilBertModel.from_pretrained('distilbert-base-uncased')

    def process_file(file_name, file_path):
        try:
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    nodes = get_nodes_in_range(tree, start_line, end_line)
                    node_embeddings = encode_nodes_with_BERT_weighted(nodes, tokenizer, model)
                    for node, emb in node_embeddings:
                        node_str = str(node)
                        bert_columns.add(node_str)
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name, file_path] + features + [
                        emb.mean() if node_str == str(node) else 0 for node_str in bert_columns])
        except Exception as e:
            raise Exception(f'An error occurred while processing file {file_name}: {e}')
            #print(f'An error occurred while processing file {file_name}: {e}')

    def process_directory(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        print(file_name)
                        process_file(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, processed_dirs, processed_files)

    process_directory(file_path)
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores', 'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(bert_columns)
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)

    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    return df





# returns a dataframe that groups the input files in file path into groupLength lines of java code. uses methods above for getting features.
def readFiles(file_path, groupLength):
    data = []
    nested_bigram_columns = set()
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if(len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i+groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    start_line = i*groupLength
                    end_line = (i+1) * groupLength -1
                    row_range = f'{start_line}-{end_line}'
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in nested_bigram_columns])
    columns = ['Row Range', 'File Name', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores', 'Empty Lines', 'Mean Line Length', 'Mean Comment Length'] + list(map(bigram_to_str,nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)
    return df, list(nested_bigram_columns)
#'Mean Line Length',

#reads files but includes KL Divergence. This method assumes that 'Written' is calculated during though, which could be improved upon later.
def readFilesWithEntropy(file_path, groupLength, eps=1e-9):
    data = []
    nested_bigram_columns = set()
    code_group_lengths = []
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if(len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i+groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    start_line = i*groupLength
                    end_line = (i+1) * groupLength -1
                    row_range = f'{start_line}-{end_line}'
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name] + features + [bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in nested_bigram_columns])
    columns = ['Row Range', 'File Name', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores', 'Empty Lines', 'Mean Line Length', 'Mean Comment Length'] + list(map(bigram_to_str,nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)

    #lazy binary labeling
    df['Written'] = 0

    df.loc[df['File Name'] == 'java1.java', 'Written'] = 1
    df.loc[df['File Name'] == 'fault.java', 'Written'] = 1
    df.loc[df['File Name'] == 'tic-tac-toe.java', 'Written'] = 1

    # Specify the columns to ignore
    ignore_cols = ['Row Range', 'Written', 'File Name']

    # Calculate the normalized KL divergence using a helper function
    normalized_kl_divergence_0, normalized_kl_divergence_1 = calculate_kl_divergence(df, ignore_cols, code_group_lengths, eps)

    # Add the normalized KL divergence as new columns to the dataframe
    df['KL Divergence 0'] = normalized_kl_divergence_0
    df['KL Divergence 1'] = normalized_kl_divergence_1

    return df, list(nested_bigram_columns)

def readFilesWithEntropy2(file_path, groupLength, eps=1e-9):
    data = []
    nested_bigram_columns = set()
    code_group_lengths = []
    def process_file(file_name, file_path):
        try:
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name, file_path] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])
        except Exception as e:
            print(f'An error occurred while processing file {file_name}: {e}')

    def process_directory(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        print(file_name)
                        process_file(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, processed_dirs, processed_files)

    process_directory(file_path)
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores', 'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(map(bigram_to_str, nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)

    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    # Specify the columns to ignore
    ignore_cols = ['Row Range', 'Written', 'File Name', 'File Path']

    # Calculate the normalized KL divergence using a helper function
    normalized_kl_divergence_0, normalized_kl_divergence_1 = calculate_kl_divergence(df, ignore_cols,
                                                                                     code_group_lengths, eps)
    # Add the normalized KL divergence as new columns to the dataframe
    df['KL Divergence 0'] = normalized_kl_divergence_0
    df['KL Divergence 1'] = normalized_kl_divergence_1

    return df, list(nested_bigram_columns)

# made it easier for labelling files and organized code parts better
def readFilesWithEntropy3(file_path, groupLength, eps=1e-9):
    data = []
    nested_bigram_columns = set()
    code_group_lengths = []

    def process_file(file_name, file_path):
        try:
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                tree = parse(code)
                lines = code.count('\n')
                all_bigrams = get_bigrams(tree, 0, lines)
                num_groups = lines // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    bigrams = get_bigrams_in_range(all_bigrams, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append(
                        [row_range, file_name, file_path] + features + [
                            bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group)
                            for bigram in nested_bigram_columns])
        except Exception as e:
            print(f'An error occurred while processing file {file_name}: {e}')

    def process_directory(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        print(file_name)
                        process_file(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, processed_dirs, processed_files)

    process_directory(file_path)

    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores', 'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(map(bigram_to_str, nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)

    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)


    # Specify the columns to ignore
    ignore_cols = ['Row Range', 'Written', 'File Name', 'File Path']

    # Calculate the normalized KL divergence using a helper function
    normalized_kl_divergence_0, normalized_kl_divergence_1 = calculate_kl_divergence(df, ignore_cols,
                                                                                     code_group_lengths, eps)
    # Add the normalized KL divergence as new columns to the dataframe
    df['KL Divergence 0'] = normalized_kl_divergence_0
    df['KL Divergence 1'] = normalized_kl_divergence_1

    return df, list(nested_bigram_columns)

# same as 2 except uses multiprocessing
def readFilesWithEntropy4(file_path, groupLength, eps=1e-9):
    data = []
    nested_bigram_columns = set()
    code_group_lengths = []

    # Create a pool of worker processes
    pool = Pool()

    # Define a function to perform feature extraction for a single file
    def extract_features(file_name, file_path):
        try:
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name, file_path] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])
        except Exception as e:
            print(f'An error occurred while processing file {file_name}: {e}')

    def process_directory(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            # Use the pool of worker processes to perform feature extraction in parallel
            pool.starmap(extract_features, [(file_name, os.path.join(root, file_name)) for file_name in files if file_name.endswith('.java') and os.path.realpath(os.path.join(root, file_name)) not in processed_files])
            processed_files.update([os.path.realpath(os.path.join(root, file_name)) for file_name in files])
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, processed_dirs, processed_files)

    process_directory(file_path)
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores', 'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(map(bigram_to_str, nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)

    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    # Specify the columns to ignore
    ignore_cols = ['Row Range', 'Written', 'File Name', 'File Path']

    # Calculate the normalized KL divergence using a helper function
    normalized_kl_divergence_0, normalized_kl_divergence_1 = calculate_kl_divergence(df, ignore_cols,
                                                                                     code_group_lengths, eps)
    # Add the normalized KL divergence as new columns to the dataframe
    df['KL Divergence 0'] = normalized_kl_divergence_0
    df['KL Divergence 1'] = normalized_kl_divergence_1

    return df, list(nested_bigram_columns)


# same as readFiles except with additional list_of_bigrams parameter which specifies which bigrams to look for
# this is for testing so the dataframe dimensions match such that it can be used on new files that have different bigrams
def readFilesWithBigrams(file_path, groupLength, list_of_bigrams):
    data = []
    nested_bigram_columns = set(list_of_bigrams)
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if(len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i+groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    row_range = f'{i*groupLength}-{(i+1)*groupLength-1}'
                    start_line, end_line = map(int, row_range.split('-'))
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in nested_bigram_columns])
    columns = ['Row Range', 'File Name', 'Whitespace Ratio', 'Statement Words','Tabs', 'Underscores', 'Empty Lines', 'Mean Line Length'] + list(map(bigram_to_str,nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)
    return df, columns

# *****************************************************************
# clustering methods
def readFilesWithHierarchical(file_path, groupLength, n_clusters):
    data = []
    nested_bigram_columns = set()
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    row_range = f'{i * groupLength}-{(i + 1) * groupLength - 1}'
                    start_line, end_line = map(int, row_range.split('-'))
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])


    feature_data = np.nan_to_num(np.array(pd.DataFrame(data).drop([0, 1], axis=1)), nan=0)

    # PCA and clustering
    #pca = PCA(n_components=30)
    #pca.fit(feature_data)
    #feature_data = pca.transform(feature_data)

    # Hierarchical clustering
    Z = linkage(feature_data, 'ward')
    cluster = AgglomerativeClustering(n_clusters=n_clusters, affinity='euclidean', linkage='ward')
    closest_centroids = cluster.fit_predict(feature_data)

    # Make dataframe
    columns = ['Row Range', 'File Name', 'Centroid', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines', 'Mean Line Length'] + list(map(bigram_to_str, nested_bigram_columns))

    df_data = []
    for i, row in enumerate(data):
        df_data.append(row[:2] + [closest_centroids[i]] + row[2:])

    df = pd.DataFrame(df_data, columns=columns)

    df.fillna(0, inplace=True)

    return df, list(map(bigram_to_str, nested_bigram_columns))

def readFilesWithDistribution(file_path, groupLength, n_clusters):
    data = []
    nested_bigram_columns = set()
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    row_range = f'{i * groupLength}-{(i + 1) * groupLength - 1}'
                    start_line, end_line = map(int, row_range.split('-'))
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])

    feature_data = np.nan_to_num(np.array(pd.DataFrame(data).drop([0, 1], axis=1)), nan=0)

    # Distribution-based clustering
    gmm = GaussianMixture(n_components=n_clusters)
    closest_centroids = gmm.fit_predict(feature_data)

    # Make dataframe
    columns = ['Row Range', 'File Name', 'Centroid', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines', 'Mean Line Length'] + list(map(bigram_to_str, nested_bigram_columns))

    df_data = []
    for i, row in enumerate(data):
        df_data.append(row[:2] + [closest_centroids[i]] + row[2:])

    df = pd.DataFrame(df_data, columns=columns)

    df.fillna(0, inplace=True)

    return df, list(map(bigram_to_str, nested_bigram_columns))

def readFilesWithKMeans(file_path, groupLength, n_clusters):
    data = []
    nested_bigram_columns = set()
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    row_range = f'{i * groupLength}-{(i + 1) * groupLength - 1}'
                    start_line, end_line = map(int, row_range.split('-'))
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])

    feature_data = np.nan_to_num(np.array(pd.DataFrame(data).drop([0, 1], axis=1)), nan=0)

    # KMeans clustering
    kmeans = KMeans(n_clusters=n_clusters)
    closest_centroids = kmeans.fit_predict(feature_data)

    # Make dataframe
    columns = ['Row Range', 'File Name', 'Centroid', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines', 'Mean Line Length'] + list(map(bigram_to_str, nested_bigram_columns))

    df_data = []
    for i, row in enumerate(data):
        df_data.append(row[:2] + [closest_centroids[i]] + row[2:])

    df = pd.DataFrame(df_data, columns=columns)

    df.fillna(0, inplace=True)

    return df, list(map(bigram_to_str, nested_bigram_columns))

def readFilesWithKMedoids(file_path, groupLength, n_clusters):
    data = []
    nested_bigram_columns = set()
    for file_name in os.listdir(file_path):
        if file_name.endswith('.java'):
            with open(os.path.join(file_path, file_name), 'r') as f:
                code = f.read()
                tree = parse(code)
                num_groups = code.count('\n') // groupLength
                if (len(code) % groupLength != 0): num_groups += 1
                code_groups = [code[i:i + groupLength] for i in range(num_groups)]
                for i, code_group in enumerate(code_groups):
                    row_range = f'{i * groupLength}-{(i + 1) * groupLength - 1}'
                    start_line, end_line = map(int, row_range.split('-'))
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])

    feature_data = np.nan_to_num(np.array(pd.DataFrame(data).drop([0, 1], axis=1)), nan=0)

    # KMedoids Clustering
    kmedoids = KMedoids(n_clusters=n_clusters, metric='hamming')
    closest_centroids = kmedoids.fit_predict(feature_data)

    # Make dataframe
    columns = ['Row Range', 'File Name', 'Centroid', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines', 'Mean Line Length'] + list(map(bigram_to_str, nested_bigram_columns))

    df_data = []
    for i, row in enumerate(data):
        df_data.append(row[:2] + [closest_centroids[i]] + row[2:])

    df = pd.DataFrame(df_data, columns=columns)

    df.fillna(0, inplace=True)

    return df, list(map(bigram_to_str, nested_bigram_columns))





