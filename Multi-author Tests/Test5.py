import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtraction import readFiles, readFilesWithBigrams, readFilesWithHierarchical, readFilesWithEntropy, readFilesWithEntropy2
from Code.Helpers import normalize

#file directory containing both regular and anomalous java code
files = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Data\15authors'

#getting features and giving 0/1 classes where 1 is anomalous
data, usedBigrams = readFilesWithEntropy2(files, 70)

#data.loc[data['File Name'] == 'java1.java', 'Written'] = 1
#data.loc[data['File Name'] == 'fault.java', 'Written'] = 1
#data.loc[data['File Name'] == 'tic-tac-toe.java', 'Written'] = 1
#data.loc[data['File Name'] == 'Anomalous.java', 'Written'] = 1
data = normalize(data)

data.to_csv('15, 70 Entropy, Normalized.csv', index=False)


#getting data for the kNN by removing unnecessary columns
xcols = list(data.columns)
xcols.remove('Row Range')
xcols.remove('Written')
xcols.remove('File Name')
xcols.remove('File Path')

X = data[xcols].values
y = data['Written'].values
print(data.shape[0])
print(data.shape[1])


#kNN fitting using contamination found from 100 line groupings and evaluation
clf = KNN(contamination = 0.11, n_neighbors = 34)
clf.fit(X)

yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)






