import javalang
from pyod.models.knn import KNN
from pyod.utils import evaluate_print

import pandas as pd

import os
from javalang.parse import parse
from javalang.tree import IfStatement, ForStatement, WhileStatement, SwitchStatement, MethodInvocation

from collections import Counter

import math


def readFiles(folder_path: str, group_length: int) -> pd.DataFrame:
    data = []
    all_node_types = set()
    for file_name in os.listdir(folder_path):
        if file_name.endswith(".java"):
            with open(os.path.join(folder_path, file_name), "r") as f:
                code = f.read()
                tree = parse(code)
                row_range_start = 1
                row_range_end = group_length
                for i in range(0, len(code.split("\n")), group_length):
                    group_code = "\n".join(code.split("\n")[i:i + group_length])

                    lines = group_code.split("\n")
                    total_chars = len(group_code)
                    keywords = sum([group_code.count(keyword) for keyword in
                                    ["if", "else if", "else", "for", "while", "switch"]]) / total_chars
                    literals = sum([group_code.count(literal) for literal in
                                    ["\"", "\'", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]]) / total_chars
                    tabs = sum([group_code.count("\t")]) / total_chars
                    spaces = sum([group_code.count(" ")]) / total_chars
                    empty_lines = sum(1 for line in lines if line.strip() == '') / total_chars
                    functions = len(list(tree.filter(javalang.tree.MethodInvocation))) / total_chars

                    nesting_depths = [len(path) for path, node in tree.filter(
                        (javalang.tree.IfStatement, javalang.tree.ForStatement, javalang.tree.WhileStatement))]
                    nesting_depth = max(nesting_depths) if nesting_depths else 0
                    average_length = sum([len(line) for line in group_code.split("\n")]) / len(group_code.split("\n"))
                    whitespace_ratio = group_code.count(" ") / (total_chars - group_code.count(" "))

                    # Nested Bigrams feature
                    bigrams = code_bigram(tree)
                    all_bigrams, frequent_bigrams, frequent = bigram_freq(bigrams)

                    max_depth = max([len(path) for path, node in tree])
                    ast_node_depths = [len(path) for path, node in tree]
                    ast_node_avg_depth = sum(ast_node_depths) / len(ast_node_depths) if ast_node_depths else 0

                    data.append({
                        "Row Range": f"{row_range_start}-{row_range_end}",
                        "File Name": file_name,
                        "Written": 0,
                        "Keywords": keywords,
                        "Literals": literals,
                        "Tabs": tabs,
                        "Spaces": spaces,
                        "Functions": functions,
                        "Empty Lines": empty_lines,
                        "Nesting Depth": nesting_depth,
                        "Average Length": average_length,
                        "Whitespace Ratio": whitespace_ratio,
                        "Max Depth": max_depth,
                        "AST Node Avg Depth": ast_node_avg_depth,
                        # Nested Bigrams feature
                        #'All Bigrams': all_bigrams,
                        #'Frequent Bigrams': frequent_bigrams,
                        #'Frequent': frequent
                    })
                    row_range_start += group_length
                    row_range_end += group_length

    df = pd.DataFrame(data)
    return df


def code_bigram(node):
    result = []
    if isinstance(node, javalang.tree.MethodDeclaration):
        block = node.body
        for child in block:
            if child is not None and not isinstance(child, (str, list)):
                bigram_collector = code_bigram(child)
                result.extend(bigram_collector)
    else:
        for child in node.children:
            if child is not None and not isinstance(child, (str, list)):
                bigram = [str(node), str(child)]
                result.append(bigram)
                bigram_collector = code_bigram(child)
                if bigram_collector:
                    result.extend(bigram_collector)
    return result

def bigram_freq(bigrams):
    bigram_ListofTuple = []
    for bigram in bigrams:
        bigram_ListofTuple.append(tuple(bigram))
    bigram_freq_holder = Counter(bigram_ListofTuple)
    bigram_SetofTuple = set(bigram_ListofTuple)
    frequent_bigrams = [bigram for bigram, count in bigram_freq_holder.items() if (count > 1 and not math.isnan(count))]
    return (bigram_SetofTuple, frequent_bigrams, bigram_freq_holder)




def one_hot_encode(df, column):
    # Check if the specified column contains any float values
    if df[column].apply(lambda x: isinstance(x, float)).any():
        print(df[column].to_string(index=False))
        raise ValueError(f"The '{column}' column contains float values and cannot be one-hot encoded")

    # Convert the set objects in the specified column to a string representation
    df[column] = df[column].apply(lambda x: ','.join(sorted(['_'.join(map(str, y)) for y in list(x)])))

    # One-hot encode the column
    dummies = pd.get_dummies(df[column].str.split(',', expand=True).stack()).sum()

    # Drop the original column and add the one-hot encoded columns
    df = df.drop(column, axis=1)
    df = pd.concat([df, dummies], axis=1)

    return df


files = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data'

data = readFiles(files, 20)

for num in range(78, 90):
    data.loc[num, 'Written'] = 1
for num in range(187, 209):
    data.loc[num, 'Written'] = 1


data = data.where(data.notnull(), 0)

# One-hot encode the 'All Bigrams' and 'Frequent Bigrams' columns
#data = one_hot_encode(data, 'All Bigrams')
#data = one_hot_encode(data, 'Frequent Bigrams')
#data = one_hot_encode(data, 'Frequent')

pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
print(data.columns)
print(data)

xcols = list(data.columns)
xcols.remove('Row Range')
xcols.remove('Written')
xcols.remove('File Name')
X = data[xcols].values
y = data['Written'].values

#kNN
clf = KNN(contamination = 0.17, n_neighbors = 129)
clf.fit(X)

yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)

#error file
fileScuffed = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data Output'
dataNew = readFiles(fileScuffed, 20)
newXCols = list(dataNew.columns)
print(f'newXCols: {newXCols}')
newXCols.remove('Row Range')
newXCols.remove('Written')
newXCols.remove('File Name')

newX = dataNew[newXCols].values
anomalyScores = clf.decision_function(newX)

threshold = 0.8

predictions = (anomalyScores > threshold).astype(int)

#Print predictions
for i in range(len(predictions)):
    prediction = predictions[i]
    if prediction == 0:
        print(f'Instance {i} is normal')
    else:
        print(f'Instance {i} is anomalous')