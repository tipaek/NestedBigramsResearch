import tensorflow as tf
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import roc_auc_score, f1_score, precision_score
from sklearn.decomposition import PCA

# load and preprocess the data
data = pd.read_csv('/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
y = data['Written']

# apply PCA to reduce the dimensionality of the data
pca = PCA(n_components=0.95)
X_pca = pca.fit_transform(X)

X_train, X_test, y_train, y_test = train_test_split(X_pca, y, random_state=42)
print("finished processing")

optimizers = ['SGD', 'Adam', 'Adagrad', 'RMSprop']

print('Training')
# define the architecture of the network
model = tf.keras.Sequential([
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(5000, activation='relu'),
        tf.keras.layers.Dense(1, activation='sigmoid')
])

# compile the model
model.compile(optimizer='Adam', loss='binary_crossentropy', metrics=['accuracy'])

# train the model on the training data
model.fit(X_train, y_train, epochs=100)

# make predictions with the model on X_test
final_predictions_proba = model.predict(X_test)[:, 0]
final_predictions = (final_predictions_proba > 0.5).astype(int)

# evaluate the performance of the model using ROC AUC, F1-score, accuracy and precision
roc_auc = roc_auc_score(y_test, final_predictions_proba)
f1 = f1_score(y_test, final_predictions)
accuracy = np.mean(y_test == final_predictions)
precision = precision_score(y_test, final_predictions)

print(f'ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')

