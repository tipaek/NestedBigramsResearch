import sys
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')
sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch')
from pyod.models.knn import KNN
from pyod.utils import evaluate_print

from Code.FeatureExtractionCleaned import *
from Code.Helpers import normalize

#file directory containing both regular and anomalous java code
files = r'C:/Users/tipaek/OneDrive - Syracuse University/Desktop/Research/NestedBigramsResearch/Data/GPT - Rewrite'

temp = [10, 20, 30, 40, 50, 60, 70]
#getting features and giving 0/1 classes where 1 is anomalous
for val in temp:
    print(f'\n\n\n\nVAL: {val}')
    data = NBKL3(files, val)

    data.to_csv(f'GPT.CNB.Base.{val}.csv', index=False)








