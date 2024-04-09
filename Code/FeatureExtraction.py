import pandas as pd
import numpy as np

# for files
import os
import chardet
from pathlib import Path

import sys
#sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch')
sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch')
# for bigrams and divergence
from kmedoids import KMedoids

from Code.Helpers import get_bigrams, get_features, bigram_freq, bigram_to_str, calculate_kl_divergence, \
    get_bigrams_in_range, get_nodes_in_range, encode_nodes_with_BERT, encode_nodes_with_BERT_weighted, add_optics_clusters, \
    normalize

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

from transformers import BertTokenizer, BertModel, DistilBertTokenizer, DistilBertModel, AutoTokenizer, AutoModel
import torch

# feature extraction but with equal width binning
def extract_features_with_equalWidthBinning(file_path, groupLength, bin_size, eps=1e-9):
    code_group_lengths = []
    feature_dict = {}
    index_numRows_divider = [10, 0, 0]
    dictionary_type = [0]
    row_ranges = []
    file_names = []
    file_paths = []
    
    def create_dict(file_name, file_path):
        with open(file_path, 'rb') as f:
            rawdata = f.read()
            encoding = chardet.detect(rawdata)['encoding']
        with open(file_path, 'r', encoding=encoding) as f:
            code = f.read()
            tree = parse(code)
            
            code_lines = code.split('\n')
            code_groups = [code_lines[i:i + groupLength] for i in range(0, len(code_lines), groupLength)]

            for i, code_group in enumerate(code_groups):
                index_numRows_divider[1] += 1
                start_line = i * groupLength
                end_line = (i + 1) * groupLength - 1
                
                if dictionary_type[0] == 0:
                    features = get_bigrams_nonverbose(tree, start_line, end_line)
                # for some reason this second if doesn't work, which makes process_file out of bounds immediately --_
                if dictionary_type[0] == 1:
                    features = get_nodes_in_range(tree, start_line, end_line)
                
                for feature in features:
                    str_feature = str(feature)
                    if feature_dict.get(str_feature) == None:
                        feature_dict[str_feature] = index_numRows_divider[0]
                        index_numRows_divider[0] += 1
                
                code_group_lengths.append(len(code_group))
        
    def process_file(file_name, file_path, data):
        try:
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                tree = parse(code)
                code_lines = code.split('\n')
                code_groups = [code_lines[i:i + groupLength] for i in range(0, len(code_lines), groupLength)]
                for i, code_group in enumerate(code_groups):
                    code_string = '\n'.join(code_group)
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    features = get_bigrams_nonverbose(tree, start_line, end_line) + get_nodes_in_range(tree, start_line, end_line)
                    feature_freqs = bigram_freq(features)
                    if len(code_string) != 0:
                        initial_features = get_features(code_string)
                    else:
                        initial_features = []
                    
                    
                    row_ranges[index_numRows_divider[1]] = row_range 
                    file_names[index_numRows_divider[1]] = file_name
                    file_paths[index_numRows_divider[1]] = file_path
                    
                    for num, feature in zip(range(3, 10), initial_features):
                        data[index_numRows_divider[1], num] = feature
                    
                    for feature, count in feature_freqs.items():
                        str_feature = str(feature)
                        
                        # the current feature is within bin range
                        if index_numRows_divider[2] != 0 and index_numRows_divider[2] < feature_dict[str_feature]:
                            bin_index = ((feature_dict[str_feature] - index_numRows_divider[2]) // bin_size) + index_numRows_divider[2]
                            data[index_numRows_divider[1], bin_index] += count / (len(code_group))
                            #(len(code_group) * bin_size)
                        else: # the current feature is not within bin range
                            data[index_numRows_divider[1], feature_dict[str_feature]] = count / len(code_group)
                        
                    index_numRows_divider[1] += 1
        except Exception as e:
            raise Exception(f'An error occurred while processing file {file_name}: {e}')
    
    def process_directory(dir_path, data, processed_dirs=set(), processed_files=set()):
       for root, dirs, files in os.walk(dir_path):
           for file_name in files:
               if file_name.endswith('.java'):
                   file_path = os.path.join(root, file_name)
                   if os.path.realpath(file_path) not in processed_files:
                       processed_files.add(os.path.realpath(file_path))
                       #print(file_name)
                       process_file(file_name, file_path, data)
           for dir_name in dirs: 
               dir_path = os.path.join(root, dir_name)
               if os.path.realpath(dir_path) not in processed_dirs:
                   #print(dir_path)
                   processed_dirs.add(os.path.realpath(dir_path))
                   process_directory(dir_path, data, processed_dirs, processed_files)
                   
                   
    def process_directory_dict(dir_path, processed_dirs=set(), processed_files=set()):
       for root, dirs, files in os.walk(dir_path):
           for file_name in files:
               if file_name.endswith('.java'):
                   file_path = os.path.join(root, file_name)
                   if os.path.realpath(file_path) not in processed_files:
                       processed_files.add(os.path.realpath(file_path))
                       #print(file_name)
                       create_dict(file_name, file_path)
           for dir_name in dirs:
               dir_path = os.path.join(root, dir_name)
               if os.path.realpath(dir_path) not in processed_dirs:
                   #print(dir_path)
                   processed_dirs.add(os.path.realpath(dir_path))
                   process_directory_dict(dir_path, processed_dirs, processed_files)
    
    process_directory_dict(file_path)
    index_numRows_divider[2] = index_numRows_divider[0]
    dictionary_type[0] = 1
    process_directory_dict(file_path, set(), set())
    
    
    num_bins = math.ceil((index_numRows_divider[0] - index_numRows_divider[2]) / bin_size)
    num_columns = index_numRows_divider[2] + num_bins
    print(num_columns)
    
    data = lil_matrix((index_numRows_divider[1], num_columns))
    row_ranges = [''] * index_numRows_divider[1]
    file_names = [''] * index_numRows_divider[1]
    file_paths = [''] * index_numRows_divider[1]
    index_numRows_divider[1] = 0
    
    
    print(f'dictionary completion finished {index_numRows_divider[0]} {index_numRows_divider[1]} {index_numRows_divider[2]}')
    print(data.shape)
    
    process_directory(file_path, data)
    
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(feature_dict.keys())[:index_numRows_divider[2]-10] + [str(i) for i in range(1, num_bins+1)]
    print(len(columns))
    
    #csr_data = data.tocsr()
    
    #df = pd.DataFrame.sparse.from_spmatrix(csr_data, columns=columns)
    df = pd.DataFrame(data.toarray(), columns=columns)
    print(f' dataframe created {df.shape}')
    
    df['Row Range'] = row_ranges
    df['File Name'] = file_names
    df['File Path'] = file_paths
    
    df.fillna(0, inplace=True)

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    df = normalize(df)
    print('normalization finished')
    
    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)
    
    return df

