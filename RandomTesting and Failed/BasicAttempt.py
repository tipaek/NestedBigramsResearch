#PyOD python library meant for anomoly detection
from pyod.models.knn import KNN

# Import the utility function for model evaluation (printing)
from pyod.utils import evaluate_print

import pandas as pd
import math


#method for making pd dataframe
def readFileLexical(code: str, groupSize: int) -> pd.DataFrame:
    with open(code, 'r') as c:
        f = c.readlines()

    total_rows = math.ceil(len(f) / groupSize)
    groups = [f[i:i + groupSize] for i in range(0, len(f), groupSize)]
    data = []

    for num in range(len(groups)):
        group = groups[num]
        rows = f"{num * groupSize + 1}-{(num + 1) * groupSize}"
        spaces = sum([line.count(' ') for line in group])
        tabs = sum([line.count('\t') for line in group])
        this = sum([line.count('this') for line in group])
        ln = len(group)
        capitalLetter = sum([sum(1 for c in line if c.isupper()) for line in group])
        written = 0

        data.append([rows, spaces, tabs, this, ln, capitalLetter, written])

    df = pd.DataFrame(data, columns=['Row Range', 'Spaces', 'Tabs', 'This', 'Lines', 'Capital Letters', 'Written'])
    df = df.head(total_rows)

    return df

#getting data
file1 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Playing.java'
data = readFileLexical(file1, 20)

#I intentionally put code not written by the author in the last 4 groups
for num in range(23, 27):
    data.loc[num, 'Written'] = 1

print(data)

#Separate data
X = data[['Spaces', 'Tabs', 'This', 'Lines', 'Capital Letters']].values
y = data['Written'].values

#Train kNN detector
clf = KNN(contamination = 0.15, n_neighbors = 5)
clf.fit(X)

#prediction labels
yTrainPred = clf.labels_

#outliers
yTrainOutput = clf.decision_scores_

#evaluate and print results
evaluate_print('KNN', y, yTrainOutput)


