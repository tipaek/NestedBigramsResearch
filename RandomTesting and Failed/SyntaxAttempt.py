#now doing the same thing as before except using clustering in the data initially
from pyod.models.knn import KNN
from pyod.utils import evaluate_print

#method for making pd dataframe
import pandas as pd
import math
import re

#for the clustering beforehand, both finding the centroids and adding it to the data
from scipy.cluster.vq import kmeans, vq

#for finding the distance from datapoints to nearest centroid
from scipy.spatial.distance import cdist

#for abstract syntax trees
import javalang
from javalang.tree import (
    Node, CompilationUnit, Import, Documented, Declaration, TypeDeclaration,
    PackageDeclaration, ClassDeclaration, EnumDeclaration, InterfaceDeclaration,
    AnnotationDeclaration, Type, BasicType, ReferenceType, TypeArgument,
    TypeParameter, Annotation, ElementValuePair, ElementArrayValue,
    Member, MethodDeclaration, FieldDeclaration, ConstructorDeclaration,
    ConstantDeclaration, ArrayInitializer, VariableDeclaration,
    LocalVariableDeclaration, VariableDeclarator, FormalParameter,
    InferredFormalParameter, Statement, IfStatement, WhileStatement,
    DoStatement, ForStatement, AssertStatement, BreakStatement,
    ContinueStatement, ReturnStatement, ThrowStatement,
    SynchronizedStatement, TryStatement, SwitchStatement,
    BlockStatement, StatementExpression,TryResource,CatchClause,
    CatchClauseParameter ,SwitchStatementCase ,ForControl ,
    EnhancedForControl ,Expression ,Assignment ,TernaryExpression ,
    BinaryOperation ,Cast ,MethodReference ,LambdaExpression ,
    Primary ,Literal ,This ,MemberReference ,Invocation ,
    ExplicitConstructorInvocation ,SuperConstructorInvocation ,
    MethodInvocation ,SuperMethodInvocation ,SuperMemberReference ,
    ArraySelector ,ClassReference ,VoidClassReference ,
    Creator ,ArrayCreator ,ClassCreator ,InnerClassCreator ,
    EnumBody ,EnumConstantDeclaration ,AnnotationMethod
)

from javalang.tree import *
from collections import Counter

#for the input files
import os

def count_node_types(tree):
    node_types = [type(node) for node in tree]
    return Counter(node_types)

def average_branching_factor(tree):
    num_nodes = 0
    num_children = 0
    for path, node in tree:
        num_nodes += 1
        num_children += len(node.children)
    if num_nodes == 0:
        return 0
    else:
        return num_children / num_nodes

def readFilesSyntactical2(folderPath, groupSize):
    filePaths = [os.path.join(folderPath, f) for f in os.listdir(folderPath) if f.endswith('.java')]
    data = []
    allVarNames = set()
    allNodeTypes = set()
    for file_path in filePaths:
        with open(file_path, 'r') as f:
            text = f.read()
            try:
                tree = javalang.parse.parse(text)
            except javalang.parser.JavaSyntaxError:
                print(f'Warning: Syntax error encountered while parsing file {file_path}')
                continue

            # Extract line numbers for each node in the AST
            line_numbers = {}
            for path, node in tree:
                if hasattr(node, 'position') and node.position:
                    line_numbers[node] = node.position.line

            # Group nodes by line number
            groups = {}
            for node, line_number in line_numbers.items():
                group_index = (line_number - 1) // groupSize
                if group_index not in groups:
                    groups[group_index] = []
                groups[group_index].append(node)

            # Process each group of nodes
            for group_index, nodes in groups.items():
                start_line = group_index * groupSize + 1
                end_line = start_line + groupSize - 1
                rowRange = f'{start_line}-{end_line}'

                # Count node types
                node_type_counts = Counter(type(node) for node in nodes)
                allNodeTypes.update(node_type_counts.keys())

                # Compute average branching factor
                num_nodes = len(nodes)
                num_children = sum(len(node.children) for node in nodes)
                avg_branching_factor = num_children / num_nodes if num_nodes > 0 else 0

                # Extract variable names
                group_text = '\n'.join(text.splitlines()[start_line - 1:end_line])
                var_names = re.findall(r'\b\w+\b(?=\s*=)', group_text)
                freq = {}
                for name in var_names:
                    if name not in freq:
                        freq[name] = 0
                    freq[name] += 1
                    allVarNames.add(name)

                data.append([rowRange, node_type_counts, avg_branching_factor, freq, 0])

                # Debugging statements
                #print(f'File path: {file_path}')
                #print(f'Row range: {rowRange}')
                #print(f'Node type counts: {node_type_counts}')
                #print(f'Average branching factor: {avg_branching_factor}')
                #print(f'Variable name frequencies: {freq}')

    columns = ['rowRange'] + [str(t) for t in allNodeTypes] + ['AvgBranchingFactor'] + list(allVarNames) + ['Written']
    df = pd.DataFrame(columns=columns)
    for row in data:
        row_range, node_type_counts, avg_branching_factor, freq, written = row
        new_row = [row_range] + [node_type_counts.get(t, 0) for t in allNodeTypes] + [avg_branching_factor] + [
            freq.get(name, 0) for name in allVarNames] + [written]
        df.loc[len(df)] = new_row
    return df