# code bert to embed sliding window of token groups, adding all cls token values
def CodeBERTSlideCLSAll(file_path, groupLength, eps=1e-9):
    # variables to track code group lengths, nested bigram column indices, and an index for rows
    code_group_lengths = []
    node_dict = {}
    index_numRows = [10, 0]
    row_ranges = []
    file_names = []
    file_paths = []
    
    #tokenizers for transformer embedding
    tokenizer = AutoTokenizer.from_pretrained('microsoft/codebert-base')
    model = AutoModel.from_pretrained('microsoft/codebert-base')
    
    # first pass that creates feature dictionary, counts rows, records code group lengths
    def create_dict(file_name, file_path):
        with open(file_path, 'rb') as f:
            rawdata = f.read()
            encoding = chardet.detect(rawdata)['encoding']
        with open(file_path, 'r', encoding=encoding) as f:
            code = f.read()
            tree = parse(code)
            num_groups = code.count('\n') // groupLength
            if(len(code) % groupLength != 0): num_groups += 1
            code_groups = [code[i:i + groupLength] for i in range(num_groups)]
            for i, code_group in enumerate(code_groups):
                index_numRows[1] += 1
                start_line = i * groupLength
                end_line = (i + 1) * groupLength - 1
                nodes = get_nodes_in_range(tree, start_line, end_line)
                
                for node in nodes:
                    str_node = str(node)
                    tokens = tokenizer.tokenize(str_node)
                    num_groups = math.ceil(len(tokens) / 512)
                    
                    token_groups = [tokens[z * 512: (z+1) * 512] for z in range(num_groups)]
                    
                    for token_group in token_groups:
                        #print(len(token_group))
                        str_token_group = tokenizer.convert_tokens_to_string(token_group)
                        if node_dict.get(str_token_group+'0') == None:
                            for value in range(768):
                                node_dict[str_token_group+str(value)] = index_numRows[0]
                                index_numRows[0] += 1
                                #print(index_numRows[0])
                           
      
                code_group_lengths.append(len(code_group))
        
        
    def process_file(file_name, file_path, data):
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
                    features = get_features(code_group)
                    
                    #print(index_numRows[1])
                    
                    
                    nodes = get_nodes_in_range(tree, start_line, end_line)
                    
                    for node in nodes:
                        str_node = str(node)
                        tokens = tokenizer.tokenize(str_node)
                        num_groups = math.ceil(len(tokens) / 512)
                        
                        token_groups = [tokens[z * 512: (z+1) * 512] for z in range(num_groups)]
                        
                        for token_group in token_groups:
                            #print(len(token_group))
                            key, val = encode_with_BERT(token_group, tokenizer, model)
                            val = val.flatten()
                            for num in range(len(val)):
                                data[index_numRows[1], node_dict[key+str(num)]] += val[num] / len(code_group)
                            # if key in node_dict:
                            #     #mean
                            #     data[index_numRows[1], node_dict[key]] += np.mean(val) / len(code_group)
                            
                            
                        
                    features = get_features(code_group)
                    
                    row_ranges[index_numRows[1]] = row_range 
                    file_names[index_numRows[1]] = file_name
                    file_paths[index_numRows[1]] = file_path
                    
                    for num, feature in zip(range(3, 10), features):
                        data[index_numRows[1], num] = feature
                        
                    index_numRows[1] += 1
                        
                    
        except Exception as e:
            raise Exception(f'An error occurred while processing file {file_name}: {e}')
            #print(f'An error occurred while processing file {file_name}: {e}')
            
    def process_directory(dir_path, data, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        #print(file_name)
                        process_file(file_name, file_path, data)
            for dir_name in dirs: 
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    #print(dir_path)
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, data, processed_dirs, processed_files)
                    
                    
    def process_directory_dict(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        #print(file_name)
                        create_dict(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    #print(dir_path)
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory_dict(dir_path, processed_dirs, processed_files)
        
    # making and storing sparse data in a lil_matrix (via two passes), then converting to a 
    #compressed sparse row matrix and converting to a dataframe from a sparse matrix
    process_directory_dict(file_path)
    data = lil_matrix((index_numRows[1], index_numRows[0]))
    row_ranges = [''] * index_numRows[1]
    file_names = [''] * index_numRows[1]
    file_paths = [''] * index_numRows[1]
    
    print(f'dictionary completion finished {index_numRows[0]} {index_numRows[1]}')
    print(data.shape)
    index_numRows[1] = 0
    process_directory(file_path, data)
    
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(node_dict.keys())
    
    #csr_data = data.tocsr()
    
    #df = pd.DataFrame.sparse.from_spmatrix(csr_data, columns=columns)
    df = pd.DataFrame(data.toarray(), columns=columns)
    print(f' dataframe created {df.shape}')
    
    df['Row Range'] = row_ranges
    df['File Name'] = file_names
    df['File Path'] = file_paths
    
    df.fillna(0, inplace=True)

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    df = normalize(df)
    print('normalization finished')
    
    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    
    #add_optics_clusters(df, ignore_cols, len(df))
    #print('optics finished')

    # KL Divergence
    #df = calculate_kl_divergence2(df, ignore_cols, code_group_lengths)
    #print('kl divergence finished')

    return df
#codebert nested bigrams, no KL divergence
def CodeBERT_NB_nKL(file_path, groupLength, eps=1e-9):
    data = []
    bert_columns = set()
    code_group_lengths = []
    #tokenizer = DistilBertTokenizer.from_pretrained('distilbert-base-uncased')
    #model = DistilBertModel.from_pretrained('distilbert-base-uncased')
    tokenizer = AutoTokenizer.from_pretrained('microsoft/codebert-base')
    model = AutoModel.from_pretrained('microsoft/codebert-base')

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
                    node_embeddings_dict = {}
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    nodes = get_bigrams(tree, start_line, end_line)
                    node_embeddings = encode_nodes_with_BERT(nodes, tokenizer, model)
                    for node, emb in node_embeddings:
                        node_str = str(node)
                        for num in range(len(emb)):
                            temp = node_str + str(num)
                            bert_columns.add(temp)
                            node_embeddings_dict[temp] = node_embeddings_dict.get(temp, 0) + emb[num]
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name, file_path] + features +
                                [node_embeddings_dict.get(node_str) if node_str in node_embeddings_dict else 0 for node_str in bert_columns])

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
    df = normalize(df)
    print('normalization finished')
    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    add_optics_clusters(df, ignore_cols, len(df))
    print('optics finished')

    #KL Divergence
    #df = calculate_kl_divergence2(df, ignore_cols, code_group_lengths)

    return df

