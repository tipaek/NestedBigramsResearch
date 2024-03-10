# experiment logging
from comet_ml import Experiment
from comet_ml.integration.sklearn import log_model
from config.py import API_KEY

#data
import pandas as pd
from sklearn.model_selection import train_test_split

#dimensionality reduction
from keras.layers import Input, Dense
from keras.models import Model

#ensembles
from sklearn import svm
from sklearn.ensemble import RandomForestClassifier

#evaluation
from sklearn.metrics import roc_auc_score, f1_score, precision_score

import itertools
import sys
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')
sys.path.insert(0, 'C:\\Users\\tipaek\OneDrive - Syracuse University\Desktop\\Research\\NestedBigramsResearch')
import gc

from Code.Helpers import hash_column, add_clustering

#features and normalization
from Code.FeatureExtraction import readFiles, readFilesWithEntropy
from Code.Helpers import normalize, clean_feature_names, check_special_characters

from sklearn.preprocessing import MinMaxScaler

from keras import backend as K


columns = ['Encoding Dim', 'AUC', 'F-1', 'Accuracy', 'Precision']


encoding_dimensions = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1]
paths = [10, 20, 30, 40, 50, 60, 70]

testing_data = []
kernel = 'linear'

experiment = Experiment(
  api_key=API_KEY,
  project_name="anomaly-detection-research",
  workspace="tipaek",
)
experiment.add_tags(["GPT - Rewrite, CNB-Base", "GPT - Rewrite", "CNB-Base.1", "Autoencoder single dense", "rf", "Random Forest"])

for path in paths:
    testing_data=[]
    temp_path = f'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch\\Datasets\\GPT - Rewrite\\CNB\\GPT.CNB.Base.{path}.csv'
    print(f'\nPATH: {temp_path}')
    data = pd.read_csv(temp_path)
    print(f'`\tInitial Shape: {data.shape[0]}\n\t {data.shape[1]}')

    data = pd.read_csv(temp_path)

    drop = ['File Name', 'Written', 'Row Range', 'File Path']

    X = data.drop(drop, axis=1)
    y = data['Written']

    X = X.rename(columns=hash_column)
    scaler = MinMaxScaler()
    X = pd.DataFrame(scaler.fit_transform(X), columns=X.columns)
    

    X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

    svm_clf = svm.SVC(kernel=kernel)  # might modify this
    print(f'\nCurrently using kernel: {kernel}')

    for size in encoding_dimensions:

        encoding_dim = int(size * data.shape[1])

        X_temp = X_train.values
        X_temp_test = X_test.values

        input_X = Input(shape=(X_temp.shape[1],))
        inputTest = Input(shape=(X_temp_test.shape[1],))

        encoded = Dense(encoding_dim, activation='relu')(input_X)
        decoded = Dense(X_temp.shape[1], activation='sigmoid')(encoded)

        autoencoder = Model(input_X, decoded)
        encoder = Model(input_X, encoded)

        autoencoder.compile(optimizer='adam', loss='binary_crossentropy')

        autoencoder.fit(X_temp, X_temp, epochs=60, batch_size=256, shuffle=True)

        reduced_input = encoder.predict(X_train.values)
        reduced_input_test = encoder.predict(X_test.values) #still not done here yet

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
            "group_size": path,
            "dimensions": encoding_dim,
            "percentage": size,
            "epochs": 60,
            "roc_auc": roc_auc,
            "f1": f1,
            "accuracy": accuracy,
            "precision": precision
            })
        
        # clear up memory
        del autoencoder, encoder, X_temp, X_temp_test, rf, input_X, inputTest
        gc.collect()
        
        K.clear_session()
    
    del X, X_train, X_test, y_train, y_test

    df = pd.DataFrame(testing_data, columns=columns)
    df.to_csv(f'GPT.CNB.AutoEncoder_Dim.{path}.csv', index=False)
    
experiment.end()




