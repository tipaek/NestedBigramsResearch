import pandas as pd
import numpy as np
import math

# for files
import os
import chardet
from pathlib import Path
import sys
sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch\\Code')

from HelpersCleaned import (get_bigrams, get_bigrams_nonverbose, get_features, bigram_freq, bigram_to_str, calculate_kl_divergence, get_nodes_in_range, encode_nodes_with_BERT, 
    add_xmeans_clusters, calculate_kl_divergence2, add_optics_clusters, normalize, encode_with_BERT)
from javalang.parse import parse

from transformers import BertTokenizer, BertModel, DistilBertTokenizer, DistilBertModel, AutoTokenizer, AutoModel

import traceback

from scipy.sparse import lil_matrix


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
                                node_dict[str_token_group+value] = index_numRows[0]
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
                    #features = []
                # for some reason this second if doesn't work, which makes process_file out of bounds immediately --_
                if dictionary_type[0] == 1:
                    #features = get_nodes_in_range(tree, start_line, end_line)
                    #features = get_bigrams(tree, start_line, end_line)
                    features = []
                
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
                    #features = get_bigrams_nonverbose(tree, start_line, end_line) + get_nodes_in_range(tree, start_line, end_line)
                    #features = get_bigrams_nonverbose(tree, start_line, end_line) + get_bigrams(tree, start_line, end_line)
                    #features = get_bigrams(tree, start_line, end_line)
                    features = get_bigrams_nonverbose(tree, start_line, end_line)
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
            code_lines = code.split('\n')
            code_groups = [code_lines[i:i + groupLength] for i in range(0, len(code_lines), groupLength)]
            for i, code_group in enumerate(code_groups):
                index_numRows[1] += 1
                start_line = i * groupLength
                end_line = (i + 1) * groupLength - 1
                #bigrams2 = get_bigrams(tree, start_line, end_line)
               #bigrams = get_nodes_in_range(tree, start_line, end_line)
                bigrams = get_bigrams(tree, start_line, end_line)
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
                code_lines = code.split('\n')
                code_groups = [code_lines[i:i + groupLength] for i in range(0, len(code_lines), groupLength)]
                for i, code_group in enumerate(code_groups):
                    code_string = '\n'.join(code_group)
                    start_line = i * groupLength
                    end_line = (i + 1) * groupLength - 1
                    row_range = f'{start_line}-{end_line}'
                    #bigrams = get_bigrams(tree, start_line, end_line)
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    if len(code_string) != 0:
                        features = get_features(code_string)
                    else:
                        features = []
                    
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

# deleted lineIterator of the 40 uathors dataset bc contains decompiled code
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

