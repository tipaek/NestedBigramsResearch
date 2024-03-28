#experiment logging
from comet_ml import Experiment
from comet_ml.integration.sklearn import log_model
from config import API_KEY

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
           'RF AUC', 'RF F-1 Score', 'RF Accuracy', 'RF Precision']


group_sizes = [10, 20, 30, 40, 50, 60, 70]
#group_sizes = [2000, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100]
#group_sizes = [5000, 4500, 4000, 3500, 3000, 2500]
#paths = [r"C:\Users\tipaek\OneDrive - Syracuse University\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite\NB\Base\GPT.NB.Base"]
paths = [r"C:\Users\tipaek\OneDrive - Syracuse University\Desktop\Research\NestedBigramsResearch\Datasets\40Authors\NB\40.NB.Base"]

testing_data = []

experiment = Experiment(
  api_key=API_KEY,
  project_name="anomaly-detection-research",
  workspace="tipaek",
)
#experiment.add_tags(["GPT - Rewrite, NB.Base", "GPT - Rewrite", "NB", "ensembles"])
experiment.add_tags(["40 Authors, NB.Base", "40 Authors", "ensembles", "NB"])

for path in paths:
    for size in group_sizes:
        curr_path = f'{path}.G{size}.csv'
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
        

        testing_data.append(size)

        for clf in (lgbm_clf, xgb_clf, catboost_clf, rf_clf):
            clf_name = type(clf).__name__
         
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
                "group_size": size,
                "dimensions": X.shape[1],
                "40.roc_auc": roc_auc,
                "40.f1": f1,
                "40.accuracy": accuracy,
                "40.precision": precision,
                "width": 3000,
                "clf": clf_name
                })
            experiment.log_confusion_matrix(labels=["Not Anomalous", "Anomalous"], matrix=cm)
            
            
            
experiment.end()

#df = pd.DataFrame(testing_data, columns=columns)
#df.to_csv('NB+N.EqualWidthBinning.60.csv', index=False)




