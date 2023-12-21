
from pyod.models.knn import KNN
from pyod.utils import evaluate_print

#method for making pd dataframe
import pandas as pd
import math


def readFilesLexical(codes: list, groupSize: int) -> pd.DataFrame:
    dfs = []
    for code in codes:
        with open(code, 'r') as c:
            f = c.readlines()

        total_rows = math.ceil(len(f) / groupSize)
        groups = [f[i:i + groupSize] for i in range(0, len(f), groupSize)]
        data = []

        for i in range(len(groups)):
            group = groups[i]
            row_range = f"{i * groupSize + 1}-{(i + 1) * groupSize}"
            spaces = sum([line.count(' ') for line in group])
            tabs = sum([line.count('\t') for line in group])
            this = sum([line.count('this') for line in group])
            ln = len(group)
            capitalLetter = sum([sum(1 for c in line if c.isupper()) for line in group])
            written = 0

            data.append([row_range, spaces, tabs, this, ln, capitalLetter, written])

        df = pd.DataFrame(data, columns=['Row Range', 'Spaces', 'Tabs', 'This', 'Lines', 'Capital Letters', 'Written'])
        df = df.head(total_rows)
        dfs.append(df)

    result = pd.concat(dfs, ignore_index=True)
    return result


#getting data
file1 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Playing.java'
file2 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Credits.java'
file3 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\GameOptions.java'
file4 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\GameState.java'
file5 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Menu.java'
file6 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\State.java'
file7 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Statemethods.java'
files = [file1, file2, file3, file4, file5, file6, file7]

data = readFilesLexical(files, 20)

#I intentionally put code not written by the author in the last 4 groups
for num in range(23, 27):
    data.loc[num, 'Written'] = 1

print(data)

#Separate data
X = data[['Spaces', 'Tabs', 'This', 'Lines', 'Capital Letters']].values
y = data['Written'].values

#Train kNN detector
clf = KNN(contamination = 0.08, n_neighbors = 50)
clf.fit(X)

#Print
yTrainPred = clf.labels_
yTrainOutput = clf.decision_scores_
evaluate_print('KNN', y, yTrainOutput)

#File not written by author and anomalyValues of said file
fileScuffed = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Playing2.java'

dataNew = readFilesLexical([fileScuffed], 20)
newX = dataNew[['Spaces', 'Tabs', 'This', 'Lines', 'Capital Letters']].values

anomalyScores = clf.decision_function(newX)


#Print the ones that are above threshold
threshold = 0.8

predictions = (anomalyScores > threshold).astype(int)

#Print predictions
for i in range(len(predictions)):
    prediction = predictions[i]
    if prediction == 0:
        print(f'Instance {i} is normal')
    else:
        print(f'Instance {i} is anomalous')
