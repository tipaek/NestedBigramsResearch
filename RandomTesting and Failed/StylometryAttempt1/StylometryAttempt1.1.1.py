import javalang
from pyod.models.knn import KNN
from pyod.utils import evaluate_print

import pandas as pd

import os
from javalang.parse import parse
from javalang.tree import *

from collections import Counter


def get_bigrams(node, start_line, end_line):
    result = []
    if isinstance(node, list):
        for child in node:
            result.extend(get_bigrams(child, start_line, end_line))
    elif isinstance(node, Node):
        if node.position is not None and start_line <= node.position.line <= end_line:
            for child in node.children:
                if isinstance(child, list):
                    for grandchild in child:
                        result.extend(get_bigrams(grandchild, start_line, end_line))
                elif isinstance(child,
                                Node) and child.position is not None and start_line <= child.position.line <= end_line:
                    result.append((node, child))
                    result.extend(get_bigrams(child, start_line, end_line))
        else:
            for child in node.children:
                if isinstance(child, list):
                    for grandchild in child:
                        result.extend(get_bigrams(grandchild, start_line, end_line))
                elif isinstance(child, Node):
                    result.extend(get_bigrams(child, start_line, end_line))

    return result


def bigram_freq(bigrams):
    return Counter(bigrams)


def bigram_to_str(bigram):
    result = []
    for node in bigram:
        attrs = []
        for attr, value in node.__dict__.items():
            if value != []:
                attrs.append(f'{attr}={value}')
        result.append(f'{node.__class__.__name__}({", ".join(attrs)})')
    return f'({", ".join(result)})'


def get_features(code_group):
    whitespace_ratio = code_group.count(' ') / len(code_group)
    keywords = sum(code_group.count(keyword) for keyword in ["if", "else if", "else", "for", "while", "switch"]) / len(
        code_group)
    literals = sum(code_group.count(literal) for literal in ["true", "false", "null"]) / len(code_group)
    tabs = code_group.count('\t') / len(code_group)
    spaces = code_group.count(' ') / len(code_group)
    empty_lines = code_group.count('\n\n') / len(code_group)
    underscores = code_group.count('_') / len(code_group)
    functions = code_group.count('function') / len(code_group)
    return [whitespace_ratio, keywords, literals, tabs, spaces, underscores, empty_lines, functions]

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
                    row_range = f'{i*groupLength}-{(i+1)*groupLength-1}'
                    start_line, end_line = map(int, row_range.split('-'))
                    bigrams = get_bigrams(tree, start_line, end_line)
                    bigram_freqs = bigram_freq(bigrams)
                    nested_bigram_columns.update(bigram_freqs.keys())
                    features = get_features(code_group)
                    data.append([row_range, file_name] + features + [bigram_freqs.get(bigram_to_str(bigram), 0) / len(code_group) for bigram in nested_bigram_columns])
    columns = ['Row Range', 'File Name', 'Whitespace Ratio', 'Keywords', 'Literals', 'Tabs', 'Spaces', 'Underscores', 'Empty Lines', 'Functions'] + list(map(bigram_to_str,nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    # Fill missing values with 0
    df.fillna(0, inplace=True)
    return df, list(nested_bigram_columns)

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
    columns = ['Row Range', 'File Name', 'Whitespace Ratio', 'Keywords', 'Literals', 'Tabs', 'Spaces', 'Underscores', 'Empty Lines', 'Functions'] + list(map(bigram_to_str,nested_bigram_columns))
    df = pd.DataFrame(data, columns=columns)
    # Fill missing values with 0
    df.fillna(0, inplace=True)
    return df, columns



files = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data'

data, usedBigrams = readFiles(files, 30)
data['Written'] = 0

data.loc[data['File Name'] == 'java1.java', 'Written'] = 1
data.loc[data['File Name'] == 'fault.java', 'Written'] = 1
data.loc[data['File Name'] == 'tic-tac-toe.java', 'Written'] = 1
#for num in range(198, 207):
    #data.loc[num, 'Written'] = 1
#for num in range(50, 54):
    #data.loc[num, 'Written'] = 1
    #59->52


#data.to_csv('StylometryAttempt1.1.1, 30.csv', index=False)


pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
#print(data)
#print(data.loc[54, 'Row Range'])
xcols = list(data.columns)
xcols.remove('Row Range')
xcols.remove('Written')
xcols.remove('File Name')
X = data[xcols].values
y = data['Written'].values


#kNN
clf = KNN(contamination = 0.11, n_neighbors = 50)
clf.fit(X)

yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)

#error file
fileScuffed = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data Output'
dataNew, newXCols = readFilesWithBigrams(fileScuffed, 30, usedBigrams)
newXCols.remove('Row Range')
newXCols.remove('File Name')

newX = dataNew[newXCols].values
anomalyScores = clf.decision_function(newX)

threshold = 0.05

predictions = (anomalyScores > threshold).astype(int)

#Print predictions
for i in range(len(predictions)):
    prediction = predictions[i]
    if prediction == 0:
        print(f'Instance {i} is normal')
    else:
        print(f'Instance {i} is anomalous')