import tensorflow as tf
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import roc_auc_score, f1_score, precision_score
from sklearn.linear_model import LogisticRegression
from concurrent.futures import ThreadPoolExecutor

# load and preprocess the data
data = pd.read_csv('/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
y = data['Written']
X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)
print("finished processing")

# split the training data into two parts: one for training the first-level classifiers and one for training the meta-classifier
X_train1, X_train2, y_train1, y_train2 = train_test_split(X_train, y_train, random_state=42)

# define the number of networks in the ensemble
num_networks = 20

# define a function to create and train a single network
def create_and_train_network(X_train1, y_train1):
    print('Training')
    # define the architecture of the network
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(1000, activation='relu'),
        tf.keras.layers.Dense(1000, activation='sigmoid')
    ])

    # compile the model
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    # train the model on the training data
    model.fit(X_train1, y_train1, epochs=10)

    return model

# create a thread pool executor
executor = ThreadPoolExecutor()

# submit tasks to train each network on a different subset of the data in parallel
futures = [executor.submit(create_and_train_network, X_train1, y_train1) for _ in range(num_networks)]

# wait for all tasks to complete and retrieve the results
models = [future.result() for future in futures]

# make predictions with each network on X_train2
predictions = []
for model in models:
    pred = model.predict(X_train2)
    predictions.append(pred)

# stack the predictions from each first-level classifier to create a new dataset for training the meta-classifier
meta_features = np.hstack(predictions)

# train the meta-classifier on the stacked predictions and y_train2
meta_classifier = LogisticRegression()
meta_classifier.fit(meta_features, y_train2)

# make predictions with each first-level classifier on X_test and stack the predictions to create a new dataset for making predictions with the meta-classifier
test_predictions = []
for model in models:
    pred = model.predict(X_test)
    test_predictions.append(pred)
test_meta_features = np.hstack(test_predictions)

# make predictions with the meta-classifier on the stacked test set predictions to obtain the final predictions of the ensemble
final_predictions_proba = meta_classifier.predict_proba(test_meta_features)[:, 1]
final_predictions = (final_predictions_proba > 0.1).astype(int)

# evaluate the performance of the ensemble using ROC AUC, F1-score, accuracy and precision
roc_auc = roc_auc_score(y_test, final_predictions_proba)
f1 = f1_score(y_test, final_predictions)
accuracy = np.mean(y_test == final_predictions)
precision = precision_score(y_test, final_predictions)

print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')
