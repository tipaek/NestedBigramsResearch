import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtraction import *


import torch

if torch.cuda.is_available():
    print("CUDA is available!")
    device = torch.device("cuda")
    x = torch.randn(1)
    x = x.to(device)
    print(x)
else:
    print("CUDA is not available.")

# file directory containing both regular and anomalous java code
files = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\two'

group_sizes = [10, 20, 30, 40, 50]
neighbors = [94, 48, 33, 26, 22]

for group_size, n_neighbor in zip(group_sizes, neighbors):
    print(f'************\nGroup Size: {group_size} || Number of Neighbors: {n_neighbor}:')
    for n_cluster in range(2, 11):
        # getting features and giving 0/1 classes where 1 is anomalous
        data, usedBigrams = readFilesWithKMedoids(files, group_size, n_cluster)
        data['Written'] = 0

        data.loc[data['File Name'] == 'java1.java', 'Written'] = 1
        data.loc[data['File Name'] == 'fault.java', 'Written'] = 1
        data.loc[data['File Name'] == 'tic-tac-toe.java', 'Written'] = 1

        # getting data for the kNN by removing unecessary columns
        xcols = list(data.columns)
        xcols.remove('Row Range')
        xcols.remove('Written')
        xcols.remove('File Name')

        X = data[xcols].values
        y = data['Written'].values

        # kNN fitting using contamination found from group size and evaluation
        clf = KNN(contamination=0.11, n_neighbors=n_neighbor)
        clf.fit(X)

        yTrainPred = clf.labels_
        yTrainOutput = clf.decision_scores_

        print(f'Number of clusters: {n_cluster}')
        evaluate_print('KNN', y, yTrainOutput)
        print('\n')
