# -*- coding: utf-8 -*-
"""
Created on Sun Mar 17 14:34:00 2024

@author: tipaek
"""

# experiment logging
from comet_ml import Experiment
from comet_ml.integration.sklearn import log_model
from config import API_KEY

#data
import pandas as pd
from sklearn.model_selection import train_test_split

#dimensionality reduction
from keras.layers import Input, Dense
from keras.models import Model
from sklearn.decomposition import PCA
import numpy as np

#ensembles
from sklearn import svm
from sklearn.ensemble import RandomForestClassifier

#evaluation
from sklearn.metrics import roc_auc_score, f1_score, precision_score

import itertools
import sys
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')
#sys.path.insert(0, 'C:\\Users\\tipaek\OneDrive - Syracuse University\Desktop\\Research\\NestedBigramsResearch')
sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch')
import gc

from Code.Helpers import hash_column, add_clustering

#features and normalization
from Code.FeatureExtraction import readFiles, readFilesWithEntropy
from Code.Helpers import normalize, clean_feature_names, check_special_characters

from sklearn.preprocessing import MinMaxScaler

from keras import backend as K


columns = ['Encoding Dim', 'AUC', 'F-1', 'Accuracy', 'Precision']


encoding_dimensions = [0.2, 0.4, 0.6, 0.8, 1]
paths = [10, 20, 30, 40, 50, 60, 70]

testing_data = []
kernel = 'linear'

experiment = Experiment(
  api_key=API_KEY,
  project_name="anomaly-detection-research",
  workspace="tipaek",
)
experiment.add_tags(["GPT - Rewrite, NB.Base", "GPT - Rewrite", "NB", "PCA", "rf"])

for path in paths:
    testing_data=[]
    temp_path = f'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch\\Datasets\\GPT - Rewrite\\NB\\GPT.NB.Base.{path}.csv'
    print(f'\nPATH: {temp_path}')
    data = pd.read_csv(temp_path)
    print(f'`\tInitial Shape: {data.shape[0]}\n\t {data.shape[1]}')

    drop = ['File Name', 'Written', 'Row Range', 'File Path']

    X = data.drop(drop, axis=1)
    y = data['Written']

    X = X.rename(columns=hash_column)
    scaler = MinMaxScaler()
    X = pd.DataFrame(scaler.fit_transform(X), columns=X.columns)
    

    X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)
    
    pca_initial = PCA()
    pca_initial_x = pca_initial.fit_transform(X_train)
    var_cumsum = np.cumsum(pca_initial.explained_variance_ratio_)
    


    for size in encoding_dimensions:

        #encoding_dim = int(size * data.shape[1])
        
       
        indices = np.where(var_cumsum > size)[0]
        if indices.size > 0:
            encoding_dim = indices[0]
        else:
            encoding_dim = len(var_cumsum)  # use all components if none satisfy the condition
        X_temp = X_train.values
        X_temp_test = X_test.values

        pca = PCA(n_components=encoding_dim,random_state=42)
        reduced_input = pca.fit_transform(X_temp)
        reduced_input_test = pca.transform(X_temp_test)


        print(f'Modified Shape: {reduced_input.shape[1]}')

        #testing_data.append(reduced_input.shape[1])

        # Random Forest testing with reduced dimensionality
        rf = RandomForestClassifier(random_state=42)

        rf.fit(reduced_input, y_train)

        y_pred = rf.predict(reduced_input_test)
        y_pred_proba = rf.predict_proba(reduced_input_test)[:, 1]

        roc_auc = roc_auc_score(y_test, y_pred_proba)
        f1 = f1_score(y_test, y_pred)
        accuracy = rf.score(reduced_input_test, y_test)
        precision = precision_score(y_test, y_pred)
        print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')

        testing_data.append([reduced_input.shape[1], roc_auc, f1, accuracy, precision])
        
        experiment.log_metrics({
            f"{path}.group_size": path,
            f"{path}.dimensions": encoding_dim,
            f"{path}.percentage": size,
            f"{path}.epochs": 60,
            f"{path}.roc_auc": roc_auc,
            f"{path}.f1": f1,
            f"{path}.accuracy": accuracy,
            f"{path}.precision": precision
            })
        
        # clear up memory
        del pca, X_temp, X_temp_test, rf, reduced_input, reduced_input_test, y_pred, y_pred_proba
        gc.collect()
        
        K.clear_session()
    
    del X, X_train, X_test, y_train, y_test, y
    gc.collect()

    df = pd.DataFrame(testing_data, columns=columns)
    df.to_csv(f'GPT.NB.PCA.{path}.csv', index=False)
    
experiment.end()