#ast node bert embeddings, but flattened instead of mean
def readFilesWithBERT2(file_path, groupLength, eps=1e-9):
    data = []
    bert_columns = set()
    code_group_lengths = []
    #tokenizer = DistilBertTokenizer.from_pretrained('distilbert-base-uncased')
    #model = DistilBertModel.from_pretrained('distilbert-base-uncased')
    tokenizer = AutoTokenizer.from_pretrained('microsoft/codebert-base')
    model = AutoModel.from_pretrained('microsoft/codebert-base')

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
                    node_embeddings_dict = {}
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    nodes = get_nodes_in_range(tree, start_line, end_line)
                    node_embeddings = encode_nodes_with_BERT(nodes, tokenizer, model)
                    for node, emb in node_embeddings:
                        node_str = str(node)
                        for num in range(len(emb)):
                            temp = node_str + str(num)
                            bert_columns.add(temp)
                            node_embeddings_dict[temp] = node_embeddings_dict.get(temp, 0) + emb[num]
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name, file_path] + features +
                                [node_embeddings_dict.get(node_str) if node_str in node_embeddings_dict else 0 for node_str in bert_columns])

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

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    add_optics_clusters(df, ignore_cols, len(df))
    print('optics finished')

    #KL Divergence
    df = calculate_kl_divergence2(df, ignore_cols, code_group_lengths)
    print('kl divergence finished')

    #moved this down, to at the end of divergence and clustering calculation
    df = normalize(df)
    print('normalization finished')

    return df

