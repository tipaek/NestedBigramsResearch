from javalang.tree import *

import pandas as pd

from collections import Counter
import numpy as np
import re

import hashlib
import base64

from scipy.stats import entropy

from sklearn.cluster import KMeans, OPTICS
from kneed import KneeLocator

import torch
import copy
from pyclustering.cluster import xmeans
from pyclustering.cluster.center_initializer import kmeans_plusplus_initializer


# generates bigrams from start_line <= node.position.line <= end_line given ast node(in this case, it's the entirety of the tree)
from sklearn.metrics import make_scorer, accuracy_score, precision_score, f1_score
from sklearn.model_selection import cross_val_score, cross_validate
from sklearn.preprocessing import StandardScaler

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

# returns dictionary of the bigrams(bigram, frequency)
def bigram_freq(bigrams):
    return Counter(bigrams)

# converts bigrams to strings for feature labels in dataframe (doesn't include empty lists)
def bigram_to_str(bigram):
    result = []
    for node in bigram:
        attrs = []
        for attr, value in node.__dict__.items():
            if value != []:
                attrs.append(f'{attr}={value}')
        result.append(f'{node.__class__.__name__}({", ".join(attrs)})')
    return f'({", ".join(result)})'

# nodes in range from ast
def get_nodes_in_range(node, start_line, end_line):
    result = []
    if isinstance(node, list):
        for child in node:
            result.extend(get_nodes_in_range(child, start_line, end_line))
    elif isinstance(node, Node):
        if node.position is not None and start_line <= node.position.line <= end_line:
            result.append(node)
        for child in node.children:
            if isinstance(child, list):
                for grandchild in child:
                    result.extend(get_nodes_in_range(grandchild, start_line, end_line))
            elif isinstance(child, Node):
                result.extend(get_nodes_in_range(child, start_line, end_line))
    return result

# encode nodes using BERT using GPU and batch processing to speed up time
def encode_nodes_with_BERT(nodes, tokenizer, model, batch_size=100):
    result = []
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    model.to(device)
    for i in range(0, len(nodes), batch_size):
        batch = nodes[i:i+batch_size]
        inputs = []
        max_length = 0
        for node in batch:
            #print(node)
            try:
                source_code = str(node)
            except AttributeError:
                source_code = f"{node.__class__.__name__} at line {node.position.line}"
            input_ids = tokenizer.encode(source_code, return_tensors='pt', max_length=512, truncation=True)
            inputs.append(input_ids)
            max_length = max(max_length, input_ids.shape[1])
        padded_inputs = []
        for input_ids in inputs:
            padding_length = max_length - input_ids.shape[1]
            padding = torch.zeros((1, padding_length), dtype=torch.long)
            padded_input_ids = torch.cat((input_ids, padding), dim=1)
            padded_inputs.append(padded_input_ids)
        inputs = torch.cat(padded_inputs).to(device)
        with torch.no_grad():
            outputs = model(inputs)
            embs = outputs.last_hidden_state[:, 0, :].cpu().numpy()
        result.extend(list(zip(batch, embs)))
    return result

# returns a list of features to add to the dataframe
def get_features(code_group):
    whitespace_ratio = code_group.count(' ') / len(code_group)
    statements = sum(code_group.count(statement) for statement in ["if", "else if", "else", "for", "while", "switch"]) / len(
        code_group)
    tabs = code_group.count('\t') / len(code_group)
    empty_lines = code_group.count('\n\n') / len(code_group)
    underscores = code_group.count('_') / len(code_group)
    mean_line_length = (np.mean([len(line) for line in code_group.split('\n')])) / len(code_group)

    # Extract all comments from the code group
    comments = re.findall(r'//.*|/\*.*?\*/', code_group, re.DOTALL)

    # Calculate the mean length of comments, if there are any comments
    mean_comment_length = (np.mean([len(comment) for comment in comments]) if comments else 0) / len(code_group)

    return [whitespace_ratio, statements, tabs, underscores, empty_lines, mean_line_length, mean_comment_length]

def normalize(df):
    ignore_cols = ['Row Range', 'Written', 'File Name', 'File Path']

    # Create a copy of the input dataframe to avoid modifying it
    df_normalized = df.copy()
    #df.columns = df.columns.astype(str)

    # Normalize each column in the dataframe, ignoring the specified columns and columns with non-numeric data types
    for col in df.columns:
        if col not in ignore_cols:
            # Calculate the 5th and 95th percentiles of the column
            xmin = df[col].quantile(0.05)
            xmax = df[col].quantile(0.95)
            #print(f'xmax: {xmax} \nmxmin: {xmin}')
            # Normalize the values in the column using the specified equation, if xmax - xmin is not 0
            if type(xmax) == float and xmax - xmin != 0:
                df_normalized[col] = (df[col] - xmin) / (xmax - xmin)
    return df_normalized

