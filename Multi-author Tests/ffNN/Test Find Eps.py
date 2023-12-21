from sklearn.neighbors import NearestNeighbors
import matplotlib.pyplot as plt

import pandas as pd

import sys
sys.path.insert(0, '/Code')

from Code.Helpers import *

data = pd.read_csv('/Datasets/15 authors/15, 70 Entropy, Normalized.csv')
print(data.shape[0])
print(data.shape[1])

# Add clustering feature to the data
drop_features = ['File Name', 'Written', 'Row Range', 'File Path']

# Extract the feature columns
X = data.drop(['File Name', 'Written', 'Row Range', 'File Path'], axis=1)

# Set the value of k (the number of nearest neighbors to consider)
k = 5

# Fit the NearestNeighbors model to the data
neigh = NearestNeighbors(n_neighbors=k)
neigh.fit(X)

# Find the k-th nearest neighbor for each point
distances, _ = neigh.kneighbors(X)

# Get the distance to the k-th nearest neighbor for each point
k_distances = distances[:, k-1]

# Sort the distances in descending order
k_distances_sorted = np.sort(k_distances)[::-1]

# Plot the k-distance graph
plt.plot(k_distances_sorted)
plt.xlabel('Points sorted by distance to their k-th nearest neighbor')
plt.ylabel(f'Distance to {k}-th nearest neighbor')
plt.show()