#codebert/distilbert nested bigrams
def readFilesWithBERT(file_path, groupLength, eps=1e-9):
    data = []
    bert_columns = set()
    code_group_lengths = []
    #tokenizer = DistilBertTokenizer.from_pretrained('distilbert-base-uncased')
    #model = DistilBertModel.from_pretrained('distilbert-base-uncased')
    tokenizer = AutoTokenizer.from_pretrained('microsoft/codebert-base')
    model = AutoModel.from_pretrained('microsoft/codebert-base')

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
                    node_embeddings_dict = {}
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    nodes = get_bigrams(tree, start_line, end_line)
                    node_embeddings = encode_nodes_with_BERT(nodes, tokenizer, model)
                    for node, emb in node_embeddings:
                        node_str = str(node)
                        bert_columns.add(node_str)
                        node_embeddings_dict[node_str] = node_embeddings_dict.get(node_str, 0) + emb
                    features = get_features(code_group)

                    # Store the length of the code group
                    code_group_lengths.append(len(code_group))

                    data.append([row_range, file_name, file_path] + features +
                                [node_embeddings_dict.get(node_str).mean() if node_str in node_embeddings_dict else 0 for node_str in bert_columns])

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
    df = normalize(df)
    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    add_optics_clusters(df, ignore_cols, len(df))

    #KL Divergence
    df = calculate_kl_divergence2(df, ignore_cols, code_group_lengths)

    return df

#bert embedded ast nodes
def readFilesWithDistilBERT(file_path, groupLength, eps=1e-9):
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

