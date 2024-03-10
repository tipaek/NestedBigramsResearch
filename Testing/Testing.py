#experiment logging
from comet_ml import Experiment
from comet_ml.integration.sklearn import log_model
from config.py import API_KEY

#data
import pandas as pd
from sklearn.model_selection import train_test_split
import re

#ensembles
from lightgbm import LGBMClassifier
from xgboost import XGBClassifier
from catboost import CatBoostClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn import svm

#evaluation
from sklearn.ensemble import VotingClassifier
from sklearn.metrics import roc_auc_score, f1_score, precision_score, confusion_matrix

import itertools
import sys
sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch')
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from Code.Helpers import hash_column, add_clustering

#features and normalization
from Code.FeatureExtraction import readFiles, readFilesWithEntropy
from Code.Helpers import normalize, clean_feature_names, check_special_characters




columns = ['Group Size',
           'LGBM AUC', 'LGBM F-1 Score', 'LGBM Accuracy', 'LGBM Precision',
           'XGB AUC', 'XGB F-1 Score', 'XGB Accuracy', 'XGB Precision',
           'CatBoost AUC', 'CatBoost F-1 Score', 'CatBoost Accuracy', 'CatBoost Precision',
           'RF AUC', 'RF F-1 Score', 'RF Accuracy', 'RF Precision',
           'RBF SVM AUC', 'RBF SVM F-1 Score', 'RBF SVM Accuracy', 'RBF SVM Precision',
           'Sigmoid SVM AUC', 'Sigmoid SVM F-1 Score', 'Sigmoid SVM Accuracy', 'Sigmoid SVM Precision']


group_sizes = [10, 20, 30, 40, 50, 60, 70]
paths = [r"C:\Users\tipaek\OneDrive - Syracuse University\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite\NB-Base\GPT.NB-base"]

testing_data = []

for path in paths:
    for size in group_sizes:
        curr_path = f'{path}.{size}.csv'
        print(f'\nPATH: {curr_path}')
        
        data = pd.read_csv(curr_path)
        print(f'`t {data.shape[0]}\n\t {data.shape[1]}')
        drop = ['File Name', 'Written', 'Row Range', 'File Path']

        X = data.drop(drop, axis=1)
        y = data['Written']

        X = X.rename(columns=hash_column)

        X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

        
        lgbm_clf =LGBMClassifier(random_state=42, verbose=-1)
        xgb_clf = XGBClassifier(random_state=42)
        catboost_clf = CatBoostClassifier(random_state=42, verbose=False)
        rf_clf = RandomForestClassifier(random_state=42)
        rbf_svm = svm.SVC(kernel='rbf', random_state=42, probability=True) 
        sigmoid_svm = svm.SVC(kernel='sigmoid', random_state=42, probability=True)
        

        testing_data.append(size)

        for clf in (lgbm_clf, xgb_clf, catboost_clf, rf_clf, rbf_svm, sigmoid_svm):
            clf_name = type(clf).__name__
            
            experiment = Experiment(
              api_key=API_KEY,
              project_name="anomaly-detection-research",
              workspace="tipaek",
            )
            
            experiment.add_tags([clf_name, f"GPT - Rewrite, NB-Base.1, Size: {size}, {clf_name}", size, "GPT - Rewrite", "NB-Base.1"])
            
            clf.fit(X_train, y_train)

            y_pred = clf.predict(X_test)
            y_pred_proba = clf.predict_proba(X_test)[:, 1]

            roc_auc = roc_auc_score(y_test, y_pred_proba)
            f1 = f1_score(y_test, y_pred)
            accuracy = clf.score(X_test, y_test)
            precision = precision_score(y_test, y_pred)
            
            cm = confusion_matrix(y_test, y_pred)

            testing_data.append([roc_auc, f1, accuracy, precision])
            
            experiment.log_metrics({
                "roc_auc": roc_auc,
                "f1": f1,
                "accuracy": accuracy,
                "precision": precision
                })
            experiment.log_confusion_matrix(labels=["Not Anomalous", "Anomalous"], matrix=cm)
            
            experiment.end()
            
            

df = pd.DataFrame(testing_data, columns=columns)
df.to_csv(f'NB-Base.1.csv', index=False)




