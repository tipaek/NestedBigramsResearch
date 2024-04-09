import pandas as pd
import numpy as np
import math

# for files
import os
import chardet
from pathlib import Path
import sys
#sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch\\Code')
sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch\\Code')

from HelpersCleaned import (get_bigrams, get_bigrams_nonverbose, get_features, bigram_freq, bigram_to_str, calculate_kl_divergence, get_nodes_in_range, encode_nodes_with_BERT, 
    add_xmeans_clusters, calculate_kl_divergence2, add_optics_clusters, normalize, encode_with_BERT, feature_cls)
from javalang.parse import parse

from transformers import BertTokenizer, BertModel, DistilBertTokenizer, DistilBertModel, AutoTokenizer, AutoModel

import traceback

from scipy.sparse import lil_matrix

import torch

# same as equal width binning except has two kinds of bins
def extract_features_with_equalWidthBinning2(file_path, groupLength, bin_size, bin2_size, eps=1e-9):
    code_group_lengths = []
    feature_dict = {}
    index_numRows_divider = [10, 0, 0, 0, 0] # column index, row index, divider for tracking binning, divider for tracking binning 2, number of type 1 bins
    dictionary_type = [0]
    row_ranges = []
    file_names = []
    file_paths = []
    
    
    #tokenizers for transformer embedding
    tokenizer = AutoTokenizer.from_pretrained('microsoft/codebert-base')
    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
    model = AutoModel.from_pretrained('microsoft/codebert-base').to(device)

    
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
                    #features = get_bigrams_nonverbose(tree, start_line, end_line)
                    features = []
                # for some reason this second if doesn't work, which makes process_file out of bounds immediately --_
                if dictionary_type[0] == 1:
                    #features = get_nodes_in_range(tree, start_line, end_line)
                    features = get_bigrams(tree, start_line, end_line)
                    #features = get_bigrams_nonverbose(tree, start_line, end_line)
                if dictionary_type[0] == 2:
                    features = get_bigrams(tree, start_line, end_line)
                
                # FOR STANDARD
                if dictionary_type[0] != 2:
                    for feature in features:
                        str_feature = str(feature) + "standard"
                        if feature_dict.get(str_feature) == None:
                            feature_dict[str_feature] = index_numRows_divider[0]
                            index_numRows_divider[0] += 1
                
                # FOR TRANSFORMERS
                if dictionary_type[0] == 2:
                    for feature in features:
                        str_feature = str(feature)
                        tokens = tokenizer.tokenize(str_feature)
                        num_groups = math.ceil(len(tokens) / 512)
                        
                        token_groups = [tokens[z * 512: (z+1) * 512] for z in range(num_groups)]
                        for token_group in token_groups:
                            str_token_group = tokenizer.convert_tokens_to_string(token_group)
                            if feature_dict.get(str_token_group) == None:
                                
                                # # FOR ALL VALUES
                                # for index in range(768):
                                #     feature_dict[str_token_group+str(index)] = index_numRows_divider[0]
                                #     index_numRows_divider[0] += 1
                                
                                # FOR STANDARD
                                feature_dict[str_token_group] = index_numRows_divider[0]
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
                    features = get_bigrams(tree, start_line, end_line)
                    #features = get_bigrams_nonverbose(tree, start_line, end_line)
                    
                    feature_freqs = bigram_freq(features, 1)
                    feature_freqs.update(feature_cls(features, tokenizer, model, feature_dict, 'absolute'))
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
                        #str_feature = feature
                        # the current feature is within bin range
                        if index_numRows_divider[2] != 0 and index_numRows_divider[2] < feature_dict[str_feature]:
                            # first bin
                            if index_numRows_divider[3] > feature_dict[str_feature]:
                                bin_index = ((feature_dict[str_feature] - index_numRows_divider[2]) // bin_size) + index_numRows_divider[2]
                            else: #second bin
                                bin_index = ((feature_dict[str_feature] - index_numRows_divider[3]) // bin2_size) + index_numRows_divider[4]
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
    print(num_bins)
    
    dictionary_type[0] = 2 
    index_numRows_divider[3] = index_numRows_divider[0]
    process_directory_dict(file_path, set(), set())
    
    index_numRows_divider[4] = num_bins
    
    num_bins2 = math.ceil((index_numRows_divider[0] - index_numRows_divider[3]) / bin2_size)
    print(num_bins2)
    
    num_columns = index_numRows_divider[2] + num_bins + num_bins2
    print(num_columns)
    
    data = lil_matrix((index_numRows_divider[1], num_columns))
    row_ranges = [''] * index_numRows_divider[1]
    file_names = [''] * index_numRows_divider[1]
    file_paths = [''] * index_numRows_divider[1]
    index_numRows_divider[1] = 0
    
    
    print(f'dictionary completion finished {index_numRows_divider[0]} {index_numRows_divider[1]} {index_numRows_divider[2]} {index_numRows_divider[3]}')
    print(data.shape)
    
    process_directory(file_path, data)
    
    columns = ['Row Range', 'File Name', 'File Path', 'Whitespace Ratio', 'Statement Words', 'Tabs', 'Underscores',
               'Empty Lines',
               'Mean Line Length', 'Mean Comment Length'] + list(feature_dict.keys())[:index_numRows_divider[2]-10] + [str(i) for i in range(1, num_bins+num_bins2+1)]
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


def extract_features_with_equalWidthBinning(file_path, groupLength, bin_size, eps=1e-9):
    code_group_lengths = []
    feature_dict = {}
    index_numRows_divider = [10, 0, 0]
    dictionary_type = [0]
    row_ranges = []
    file_names = []
    file_paths = []
    
    
    #tokenizers for transformer embedding
    tokenizer = AutoTokenizer.from_pretrained('microsoft/codebert-base')
    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
    model = AutoModel.from_pretrained('microsoft/codebert-base').to(device)

    
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
                    #features = get_bigrams_nonverbose(tree, start_line, end_line)
                    features = []
                # for some reason this second if doesn't work, which makes process_file out of bounds immediately --_
                if dictionary_type[0] == 1:
                    #features = get_nodes_in_range(tree, start_line, end_line)
                    features = get_bigrams(tree, start_line, end_line)
                    #features = get_bigrams_nonverbose(tree, start_line, end_line)
                
                # FOR STANDARD
                # for feature in features:
                #     str_feature = str(feature)
                #     if feature_dict.get(str_feature) == None:
                #         feature_dict[str_feature] = index_numRows_divider[0]
                #         index_numRows_divider[0] += 1
                
                # FOR TRANSFORMERS
                for feature in features:
                    str_feature = str(feature)
                    tokens = tokenizer.tokenize(str_feature)
                    num_groups = math.ceil(len(tokens) / 512)
                    
                    token_groups = [tokens[z * 512: (z+1) * 512] for z in range(num_groups)]
                    for token_group in token_groups:
                        str_token_group = tokenizer.convert_tokens_to_string(token_group)
                        if feature_dict.get(str_token_group) == None:
                            
                            # # FOR ALL VALUES
                            # for index in range(768):
                            #     feature_dict[str_token_group+str(index)] = index_numRows_divider[0]
                            #     index_numRows_divider[0] += 1
                            
                            # FOR STANDARD
                            feature_dict[str_token_group] = index_numRows_divider[0]
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
                    features = get_bigrams(tree, start_line, end_line)
                    #features = get_bigrams_nonverbose(tree, start_line, end_line)
                    
                    #feature_freqs = bigram_freq(features)
                    feature_freqs = feature_cls(features, tokenizer, model, feature_dict, 'absolute')
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
                        #str_feature = feature
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
    
    process_directory_dict(file_path, set(), set())
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
    

