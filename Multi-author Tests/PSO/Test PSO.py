# data
import pandas as pd
from sklearn.model_selection import train_test_split, cross_val_score
import re

# ensembles
from lightgbm import LGBMClassifier
from xgboost import XGBClassifier
from catboost import CatBoostClassifier
from sklearn.ensemble import RandomForestClassifier

# evaluation
from sklearn.metrics import roc_auc_score, f1_score, precision_score

import sys
sys.path.insert(0, '/Code')

from Code.Helpers import hash_column, objective_function

# features and normalization
from Code.FeatureExtraction import readFiles, readFilesWithEntropy
from Code.Helpers import normalize, clean_feature_names, check_special_characters

# PSO
import pyswarms as ps

data = pd.read_csv(
    '/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
print(data.shape[0])
print(data.shape[1])

# Drop unusable columns and split data
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
y = data['Written']

# Rename columns by replacing any non-alphanumeric characters with a unique string and truncate
X = X.rename(columns=hash_column)

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

# Set the bounds of the hyperparameters
lower_bounds = [10, 2, 2]
upper_bounds = [200, 10, 10]
bounds = (lower_bounds, upper_bounds)

# Create a PSO optimizer
options = {'c1': 0.5, 'c2': 0.3, 'w': 0.9}
optimizer = ps.single.GlobalBestPSO(n_particles=10, dimensions=3, options=options, bounds=bounds)

# Create a list of classifiers to optimize
#classifiers = [LGBMClassifier(random_state=42, verbose=-1), XGBClassifier(random_state=42), CatBoostClassifier(random_state=42, verbose=False),
               #RandomForestClassifier(random_state=42)]
classifiers = [RandomForestClassifier(random_state=42, n_jobs=-1)]


# Optimize each classifier individually
for clf in classifiers:
    # Run the optimization
    cost, pos = optimizer.optimize(lambda x: objective_function(x, clf, X_train, y_train, X, y), iters=50)

    # Set the optimal hyperparameters for each classifier
    clf.set_params(n_estimators=int(pos[0]), max_depth=int(pos[1]), min_samples_split=int(pos[2]))

    # Print the optimal hyperparameters for each classifier
    print(clf.__class__.__name__)
    print(
        f'Optimal hyperparameters: n_estimators={int(pos[0])}, max_depth={int(pos[1])}, min_samples_split={int(pos[2])}')

for clf in classifiers:
    # fit data with optimal hyperparameters
    clf.fit(X_train, y_train)

    # make predictions with optimal hyperparameters
    y_pred = clf.predict(X_test)
    y_pred_proba = clf.predict_proba(X_test)[:, 1]

    # evaluation using ROC_AUC, F1, and Precision(basically)
    roc_auc = roc_auc_score(y_test, y_pred_proba)
    f1 = f1_score(y_test, y_pred)
    accuracy = clf.score(X_test, y_test)
    precision = precision_score(y_test, y_pred)

    print(clf.__class__.__name__)
    print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')
