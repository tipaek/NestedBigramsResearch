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
#sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch')
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')
sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch')

from Code.Helpers import hash_column, add_clustering

#features and normalization
from Code.FeatureExtraction import readFiles, readFilesWithEntropy
from Code.Helpers import normalize, clean_feature_names, check_special_characters

#for t-test plot
import scipy.stats as stats
from scipy.stats import ttest_ind
import matplotlib.pyplot as plt
import numpy as np



columns = ['Group Size',
           'LGBM AUC', 'LGBM F-1 Score', 'LGBM Accuracy', 'LGBM Precision',
           'XGB AUC', 'XGB F-1 Score', 'XGB Accuracy', 'XGB Precision',
           'CatBoost AUC', 'CatBoost F-1 Score', 'CatBoost Accuracy', 'CatBoost Precision',
           'RF AUC', 'RF F-1 Score', 'RF Accuracy', 'RF Precision']


#group_sizes = [10, 20, 30, 40, 50, 60, 70]
group_sizes = [i for i in range(1, 300)]

#group_sizes = [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 2000, 2500, 3000, 3500, 4000, 4500, 5000]

#paths = [r"C:\Users\tipaek\OneDrive - Syracuse University\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite\NB\Base\GPT.NB.Base"]
#paths = [r"C:\Users\tipaek\OneDrive - Syracuse University\Desktop\Research\NestedBigramsResearch\Datasets\40Authors\CNB\40.CNB"]
#paths = [r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite\CNB\EqualWidthBinning\AverageCLS\40\GPT.EqualWidthBinning(average)"]
#paths = [r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Datasets\40Authors\EqualWidthBinning\NB Average + Freq\Optimized\40.NB.EqualWidthBinning(average+freq)"]
testing_data = []
paths = [r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite\CNB + NB\EqualWidthBinning\(3000)\GPT.CNB+NB.EqualWidthBinning.3000.G30.csv"]

# experiment = Experiment(
#   api_key=API_KEY,
#   project_name="anomaly-detection-research",
#   workspace="tipaek",
# )
# experiment.add_tags(["GPT - Rewrite, NB Mean Squared Average + Freq", "GPT - Rewrite", "NB Average Cls + Freq", "ensembles", "Equal Width Binning", "Test Seeds"])
#experiment.add_tags(["40 Authors, NB Average+freq", "40 Authors", "ensembles", "NB Average+freq"])
accuracies = []
f1s = []
aucs = []
precisions = []
for path in paths:
    curr_path = f'{path}'
    for size in group_sizes:
        #curr_path = f'{path}.600.2000.G{size}.csv'
        print(f'\nPATH: {curr_path}')
        print(size)
        
        data = pd.read_csv(curr_path)
        print(f'`t {data.shape[0]}\n\t {data.shape[1]}')
        drop = ['File Name', 'Written', 'Row Range', 'File Path']

        X = data.drop(drop, axis=1)
        y = data['Written']

        X = X.rename(columns=hash_column)

        # X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

        
        # lgbm_clf =LGBMClassifier(random_state=42, verbose=-1)
        # xgb_clf = XGBClassifier(random_state=42)
        # catboost_clf = CatBoostClassifier(random_state=42, verbose=False)
        # rf_clf = RandomForestClassifier(random_state=42)
        
        X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=size)

        
        lgbm_clf =LGBMClassifier(random_state=size, verbose=-1)
        xgb_clf = XGBClassifier(random_state=size)
        catboost_clf = CatBoostClassifier(random_state=size, verbose=False)
        rf_clf = RandomForestClassifier(random_state=size)
        

        testing_data.append(size)
        
        acc = 0 
        prec = 0 
        f1t = 0 
        aucc = 0
        
        for clf in (lgbm_clf, xgb_clf, catboost_clf, rf_clf):
            clf_name = type(clf).__name__
         
            clf.fit(X_train, y_train)

            y_pred = clf.predict(X_test)
            y_pred_proba = clf.predict_proba(X_test)[:, 1]

            roc_auc = roc_auc_score(y_test, y_pred_proba)
            f1 = f1_score(y_test, y_pred)
            accuracy = clf.score(X_test, y_test)
            precision = precision_score(y_test, y_pred)
            
            acc += accuracy / 4
            prec += precision / 4
            f1t += f1 / 4
            aucc += roc_auc / 4
            
            # cm = confusion_matrix(y_test, y_pred)
        
        accuracies.append(acc)
        precisions.append(prec)
        f1s.append(f1t)
        aucs.append(aucc)
            
        

            # testing_data.append([roc_auc, f1, accuracy, precision])
     
#             experiment.log_metrics({
#                 "group_size": 30,
#                 "dimensions": X.shape[1],
#                 "roc_auc": roc_auc, #metrics
#                 "f1": f1,
#                 "accuracy": accuracy,
#                 "precision": precision,
#                 "width": "600 + 2000",
#                 "clf": clf_name,
#                 "random_seed": size
#                 })
#             experiment.log_confusion_matrix(labels=["Not Anomalous", "Anomalous"], matrix=cm)
            
            
            
# experiment.end()

#df = pd.DataFrame(testing_data, columns=columns)
#df.to_csv('NB+N.EqualWidthBinning.60.csv', index=False)

# t-test
metrics = {
    'accuracy': accuracies,
    'precision': precisions,
    'auc': aucs,
    'f1_score': f1s
}

for metric, values in metrics.items():
    # Calculate t-test
    t_stat, p_val = stats.ttest_1samp(values, 0.5)  # Null hypothesis: mean = 0.5
    print(f"{metric} t-statistic: {t_stat}, p-value: {p_val}")

    mean_val = np.mean(values)
    std_dev = np.std(values)
    
    # plotting
    plt.figure(figsize=(10, 5))
    plt.hist(values, bins=20, color='skyblue', edgecolor='black')
    plt.title(f"Histogram of {metric}")
    plt.xlabel(metric)
    plt.ylabel("Frequency")
    
    # mean
    plt.axvline(x=mean_val, color='r', linestyle='dashed', linewidth=2, label=f"Mean: {mean_val:.2f}")
    
    # std
    plt.axvline(x=mean_val - std_dev, color='g', linestyle='dashed', linewidth=2, label=f"Mean - 1 SD: {mean_val - std_dev:.2f}")
    plt.axvline(x=mean_val + std_dev, color='b', linestyle='dashed', linewidth=2, label=f"Mean + 1 SD: {mean_val + std_dev:.2f}")
    
    plt.legend()
    plt.grid(True)


    plt.savefig(f"GPTR.EWD.F+M(G30){metric}_histogram.png")




