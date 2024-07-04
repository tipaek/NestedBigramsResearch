import sys
#sys.path.insert(0, 'C:\\Coding\\Research\\Summer 2023\\Stylometry Neural Networks\\Code')
#sys.path.insert(0, 'C:\\Users\\tipaek\\OneDrive - Syracuse University\\Desktop\\Research\\NestedBigramsResearch')
sys.path.insert(0, 'C:\\Users\\l-tipaek\Desktop\\Research\\NestedBigramsResearch')


from Code.FeatureExtractionCleaned import *
from Code.Helpers import normalize

#file directory containing both regular and anomalous java code
#files = r'C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\40authors'
#files = r'C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\GPT - Rewrite'
#files = r'C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\temp3'
#files = r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\GCJ\files"
files = r"C:\Users\l-tipaek\Desktop\Research\NestedBigramsResearch\Data\GCJ\dataset"
temp = [10, 20, 30, 40, 50, 60, 70]
#getting features and giving 0/1 classes where 1 is anomalous
#temp = [500, 1000, 2000, 3000, 4000, 5000]
#temp = [2000, 1000, 900, 800, 700, 600, 500, 400, 300, 200, 100] + [5000, 4500, 4000, 3500, 3000, 2500]
for val in temp:
    print(f'\n\n\n\nVAL: {val}')
    #deleteFailedFiles(files)
    #checkFiles(files)
    data = extract_features_with_equalWidthBinning(files, val, 3000)
    #data = NBKL3(files, val)

    data.to_csv(f'GCJ-GPT.EWD.NB.G{val}.3000.csv', index=False)








