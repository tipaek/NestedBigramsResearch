import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtraction import readFiles, readFilesWithBigrams, readFilesWithHierarchical, readFilesWithEntropy, readFilesWithBERT, readFilesWithEntropy2
from Code.Helpers import normalize
import torch


print(torch.cuda.is_available())

#file directory containing both regular and anomalous java code
files = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Data\two'

#getting features and giving 0/1 classes where 1 is anomalous
data = readFilesWithBERT(files, 70)
#data['Written'] = 0

#data.loc[data['File Name'] == 'java1.java', 'Written'] = 1
#data.loc[data['File Name'] == 'fault.java', 'Written'] = 1
#data.loc[data['File Name'] == 'tic-tac-toe.java', 'Written'] = 1
#data.loc[data['File Name'] == 'Anomalous.java', 'Written'] = 1


data = normalize(data)
data.to_csv('Bert, two, 30 weighted.csv', index=False)

#getting data for the kNN by removing unecessary columns
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



###############################################################################
###############################################################################


#file directory for testing
testFiles = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Test\one'
#getting features and preparing data
dataNew, newXCols = readFilesWithBigrams(testFiles, 30, usedBigrams)
newXCols.remove('Row Range')
newXCols.remove('File Name')

newX = dataNew[newXCols].values
anomalyScores = clf.decision_function(newX)

threshold = 0.05

predictions = (anomalyScores > threshold).astype(int)

#Print predictions
for i in range(len(predictions)):
    prediction = predictions[i]
    file_name = dataNew.iloc[i]['File Name']
    row_range = dataNew.iloc[i]['Row Range']
    anomaly_score = anomalyScores[i]

    if prediction == 0:
        print(f'{i}: File Name: {file_name}, Row Range: {row_range} \n normal with Anomaly Score: {anomaly_score}'
              f'\n-------------------------------------------------')
    else:
        print(f'{i}: File Name: {file_name}, Row Range: {row_range} \n anomalous with Anomaly Score: {anomaly_score}'
              f'\n-------------------------------------------------')






# fit data
    voting_clf.fit(X_train, y_train)

    # make predictions
    y_pred = voting_clf.predict(X_test)
    y_pred_proba = voting_clf.predict_proba(X_test)[:, 1]

    # evaluation using ROC_AUC, F1, and Precision(basically)
    roc_auc = roc_auc_score(y_test, y_pred_proba)
    f1 = f1_score(y_test, y_pred)
    accuracy = voting_clf.score(X_test, y_test)
    precision = precision_score(y_test, y_pred)
    print(clf.__class__.__name__)
    print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')