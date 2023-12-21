import sys
sys.path.insert(0, '/Code')

import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split
from sklearn.metrics import roc_auc_score, f1_score, precision_score, accuracy_score

from catboost import CatBoostClassifier
from sklearn.ensemble import RandomForestClassifier, VotingClassifier

import Code.Helpers

data = pd.read_csv('/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
print(data.shape[0])
print(data.shape[1])

# Drop unusable columns and split data
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
y = data['Written']

# Rename columns by replacing any non-alphanumeric characters with a unique string and truncate
X = X.rename(columns=Code.Helpers.hash_column)

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)

# Split the test data into groups of 400
n_splits = int(np.ceil(len(X_test) / 500))
X_test_splits = np.array_split(X_test, n_splits)
y_test_splits = np.array_split(y_test, n_splits)

# Train a CatBoost or Random Forest classifier on each test data group (50/50 split)
classifiers = []
for i in range(n_splits):
    if i % 2 == 0:
        clf = CatBoostClassifier(random_state=42, verbose=False)
    else:
        clf = RandomForestClassifier(random_state=42)
    clf.fit(X_train, y_train)
    classifiers.append(clf)

# Make predictions using the trained classifiers
y_pred_probas = []
for clf in classifiers:
    y_pred_proba = clf.predict_proba(X_test)[:, 1]
    y_pred_probas.append(y_pred_proba)
y_pred_probas = np.mean(y_pred_probas, axis=0)

# Evaluate performance using ROC AUC, F1-score, accuracy, and precision
roc_auc = roc_auc_score(y_test, y_pred_probas)
f1 = f1_score(y_test, y_pred_probas.round())
accuracy = accuracy_score(y_test, y_pred_probas.round())
precision = precision_score(y_test, y_pred_probas.round())
print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')
