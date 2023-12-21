import tensorflow as tf
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import roc_auc_score, f1_score, precision_score
from concurrent.futures import ThreadPoolExecutor

# load and preprocess the data
data = pd.read_csv('/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
y = data['Written']
X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=42)
print("finished processing")

# define the number of networks in the ensemble
#num_networks = data.shape[0]
num_networks = 10

# define a function to create and train a single network
def create_and_train_network(X_train, y_train):
    print('Training')
    # define the architecture of the network
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(16, activation='relu'),
        tf.keras.layers.Dense(1, activation='sigmoid')
    ])

    # compile the model
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    # create a bootstrap sample of the data
    sample_indices = np.random.choice(X_train.shape[0], size=X_train.shape[0], replace=True)
    X_sample = X_train.iloc[sample_indices]
    y_sample = y_train.iloc[sample_indices]

    # train the model on the bootstrap sample
    model.fit(X_sample, y_sample, epochs=10)

    return model

# create a thread pool executor
executor = ThreadPoolExecutor()

# submit tasks to train each network on a different subset of the data in parallel
futures = [executor.submit(create_and_train_network, X_train, y_train) for _ in range(num_networks)]

# wait for all tasks to complete and retrieve the results
models = [future.result() for future in futures]

# make predictions with each network
predictions = []
for model in models:
    pred = model.predict(X_test)
    predictions.append(pred)

# combine the predictions by taking the average
final_predictions_proba = np.mean(predictions, axis=0)
final_predictions = (final_predictions_proba > 0.1).astype(int)
final_predictions = final_predictions.reshape(-1)

# evaluate the performance of the ensemble using ROC AUC, F1-score, accuracy and precision
roc_auc = roc_auc_score(y_test, final_predictions_proba)
f1 = f1_score(y_test, final_predictions)
accuracy = np.mean(y_test == final_predictions)
precision = precision_score(y_test, final_predictions)

print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')
