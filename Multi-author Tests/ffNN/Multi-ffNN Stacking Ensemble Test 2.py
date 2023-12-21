import tensorflow as tf
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import roc_auc_score, f1_score, precision_score
from sklearn.decomposition import PCA
from concurrent.futures import ThreadPoolExecutor
from sklearn.ensemble import RandomForestClassifier, GradientBoostingClassifier, VotingClassifier
from sklearn.svm import SVC

# load and preprocess the data
data = pd.read_csv('/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)
y = data['Written']

# apply PCA to reduce the dimensionality of the data
pca = PCA(n_components=0.95)
X_pca = pca.fit_transform(X)

X_train, X_test, y_train, y_test = train_test_split(X_pca, y, random_state=42)
print("finished processing")

# split the training data into two parts: one for training the first-level classifiers and one for training the meta-classifier
X_train1, X_train2, y_train1, y_train2 = train_test_split(X_train, y_train, random_state=42)

# define the number of networks in the ensemble
num_networks = 30

optimizers = ['SGD', 'Adam', 'Adagrad', 'RMSprop']

# define a function to create and train a single network
def create_and_train_network(X_train1, y_train1):
    print('Training')
    # define the architecture of the network
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(5000, activation='relu', kernel_initializer='he_normal'),
        tf.keras.layers.Dense(1, activation='sigmoid', kernel_initializer='glorot_uniform')
    ])

    # randomly select an optimizer from the list of available optimizers
    optimizer = np.random.choice(optimizers)

    # compile the model
    model.compile(optimizer=optimizer, loss='binary_crossentropy', metrics=['accuracy'])

    # train the model on the training data
    model.fit(X_train1, y_train1, epochs=15)

    return model

# create a thread pool executor
executor = ThreadPoolExecutor(max_workers=5)

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
meta_features = np.hstack([X_train2] + predictions)



# Train meta-classifiers
# train the Random Forest meta-classifier on the stacked predictions and y_train2
rf_meta_classifier = RandomForestClassifier()
rf_meta_classifier.fit(meta_features, y_train2)

# train the Gradient Boosting Machines meta-classifier on the stacked predictions and y_train2
gbm_meta_classifier = GradientBoostingClassifier()
gbm_meta_classifier.fit(meta_features, y_train2)

# train the Support Vector Machines meta-classifier on the stacked predictions and y_train2
svm_meta_classifier = SVC(probability=True)
svm_meta_classifier.fit(meta_features, y_train2)



# make predictions with each first-level classifier on X_test and stack the predictions to create a new dataset for making predictions with the meta-classifier
test_predictions = []
for model in models:
    pred = model.predict(X_test)
    test_predictions.append(pred)
test_meta_features = np.hstack([X_test] + test_predictions)

# Evaluate Random Forest, GBM, and SVM
rf_final_predictions_proba = rf_meta_classifier.predict_proba(test_meta_features)[:, 1]
rf_final_predictions = (rf_final_predictions_proba > 0.5).astype(int)

gbm_final_predictions_proba = gbm_meta_classifier.predict_proba(test_meta_features)[:, 1]
gbm_final_predictions = (gbm_final_predictions_proba > 0.5).astype(int)

svm_final_predictions_proba = svm_meta_classifier.predict_proba(test_meta_features)[:, 1]
svm_final_predictions = (svm_final_predictions_proba > 0.5).astype(int)

# evaluate the performance of each meta-classifier using ROC AUC, F1-score, accuracy and precision
rf_roc_auc = roc_auc_score(y_test, rf_final_predictions_proba)
rf_f1 = f1_score(y_test, rf_final_predictions)
rf_accuracy = np.mean(y_test == rf_final_predictions)
rf_precision = precision_score(y_test, rf_final_predictions)

gbm_roc_auc = roc_auc_score(y_test, gbm_final_predictions_proba)
gbm_f1 = f1_score(y_test, gbm_final_predictions)
gbm_accuracy = np.mean(y_test == gbm_final_predictions)
gbm_precision = precision_score(y_test, gbm_final_predictions)

svm_roc_auc = roc_auc_score(y_test, svm_final_predictions_proba)
svm_f1 = f1_score(y_test, svm_final_predictions)
svm_accuracy = np.mean(y_test == svm_final_predictions)
svm_precision = precision_score(y_test, svm_final_predictions)

print(f'Random Forest: ROC AUC: {rf_roc_auc:.2f} F1-score: {rf_f1:.2f} Accuracy: {rf_accuracy:.2f} Precision: {rf_precision:.2f}')
print(f'Gradient Boosting Machines: ROC AUC: {gbm_roc_auc:.2f} F1-score: {gbm_f1:.2f} Accuracy: {gbm_accuracy:.2f} Precision: {gbm_precision:.2f}')
print(f'Support Vector Machines: ROC AUC: {svm_roc_auc:.2f} F1-score: {svm_f1:.2f} Accuracy: {svm_accuracy:.2f} Precision: {svm_precision:.2f}')



# Voting Classifier and Tests
# create a voting classifier that combines the outputs of the three meta-classifiers using soft voting
voting_classifier = VotingClassifier(estimators=[('rf', rf_meta_classifier), ('gbm', gbm_meta_classifier), ('svm', svm_meta_classifier)], voting='soft')
voting_classifier.fit(meta_features, y_train2)

# make predictions with the voting classifier on the stacked test set predictions to obtain the final predictions of the ensemble
final_predictions_proba = voting_classifier.predict_proba(test_meta_features)[:, 1]
final_predictions = (final_predictions_proba > 0.5).astype(int)

# evaluate the performance of the ensemble using ROC AUC, F1-score, accuracy and precision
roc_auc = roc_auc_score(y_test, final_predictions_proba)
f1 = f1_score(y_test, final_predictions)
accuracy = np.mean(y_test == final_predictions)
precision = precision_score(y_test, final_predictions)

print(f'Voting Classifier: ROC AUC: {roc_auc:.2f} F1-score: {f1:.2f} Accuracy: {accuracy:.2f} Precision: {precision:.2f}')

