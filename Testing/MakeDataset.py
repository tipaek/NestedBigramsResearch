import sys
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')
sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch')

from Code.FeatureExtractionCleaned import *
from Code.Helpers import normalize

#file directory containing both regular and anomalous java code
files = r'C:/Users/tipaek/OneDrive - Syracuse University/Desktop/Research/NestedBigramsResearch/Data/40authors'

temp = [10, 20, 30, 40, 50, 60, 70]
#getting features and giving 0/1 classes where 1 is anomalous
#temp = [2000, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100] + [5000, 4500, 4000, 3500, 3000, 2500]
for val in temp:
    print(f'\n\n\n\nVAL: {val}')
    checkFiles(files)
    data = extract_features_with_equalWidthBinning(files, val, 600)

    data.to_csv(f'40.CNB.G{val}.csv', index=False)