# processes the files in two passes, one to create a dictionary, and the next to collect the data
def NBKL3(file_path, groupLength, eps=1e-9):
    # variables to track code group lengths, nested bigram column indices, and an index for rows
    code_group_lengths = []
    nested_bigram_dict = {}
    index_numRows = [10, 0]
    row_ranges = []
    file_names = []
    file_paths = []
    
    # first pass that creates feature dictionary, counts rows, records code group lengths
    def create_dict(file_name, file_path):
        with open(file_path, 'rb') as f:
            rawdata = f.read()
            encoding = chardet.detect(rawdata)['encoding']
        with open(file_path, 'r', encoding=encoding) as f:
            code = f.read()
            tree = parse(code)
            num_groups = code.count('\n') // groupLength
            if(len(code) % groupLength != 0): num_groups += 1
            code_groups = [code[i:i + groupLength] for i in range(num_groups)]
            for i, code_group in enumerate(code_groups):
                index_numRows[1] += 1
                start_line = i * groupLength
                end_line = (i + 1) * groupLength - 1
                #bigrams2 = get_bigrams(tree, start_line, end_line)
               #bigrams = get_nodes_in_range(tree, start_line, end_line)
                bigrams = get_bigrams_nonverbose(tree, start_line, end_line) + get_nodes_in_range(tree, start_line, end_line)
                for bigram in bigrams:
                    str_bigram = str(bigram)
                    if nested_bigram_dict.get(str_bigram) == None:
                        nested_bigram_dict[str_bigram] = index_numRows[0]
                        index_numRows[0] += 1        
                code_group_lengths.append(len(code_group))
        
        
    def process_file(file_name, file_path, data):
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
                    #bigrams = get_bigrams(tree, start_line, end_line)
                    bigrams = get_bigrams_nonverbose(tree, start_line, end_line) + get_nodes_in_range(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    features = get_features(code_group)
                    
                    #print(index_numRows[1])
                    
                    row_ranges[index_numRows[1]] = row_range 
                    file_names[index_numRows[1]] = file_name
                    file_paths[index_numRows[1]] = file_path
                    
                    for num, feature in zip(range(3, 10), features):
                        data[index_numRows[1], num] = feature
                    
                    for bigram, count in bigram_freqs.items():
                        str_bigram = str(bigram)
                        if str_bigram in nested_bigram_dict:
                            data[index_numRows[1], nested_bigram_dict[str_bigram]] = count / len(code_group)
                            
                        

                        
                    index_numRows[1] += 1
                        
                    
        except Exception as e:
            raise Exception(f'An error occurred while processing file {file_name}: {e}')
            #print(f'An error occurred while processing file {file_name}: {e}')
            
    def process_directory(dir_path, data, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        #print(file_name)
                        process_file(file_name, file_path, data)
            for dir_name in dirs: 
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    #print(dir_path)
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, data, processed_dirs, processed_files)
                    
                    
    def process_directory_dict(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        #print(file_name)
                        create_dict(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    #print(dir_path)
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory_dict(dir_path, processed_dirs, processed_files)
        
    # making and storing sparse data in a lil_matrix (via two passes), then converting to a 
    #compressed sparse row matrix and converting to a dataframe from a sparse matrix
    process_directory_dict(file_path)
    data = lil_matrix((index_numRows[1], index_numRows[0]))
    row_ranges = [''] * index_numRows[1]
    file_names = [''] * index_numRows[1]
    file_paths = [''] * index_numRows[1]
    
    print(f'dictionary completion finished {index_numRows[0]} {index_numRows[1]}')
    print(data.shape)
    index_numRows[1] = 0
    process_directory(file_path, data)
    
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(nested_bigram_dict.keys())
    
    #csr_data = data.tocsr()
    
    #df = pd.DataFrame.sparse.from_spmatrix(csr_data, columns=columns)
    df = pd.DataFrame(data.toarray(), columns=columns)
    print(f' dataframe created {df.shape}')
    
    df['Row Range'] = row_ranges
    df['File Name'] = file_names
    df['File Path'] = file_paths
    
    df.fillna(0, inplace=True)

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    df = normalize(df)
    print('normalization finished')
    
    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    
    #add_optics_clusters(df, ignore_cols, len(df))
    #print('optics finished')

    # KL Divergence
    #df = calculate_kl_divergence2(df, ignore_cols, code_group_lengths)
    #print('kl divergence finished')

    return df

def checkFiles(file_path):
    def process_file(file_name, file_path):
        try:
            with open(file_path, 'rb') as f:
                rawdata = f.read()
                encoding = chardet.detect(rawdata)['encoding']
            with open(file_path, 'r', encoding=encoding) as f:
                code = f.read()
                tree = parse(code)
        except Exception as e:
            print(f'\n\nFailed: {file_name}, {file_path}')
            traceback.print_exc()
    def process_directory(dir_path, processed_dirs=set(), processed_files=set()):
        for root, dirs, files in os.walk(dir_path):
            for file_name in files:
                if file_name.endswith('.java'):
                    file_path = os.path.join(root, file_name)
                    if os.path.realpath(file_path) not in processed_files:
                        processed_files.add(os.path.realpath(file_path))
                        process_file(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, processed_dirs, processed_files)

    process_directory(file_path)

#NB KL divergence with optics clustering and KL divergence, but very inefficient runtime
def NBKL2(file_path, groupLength, eps=1e-9):
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
                        
                    # the way I'm making this dataset may be scuffed to begin with(like I don't think there's a new row being entered here for example...)
                    data.append([row_range, file_name, file_path] + features + [
                        bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in
                        nested_bigram_columns])
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
                        #print(file_name)
                        process_file(file_name, file_path)
            for dir_name in dirs:
                dir_path = os.path.join(root, dir_name)
                if os.path.realpath(dir_path) not in processed_dirs:
                    print(dir_path)
                    processed_dirs.add(os.path.realpath(dir_path))
                    process_directory(dir_path, processed_dirs, processed_files)

    process_directory(file_path)
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(map(bigram_to_str, nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    df.fillna(0, inplace=True)

    ignore_cols = ['Written', 'File Path', 'Row Range', 'File Name']
    df = normalize(df)
    print('normalization finished')
    
    # Labeling
    df['Written'] = df['File Path'].apply(lambda x: 1 if 'Anomalous' in x else 0)

    
    add_optics_clusters(df, ignore_cols, len(df))
    print('optics finished')

    # KL Divergence
    df = calculate_kl_divergence2(df, ignore_cols, code_group_lengths)
    print('kl divergence finished')

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





