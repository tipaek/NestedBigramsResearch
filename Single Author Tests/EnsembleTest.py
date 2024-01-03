#data
import pandas as pd
from sklearn.model_selection import train_test_split
import re

#ensembles
from lightgbm import LGBMClassifier
from xgboost import XGBClassifier
from catboost import CatBoostClassifier
from sklearn.ensemble import RandomForestClassifier

#evaluation
from sklearn.ensemble import VotingClassifier
from sklearn.metrics import roc_auc_score, f1_score, precision_score

import itertools
import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from Code.Helpers import hash_column, add_clustering

#features and normalization
from Code.FeatureExtraction import readFiles, readFilesWithEntropy
from Code.Helpers import normalize, clean_feature_names, check_special_characters

author = ['author one dataset', 'author two dataset']
author2 = [1]
group_sizes = [10, 20, 30, 40, 50, 60, 70]

for (path, number) in zip(author, author2):
    print(f'\n\n\n*****AUTHOR: {number}*****')
    for size in group_sizes:
        print(f'\ngroup size: {size}\n')
        data = pd.read_csv(f'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Datasets\\{path}\\CodeBeNB\\CodeBeNB.{number}.{size}, normalized.csv')
        print(f'\t {data.shape[0]}')
        print(f'\t {data.shape[1]}')
        drop = ['File Name', 'Written', 'Row Range', 'File Path']

        # Drop unusable columns and split data
        X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
        y = data['Written']

        # Rename columns by replacing any non-alphanumeric characters with a unique string and truncate
        X = X.rename(columns=hash_column)

        X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

        # make models for ensemble
        lgbm_clf = LGBMClassifier(random_state=42, verbose=-1)
        xgb_clf = XGBClassifier(random_state=42)
        catboost_clf = CatBoostClassifier(random_state=42, verbose=False)
        rf_clf = RandomForestClassifier(random_state=42)

        voting_clf = VotingClassifier(estimators=[('lgbm', lgbm_clf), ('xgb', xgb_clf), ('catboost', catboost_clf), ('rf', rf_clf)], voting='soft')

        for clf in (lgbm_clf, xgb_clf, catboost_clf, rf_clf, voting_clf):
            # fit data
            clf.fit(X_train, y_train)

            # make predictions
            y_pred = clf.predict(X_test)
            y_pred_proba = clf.predict_proba(X_test)[:, 1]

            # evaluation using ROC_AUC, F1, and Precision(basically)
            roc_auc = roc_auc_score(y_test, y_pred_proba)
            f1 = f1_score(y_test, y_pred)
            accuracy = clf.score(X_test, y_test)
            precision = precision_score(y_test, y_pred)
            print(clf.__class__.__name__)
            print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')