def readFilesSyntactical3(folderPath, groupSize, columns):
    filePaths = [os.path.join(folderPath, f) for f in os.listdir(folderPath) if f.endswith('.java')]
    print(f'Found {len(filePaths)} Java files in folder {folderPath}')

    data = []
    allVarNames = set()
    allNodeTypes = set()
    for file_path in filePaths:
        with open(file_path, 'r') as f:
            text = f.read()
            try:
                tree = javalang.parse.parse(text)
            except javalang.parser.JavaSyntaxError:
                print(f'Warning: Syntax error encountered while parsing file {file_path}')
                continue

            # Extract line numbers for each node in the AST
            line_numbers = {}
            for path, node in tree:
                if hasattr(node, 'position') and node.position:
                    line_numbers[node] = node.position.line

            # Group nodes by line number
            groups = {}
            for node, line_number in line_numbers.items():
                group_index = (line_number - 1) // groupSize
                if group_index not in groups:
                    groups[group_index] = []
                groups[group_index].append(node)

            # Process each group of nodes
            for group_index, nodes in groups.items():
                start_line = group_index * groupSize + 1
                end_line = start_line + groupSize - 1
                rowRange = f'{start_line}-{end_line}'

                # Count node types
                node_type_counts = Counter(type(node) for node in nodes)
                allNodeTypes.update(node_type_counts.keys())

                # Compute average branching factor
                num_nodes = len(nodes)
                num_children = sum(len(node.children) for node in nodes)
                avg_branching_factor = num_children / num_nodes if num_nodes > 0 else 0

                # Extract variable names
                group_text = '\n'.join(text.splitlines()[start_line - 1:end_line])
                var_names = re.findall(r'\b\w+\b(?=\s*=)', group_text)
                freq = {}
                for name in var_names:
                    if name not in freq:
                        freq[name] = 0
                    freq[name] += 1
                    allVarNames.add(name)

                data.append([rowRange, node_type_counts, avg_branching_factor, freq, 0])

        print(f'Processed {len(data)} rows of data from file {file_path}')

    df = pd.DataFrame(columns=columns)
    for row in data:
        row_range, node_type_counts, avg_branching_factor, freq, written = row
        new_row = [row_range]
        for col in columns[1:]:
            if col == 'AvgBranchingFactor':
                new_row.append(avg_branching_factor)
            elif col == 'Written':
                new_row.append(written)
            elif col.startswith('<class'):
                class_name = col.split("'")[1]
                module_name, _, class_name = class_name.rpartition('.')
                module = __import__(module_name)

                # Check if the class exists before trying to access it
                if hasattr(module, class_name):
                    t = getattr(module, class_name)
                    new_row.append(node_type_counts.get(t, 0))
                else:
                    new_row.append(0)

            else:
                name = col
                new_row.append(freq.get(name, 0))
        df.loc[len(df)] = new_row

    # Ensure that the 'rowRange' column is always present in the output DataFrame
    if 'rowRange' not in df.columns:
        df.insert(0, 'rowRange', None)

    return df


#getting data
files = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data'

data = readFilesSyntactical2(files, 20)

#I intentionally put code not written by the author in the last 4 groups
for num in range(23, 27):
    data.loc[num, 'Written'] = 1

data = data.dropna()

print(data)

#Separate data
xCols = list(data.columns)
xCols.remove('rowRange')
xCols.remove('Written')
X = data[xCols].values
y = data['Written'].values

#Train kNN detector
clf = KNN(contamination = 0.08, n_neighbors = 8)
clf.fit(X)

#Print
yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)

#File not written by author and anomalyValues of said file
fileScuffed = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data Output'

if 'Written' not in xCols:
    xCols.append('Written')
dataNew = readFilesSyntactical3(fileScuffed, 20, xCols)
print(f'dataNew columns: {dataNew.columns}')
newXCols = list(dataNew.columns)
print(f'newXCols: {newXCols}')
newXCols.remove('rowRange')
newXCols.remove('Written')

dataNew = dataNew.dropna()

newXCols = list(dataNew.columns)
newX = dataNew[newXCols].values

print(f'newX: {newX}')
anomalyScores = clf.decision_function(newX)


#Print the ones that are above threshold
threshold = 0.8

predictions = (anomalyScores > threshold).astype(int)

#Print predictions
for i in range(len(predictions)):
    prediction = predictions[i]
    if prediction == 0:
        print(f'Instance {i} is normal')
    else:
        print(f'Instance {i} is anomalous')