# calculates divergence using entropy function and normalizes by / number of characters
def calculate_kl_divergence(df, ignore_cols, code_group_lengths, eps=1e-9):
    # Calculate the standard deviation of columns for rows with 'Written' of 0 and 1, ignoring the specified columns
    dist_0 = df[df['Written'] == 0].drop(ignore_cols, axis=1).mean() + eps
    dist_1 = df[df['Written'] == 1].drop(ignore_cols, axis=1).mean() + eps

    # Calculate the KL divergence between the standard deviation of each row and std_0 and std_1
    kl_divergence_0 = []
    kl_divergence_1 = []
    for i, row in df.drop(ignore_cols, axis=1).iterrows():
        dist_row = row + eps
        kl_divergence_0.append(entropy(dist_row, dist_0))
        kl_divergence_1.append(entropy(dist_row, dist_1))

    # Normalize the KL divergence by dividing by the length of each code group
    normalized_kl_divergence_0 = [kl_div / length for kl_div, length in zip(kl_divergence_0, code_group_lengths)]
    normalized_kl_divergence_1 = [kl_div / length for kl_div, length in zip(kl_divergence_1, code_group_lengths)]
    return normalized_kl_divergence_0, normalized_kl_divergence_1

# same as above except is with respect to the cluster labels instead(note: this is computationally pricey)
def calculate_kl_divergence2(df, ignore_cols, code_group_lengths, eps=1e-9):
    pd.set_option('display.max_rows', None)
    df_copy = df.copy()
    # Calculate the standard deviation of columns for each cluster, ignoring the specified columns
    dists = [df_copy[df_copy['Cluster'] == cluster].drop(ignore_cols, axis=1).mean() for cluster in df['Cluster'].unique()]
    dists = [(np.abs(dist) + eps) / np.sum(dist.abs() + eps) for dist in dists]

    # Calculate the KL divergence between the standard deviation of each row and each cluster
    kl_divergences = []
    for i, row in df_copy.drop(ignore_cols, axis=1).iterrows():
        dist_row = np.abs(row) + eps
        kl_divergence = [entropy(dist_row, dist) for dist in dists]
        kl_divergences.append(kl_divergence)

    # Normalize the KL divergence by dividing by the length of each code group
    normalized_kl_divergences = [[kl_div / code_group_lengths[i] for kl_div in kl_divergence] for kl_divergence in kl_divergences]

    # Add normalized KL divergence values to the dataframe
    for i, cluster in enumerate(df['Cluster'].unique()):
        df[f'KL Divergence {cluster}'] = [kl_div[i] for kl_div in normalized_kl_divergences]

    return df



# gets rid of special characters in feature labels to be used for fitting for some networks that don't like that
def clean_feature_names(feature_names):
    remove_chars = '[]{}:,()_'
    replace_chars = {
        '(': 'oParen',
        ')': 'cParen'
    }

    # clean labels
    cleaned_feature_names = []
    for feature_name in feature_names:
        for char in remove_chars:
            feature_name = feature_name.replace(char, '')
        for char, replacement in replace_chars.items():
            feature_name = feature_name.replace(char, replacement)
        cleaned_feature_names.append(feature_name)
    return cleaned_feature_names

# Define a function to check for special characters
def check_special_characters(feature_names):
    # Define the special characters to check for
    special_chars = '[]{}:,'
    # Check for special characters
    for feature_name in feature_names:
        for char in special_chars:
            if char in feature_name:
                print(f'Feature name "{feature_name}" contains special character "{char}"')

def hash_column(column):
    # Hash the column name using SHA-256
    hash = hashlib.sha256(column.encode('utf-8')).digest()
    # Encode the hash using Base64
    encoded = base64.b64encode(hash).decode('utf-8')
    # Remove any special characters from the encoded string
    encoded = re.sub('[^A-Za-z0-9_]+', '_', encoded)
    return encoded

#adds kMeans clustering
def add_clustering(df, ignore_features, n_clusters=None):
    # Create a copy of the dataframe and drop the specified features
    df_copy = df.copy()
    df_copy = df_copy.drop(ignore_features, axis=1)

    # Scale the data
    scaler = StandardScaler()
    data = scaler.fit_transform(df_copy)

    # Determine the number of clusters using the elbow method if n_clusters is not specified
    if n_clusters is None:
        distortions = []
        for i in range(1, 11):
            kmeans = KMeans(n_clusters=i)
            kmeans.fit(data)
            distortions.append(kmeans.inertia_)
        n_clusters = KneeLocator(range(1, 11), distortions, curve='convex', direction='decreasing').elbow

    # Cluster the data using KMeans
    kmeans = KMeans(n_clusters=n_clusters)
    clusters = kmeans.fit_predict(data)

    # Add the cluster feature to the original dataframe
    df['Cluster'] = clusters

    return df

#adds xmeans clustering
def add_xmeans_clusters(df, ignore_cols):
    # Prepare data for clustering
    X = df.drop(ignore_cols, axis=1).values

    # Initialize initial centers using K-Means++ method.
    initial_centers = kmeans_plusplus_initializer(X, 2).initialize()

    # Create instance of X-Means algorithm. The algorithm will start analysis from 2 clusters.
    xm = xmeans.xmeans(X, initial_centers)

    # Run cluster analysis and obtain results.
    xm.process()
    clusters = xm.get_clusters()

    # Add cluster labels to the dataframe
    df['Cluster'] = [label for cluster in clusters for label in cluster]

def add_optics_clusters(df, ignore_cols, size):
    X = df.drop(ignore_cols, axis=1).values

    optics = OPTICS(min_samples=2*int(np.sqrt(size)))
    optics.fit(X)
    clusters = optics.labels_

    df['Cluster'] = clusters

















