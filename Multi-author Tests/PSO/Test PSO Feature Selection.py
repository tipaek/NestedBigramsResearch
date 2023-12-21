# data and math
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split, cross_val_score
import re

# ensembles
from sklearn.ensemble import RandomForestClassifier

# evaluation
from sklearn.metrics import roc_auc_score, f1_score, precision_score, accuracy_score, make_scorer
from sklearn.model_selection import cross_validate

import sys
sys.path.insert(0, '/Code')

from Code.Helpers import hash_column

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
lower_bounds = [0] * X_train.shape[1]
upper_bounds = [1] * X_train.shape[1]
bounds = (lower_bounds, upper_bounds)

# Create a PSO optimizer
options = {'c1': 0.5, 'c2': 0.3, 'w': 0.9}
optimizer = ps.single.GlobalBestPSO(n_particles=10, dimensions=X_train.shape[1], options=options, bounds=bounds)

clf = RandomForestClassifier(n_estimators=100, random_state=42, n_jobs=-1)

def objective_function(params, clf, X_train, y_train):
    # Initialize an array to store the objective function values for each particle
    obj = np.zeros(params.shape[0])

    # Compute the objective function value for each particle
    for i in range(params.shape[0]):
        # Create a mask to select the features for the current particle
        mask = params[i] > 0.5

        # Select the features for the current particle
        X_train_selected = X_train.iloc[:, mask]

        # Compute the cross-validation scores of the classifier using only the selected features
        scores = cross_validate(clf, X_train_selected, y_train, cv=5, scoring={
            'accuracy': make_scorer(accuracy_score),
            'f1': make_scorer(f1_score)
        })

        # Compute the mean value of each metric
        accuracy = scores['test_accuracy'].mean()
        f1 = scores['test_f1'].mean()

        # Combine the metrics into a single objective function value
        obj[i] = 1 - (accuracy + f1) / 2

    # Return the array of objective function values for all particles
    return obj


# Run the optimization
cost, pos = optimizer.optimize(lambda x: objective_function(x, clf, X_train, y_train), iters=50)

# Create a mask to select the optimal features
mask = pos > 0.5

# Select the optimal features
X_train_selected = X_train.iloc[:, mask]
X_test_selected = X_test.iloc[:, mask]

# fit data with optimal hyperparameters
clf.fit(X_train_selected,y_train)

# make predictions with optimal hyperparameters
y_pred = clf.predict(X_test_selected)
y_pred_proba = clf.predict_proba(X_test_selected)[:, 1]

# evaluation using ROC_AUC, F1 and Precision(basically)
roc_auc = roc_auc_score(y_test,y_pred_proba)
f1=f1_score(y_test,y_pred)
accuracy=clf.score(X_test_selected,y_test)
precision=precision_score(y_test,y_pred)

print(clf.__class__.__name__)
print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')
