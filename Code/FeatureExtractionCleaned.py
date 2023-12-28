import pandas as pd
import numpy as np

# for files
import os
import chardet
from pathlib import Path


from HelpersCleaned import get_bigrams, get_features, bigram_freq, bigram_to_str, calculate_kl_divergence, get_nodes_in_range, encode_nodes_with_BERT, encode_nodes_with_BERT_weighted, add_xmeans_clusters, calculate_kl_divergence2, add_optics_clusters, normalize
from javalang.parse import parse

from transformers import BertTokenizer, BertModel, DistilBertTokenizer, DistilBertModel

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

