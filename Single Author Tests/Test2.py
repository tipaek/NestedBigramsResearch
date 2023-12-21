import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtraction import *
from Code.Helpers import *


# file directory containing both regular and anomalous java code
files = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Data\one'

group_sizes = [10, 10, 10, 10, 10, 20, 20, 20, 20, 20, 30, 30, 30, 30, 30, 40, 40, 40, 40, 40, 50, 50, 50, 50, 50]
neighbors = [23,47,72,94,118,12,24,36,48,61,8,17,25,34,43,6,13,20,26,33,5,11,16,22,27]
#two:neighbors = [23,47,70,94,118,12,24,36,48,61,8,16,25,33,42,6,13,20,26,33,5,11,16,22,27]

index = 0
for group_size, n_neighbor in zip(group_sizes, neighbors):
    if(index == 0 or group_sizes[index-1] != group_size):
        print(f'\n\n*****{group_size}*****')
    index+=1
    # getting features and giving 0/1 classes where 1 is anomalous
    data, usedBigrams = readFilesWithEntropy(files, group_size)
    data['Written'] = 0

    data.loc[data['File Name'] == 'java1.java', 'Written'] = 1
    data.loc[data['File Name'] == 'fault.java', 'Written'] = 1
    data.loc[data['File Name'] == 'tic-tac-toe.java', 'Written'] = 1
    data.loc[data['File Name'] == 'Anomalous.java', 'Written'] = 1

    data = normalize(data)

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

    print(f'{group_size}: {n_neighbor}')
    evaluate_print('KNN', y, yTrainOutput)
