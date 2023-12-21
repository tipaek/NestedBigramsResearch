import javalang
from pyod.models.knn import KNN
from pyod.utils import evaluate_print

import pandas as pd

import os
from javalang.parse import parse
from javalang.tree import IfStatement

def get_node_types(tree, start_line, end_line):
    node_types = []
    stack = [tree]
    while stack:
        node = stack.pop()
        if isinstance(node, list):
            stack.extend(node)
        else:
            if hasattr(node, 'position') and node.position:
                if start_line <= node.position.line <= end_line:
                    node_types.append(type(node))
            if hasattr(node, 'children'):
                stack.extend(node.children)
    return node_types



def get_bigram_counts(bigrams, node_types):
    bigram_counts = {bigram: 0 for bigram in bigrams}
    for bigram in zip(node_types, node_types[1:]):
        if bigram in bigram_counts:
            bigram_counts[bigram] += 1
    return bigram_counts

def get_variable_names(tree, start_line, end_line):
    variable_names = []
    stack = [tree]
    while stack:
        node = stack.pop()
        if isinstance(node, list):
            stack.extend(node)
        else:
            if hasattr(node, 'position') and node.position:
                if start_line <= node.position.line <= end_line:
                    if isinstance(node, javalang.tree.VariableDeclarator):
                        variable_names.append(node.name)
            if hasattr(node, 'children'):
                stack.extend(node.children)
    return variable_names

def get_variable_name_counts(variable_names_set, variable_names):
    variable_name_counts = {variable_name: 0 for variable_name in variable_names_set}
    for variable_name in variable_names:
        if variable_name in variable_name_counts:
            variable_name_counts[variable_name] += 1
    return variable_name_counts

def readFiles(folder_path: str, group_length: int) -> tuple[pd.DataFrame, list[str], set[tuple[type, type]]]:
    data = []
    all_variable_names = set()
    all_node_types = set()
    for file_name in os.listdir(folder_path):
        if file_name.endswith(".java"):
            with open(os.path.join(folder_path, file_name), "r") as f:
                code = f.read()
                tree = parse(code)
                row_range_start = 1
                row_range_end = group_length
                for i in range(0, len(code.split("\n")), group_length):
                    start_line = i + 1
                    end_line = i + group_length
                    node_types = get_node_types(tree, start_line, end_line)
                    all_node_types.update(node_types)

                    lines = code.split("\n")[i:i + group_length]
                    total_chars = sum(len(line) for line in lines)
                    group_code = "\n".join(lines)
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
                    average_length = sum([len(line) for line in lines]) / len(lines)
                    whitespace_ratio = group_code.count(" ") / (total_chars - group_code.count(" "))

                    max_depth = max([len(path) for path, node in tree])
                    ast_node_depths = [len(path) for path, node in tree]
                    ast_node_avg_depth = sum(ast_node_depths) / len(ast_node_depths) if ast_node_depths else 0

                    variable_names = get_variable_names(tree, start_line, end_line)
                    all_variable_names.update(variable_names)

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

                    })
                    row_range_start += group_length
                    row_range_end += group_length

                    all_bigrams_set = set(zip(all_node_types, all_node_types))

    all_bigrams = set(zip(all_node_types, all_node_types))
    for row in data:
        row.update(get_bigram_counts(all_bigrams, node_types))
        row.update(get_variable_name_counts(all_variable_names, variable_names))

    df = pd.DataFrame(data)
    return df, all_bigrams_set


def readFilesWithBigrams(folder_path: str, group_length: int, bigrams: list[tuple[type, type]]) -> tuple[
    pd.DataFrame, list[str]]:
    data = []
    all_variable_names = set()
    for file_name in os.listdir(folder_path):
        if file_name.endswith(".java"):
            with open(os.path.join(folder_path, file_name), "r") as f:
                code = f.read()
                tree = parse(code)
                row_range_start = 1
                row_range_end = group_length
                for i in range(0, len(code.split("\n")), group_length):
                    start_line = i + 1
                    end_line = i + group_length
                    node_types = get_node_types(tree, start_line, end_line)

                    lines = code.split("\n")[i:i + group_length]
                    total_chars = sum(len(line) for line in lines)
                    group_code = "\n".join(lines)
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
                    average_length = sum([len(line) for line in lines]) / len(lines)
                    whitespace_ratio = group_code.count(" ") / (total_chars - group_code.count(" "))

                    max_depth = max([len(path) for path, node in tree])
                    ast_node_depths = [len(path) for path, node in tree]
                    ast_node_avg_depth = sum(ast_node_depths) / len(ast_node_depths) if ast_node_depths else 0

                    variable_names = get_variable_names(tree, start_line, end_line)
                    all_variable_names.update(variable_names)

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

                    })
                    row_range_start += group_length
                    row_range_end += group_length

    all_bigrams_set = set(bigrams)
    for row in data:
        row.update(get_bigram_counts(all_bigrams_set, node_types))
        row.update(get_variable_name_counts(all_variable_names, variable_names))

    df = pd.DataFrame(data)
    columns = list(df.columns)
    return df, columns


files = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data'

data, usedBigrams = readFiles(files, 20)

for num in range(78, 90):
    data.loc[num, 'Written'] = 1
for num in range(187, 209):
    data.loc[num, 'Written'] = 1


data = data.where(data.notnull(), 0)


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
clf = KNN(contamination = 0.17, n_neighbors = 80)
clf.fit(X)

yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)

#error file
fileScuffed = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data Output'
dataNew, newXCols = readFilesWithBigrams(fileScuffed, 20, usedBigrams)
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