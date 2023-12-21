from pyod.models.knn import KNN
from pyod.utils import evaluate_print

import pandas as pd
import math

import javalang
from javalang.parse import parse
from javalang.tree import *# ForStatement, WhileStatement, DoStatement, IfStatement

import os

from sklearn.feature_extraction.text import CountVectorizer

def readFilesSyntactical(folder_path: str, group_size: int) -> (pd.DataFrame, list):
    data = []
    all_variable_names = set()
    for file_name in os.listdir(folder_path):
        if file_name.endswith(".java"):
            with open(os.path.join(folder_path, file_name), "r") as f:
                code = f.read()
                tree = parse(code)
                row_range_start = 1
                row_range_end = group_size
                for i in range(0, len(code.split("\n")), group_size):
                    group_code = "\n".join(code.split("\n")[i:i+group_size])
                    num_nodes = len(list(tree))
                    num_loops = len(list(tree.filter((ForStatement, WhileStatement, DoStatement))))
                    num_conditionals = len(list(tree.filter(IfStatement)))
                    identifiers = [node.name for path, node in tree if hasattr(node, "name")]
                    all_variable_names.update(identifiers)
                    avg_identifier_length = sum([len(identifier) for identifier in identifiers])/len(identifiers) if identifiers else 0
                    vectorizer = CountVectorizer(lowercase=False)
                    X = vectorizer.fit_transform(identifiers)
                    variable_name_counts = X.toarray().sum(axis=0)
                    data.append({
                        "File Name": file_name,
                        "Row Range": f"{row_range_start}-{row_range_end}",
                        "Written": 0,
                        "Number of Nodes": num_nodes,
                        "Number of Loops": num_loops,
                        "Number of Conditionals": num_conditionals,
                        "Average Identifier Length": avg_identifier_length,
                        "Variable Name Counts": dict(zip(vectorizer.get_feature_names_out(), variable_name_counts))
                    })
                    row_range_start += group_size
                    row_range_end += group_size
    all_variable_names = sorted(list(all_variable_names))
    for row in data:
        for variable_name in all_variable_names:
            row[variable_name] = row["Variable Name Counts"].get(variable_name, 0)
        del row["Variable Name Counts"]
    df = pd.DataFrame(data)
    return df, all_variable_names


def readFilesSyntacticalWithVariableNames(folder_path: str, group_size: int, variable_names: list) -> pd.DataFrame:
    data = []
    for file_name in os.listdir(folder_path):
        if file_name.endswith(".java"):
            with open(os.path.join(folder_path, file_name), "r") as f:
                code = f.read()
                tree = parse(code)
                row_range_start = 1
                row_range_end = group_size
                for i in range(0, len(code.split("\n")), group_size):
                    group_code = "\n".join(code.split("\n")[i:i+group_size])
                    num_nodes = len(list(tree))
                    num_loops = len(list(tree.filter((ForStatement, WhileStatement, DoStatement))))
                    num_conditionals = len(list(tree.filter(IfStatement)))
                    identifiers = [node.name for path, node in tree if hasattr(node, "name")]
                    avg_identifier_length = sum([len(identifier) for identifier in identifiers])/len(identifiers) if identifiers else 0
                    vectorizer = CountVectorizer(vocabulary=variable_names, lowercase=False)
                    X = vectorizer.fit_transform(identifiers)
                    variable_name_counts = X.toarray().sum(axis=0)
                    data.append({
                        "Row Range": f"{row_range_start}-{row_range_end}",
                        "Written": 1,
                        "Number of Nodes": num_nodes,
                        "Number of Loops": num_loops,
                        "Number of Conditionals": num_conditionals,
                        "Average Identifier Length": avg_identifier_length,
                        "Variable Name Counts": dict(zip(vectorizer.get_feature_names_out(), variable_name_counts))
                    })
                    row_range_start += group_size
                    row_range_end += group_size
    for row in data:
        for variable_name in variable_names:
            row[variable_name] = row["Variable Name Counts"].get(variable_name, 0)
        del row["Variable Name Counts"]
    df = pd.DataFrame(data)
    return df


files = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data'

data, variables = readFilesSyntactical(files, 20)

for num in range(78, 90):
    data.loc[num, 'Written'] = 1
for num in range(187, 209):
    data.loc[num, 'Written'] = 1

pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
print(data)

xcols = list(data.columns)
xcols.remove('Row Range')
xcols.remove('Written')
xcols.remove('File Name')
X = data[xcols].values
y = data['Written'].values

#kNN
clf = KNN(contamination = 0.17, n_neighbors = 200)
clf.fit(X)

yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)

#error file
fileScuffed = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data Output'
dataNew = readFilesSyntacticalWithVariableNames(fileScuffed, 20, variables)
newXCols = list(dataNew.columns)
print(f'newXCols: {newXCols}')
newXCols.remove('Row Range')
newXCols.remove('Written')

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