import sys
sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')

from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtractionCleaned import readFilesWithBERT
from Code.Helpers import normalize

#file directory containing both regular and anomalous java code
files = r'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Data\one'

#getting features and giving 0/1 classes where 1 is anomalous
data = readFilesWithBERT(files, 10)

data = normalize(data)

data.to_csv('BeNV.1.10, normalized.csv', index=False)








