import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtractionCleaned import readFilesWithBERT, readFilesWithEntropy
from Code.Helpers import normalize

#file directory containing both regular and anomalous java code
files = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Data\one'

#temp = [10, 20, 30, 40, 50, 60, 70]
temp = [20, 30, 40, 50, 60, 70]

#getting features and giving 0/1 classes where 1 is anomalous
for val in temp:
    print(f'\n\n\n\nVAL: {val}')
    data = readFilesWithBERT(files, val)

    data = normalize(data)

    data.to_csv(f'BeNV.1.{val}, normalized.csv', index=False)








