# -*- coding: utf-8 -*-
"""
Created on Fri May 31 10:50:31 2024

@author: l-tipaek
"""

import numpy as np
import pandas as pd

from scipy import stats
from itertools import combinations

from scipy.stats import ttest_rel, combine_pvalues

#ensembles
from lightgbm import LGBMClassifier
from xgboost import XGBClassifier
from catboost import CatBoostClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn import svm

from sklearn.model_selection import train_test_split
from Code.Helpers import hash_column, add_clustering

import sys
sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch')



datasets = [r"\NB\Base\GPT.NB.G30.csv", r"\CNB\GPT.CNB.Base.30.csv", r"\NB\EWDBase\GPT.EWD.NB.G30.csv",
            r"\NB\Transformer\AverageEqualWidth\800\GPT.NB.EqualWidthBinning(average).800.G30.csv", 
            r"\NB\Base+Transformer\Freq + Average\GPT.NB.EqualWidthBinning(freq+average).3000.4500.G30.csv"]

random_states = [i for i in range(100)]

def accuracy_scores(path, states):
    data = pd.read_csv(r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Datasets\GPT - Rewrite" + path)
    print(path)
    drop = ['File Name', 'Written', 'Row Range', 'File Path']

    X = data.drop(drop, axis=1)
    y = data['Written']

    X = X.rename(columns=hash_column)
    
    scores = []

    for size in states:
        X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=size)
        
        lgbm_clf =LGBMClassifier(random_state=size, verbose=-1)
        xgb_clf = XGBClassifier(random_state=size)
        catboost_clf = CatBoostClassifier(random_state=size, verbose=False)
        rf_clf = RandomForestClassifier(random_state=size)
        
        acc = 0
        
        for clf in (lgbm_clf, xgb_clf, catboost_clf, rf_clf):
         
            clf.fit(X_train, y_train)

            y_pred = clf.predict(X_test)
            y_pred_proba = clf.predict_proba(X_test)[:, 1]

            accuracy = clf.score(X_test, y_test)
            
            acc += accuracy / 4
        
        scores.append(acc)
        
    return scores


all_scores = []
for dataset in datasets:
    scores = accuracy_scores(dataset, random_states)
    all_scores.append(scores)

def paired_t_tests(all_scores):
    p_values = []
    statistics = []
    num_datasets = len(all_scores)
    for i, j in combinations(range(num_datasets), 2):
        p_values_pair = []
        for k in range(len(random_states)):
            t_stat, p_val = ttest_rel(all_scores[i], all_scores[j])
            p_values_pair.append(p_val)
        combined_p_value = combine_pvalues(p_values_pair)[1]
        p_values.append((i, j, combined_p_value))
        
        # Compute statistics
        stats_i = {
            'mean': np.mean(all_scores[i]),
            'median': np.median(all_scores[i]),
            'std_dev': np.std(all_scores[i]),
            '10th_percentile': np.percentile(all_scores[i], 10),
            '90th_percentile': np.percentile(all_scores[i], 90)
        }
        stats_j = {
            'mean': np.mean(all_scores[j]),
            'median': np.median(all_scores[j]),
            'std_dev': np.std(all_scores[j]),
            '10th_percentile': np.percentile(all_scores[j], 10),
            '90th_percentile': np.percentile(all_scores[j], 90)
        }
        statistics.append((i, j, stats_i, stats_j))
        
    return p_values, statistics

p_values, statistics = paired_t_tests(all_scores)

for i, j, p_val in p_values:
    print(f"Dataset {i+1} vs Dataset {j+1}: combined p-value = {p_val:.5f}")

results = []
for (i, j, p_val), (_, _, stats_i, stats_j) in zip(p_values, statistics):
    results.append({
        'Dataset 1': datasets[i], 
        'Dataset 2': datasets[j], 
        'Combined P-Value': p_val,
        'Dataset 1 Mean': stats_i['mean'],
        'Dataset 1 Median': stats_i['median'],
        'Dataset 1 Std Dev': stats_i['std_dev'],
        'Dataset 1 10th Percentile': stats_i['10th_percentile'],
        'Dataset 1 90th Percentile': stats_i['90th_percentile'],
        'Dataset 2 Mean': stats_j['mean'],
        'Dataset 2 Median': stats_j['median'],
        'Dataset 2 Std Dev': stats_j['std_dev'],
        'Dataset 2 10th Percentile': stats_j['10th_percentile'],
        'Dataset 2 90th Percentile': stats_j['90th_percentile']
    })
# Step 5: Convert the results to a DataFrame
results_df = pd.DataFrame(results)

# Step 6: Save the results to a CSV file
results_df.to_csv(r't_test_results.csv', index=False)




           