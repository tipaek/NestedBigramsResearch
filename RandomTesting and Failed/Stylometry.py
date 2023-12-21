from scipy import stats

import pandas as pd
import numpy as np


class Stylometry:
    def readFileLexical(self, code: str, groupSize: int) -> pd.DataFrame:
        with open(code, 'r') as c:
            f = c.read()

        groups = [f[i:i + groupSize] for i in range(0, len(f), groupSize)]
        data = []

        for group in groups:
            spaces = group.count(' ')
            tabs = group.count('\t')
            this = group.count('this')
            ln = group.count('\n')
            capitalLetter = sum(1 for c in group if c.isupper())

            data.append([spaces, tabs, this, ln, capitalLetter])

            df = pd.DataFrame(data, columns = ['Spaces', 'Tabs', 'This', 'Lines', 'Capital Letters'])

        return df

    def anomolyDetectionLexical(self, f):
        X = self.readFileLexical(f, 20)

        z_scores = np.abs(stats.zscore(X))

        threshold = 3 #how many standards deviations away to be consider anaomolous

        anomolies = np.where(z_scores > threshold)

        print(anomolies[0])
        print(X.iloc[anomolies[0]])















file1 = 'C:\Coding\Research\Summer 2023\Stylometry Neural Networks\Stylometry Attempt #1\Data\Game.java'
Stylometry = Stylometry()
Stylometry.anomolyDetectionLexical(file1)
