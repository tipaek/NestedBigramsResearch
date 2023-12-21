# data
import pandas as pd
from sklearn.model_selection import train_test_split, cross_val_score
import re

# ensembles
from sklearn.ensemble import RandomForestClassifier

# evaluation
from sklearn.metrics import roc_auc_score, f1_score, precision_score, accuracy_score

import sys
sys.path.insert(0, '/Code')

from Code.Helpers import hash_column, objective_function

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
lower_bounds = [10, 2, 2, 1, 0.1, 0]
upper_bounds = [200, 10, 10, 50, 1.0, 1]
bounds = (lower_bounds, upper_bounds)

# Create a PSO optimizer
options = {'c1': 0.5, 'c2': 0.3, 'w': 0.9}
optimizer = ps.single.GlobalBestPSO(n_particles=10, dimensions=6, options=options, bounds=bounds)

clf = RandomForestClassifier(n_estimators=100, n_jobs=-1)

# Run the optimization
cost, pos = optimizer.optimize(lambda x: objective_function(x, clf, X_train,y_train), iters=200)

# Unpack the optimal hyperparameters
n_estimators, max_depth, min_samples_split,min_samples_leaf,max_features,criterion = pos
print(f'Optimal hyperparameters: n_estimators={n_estimators}, max_depth={max_depth}, min_samples_split={min_samples_split}, min_samples_leaf={min_samples_leaf}, max_features={max_features}, criterion={criterion}')

# Round the hyperparameters to their nearest integer value
n_estimators = int(n_estimators)
max_depth = int(max_depth)
min_samples_split = int(min_samples_split)
min_samples_leaf=int(min_samples_leaf)
max_features=float(max_features)
criterion=int(criterion)

if criterion==0:
    criterion='gini'
else:
    criterion='entropy'

# Create a Random Forest classifier with the optimal hyperparameters
clf = RandomForestClassifier(n_estimators=n_estimators,
                             max_depth=max_depth,
                             min_samples_split=min_samples_split,
                             min_samples_leaf=min_samples_leaf,
                             max_features=max_features,
                             criterion=criterion,
                             random_state=42, n_jobs=-1)

# Fit the classifier to the training data
clf.fit(X_train, y_train)

# Make predictions on the test data
y_pred = clf.predict(X_test)
y_pred_proba = clf.predict_proba(X_test)[:, 1]

# evaluation using ROC_AUC, F1 and Precision(basically)
roc_auc = roc_auc_score(y_test,y_pred_proba)
f1=f1_score(y_test,y_pred)
accuracy=clf.score(X_test,y_test)
precision=precision_score(y_test,y_pred)

print(clf.__class__.__name__)
print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')
