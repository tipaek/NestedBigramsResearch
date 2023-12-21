import pandas as pd

data = pd.read_csv('StylometryAttempt1.1.1, 20.csv')
pd.set_option('display.max_rows', None)
pd.set_option('display.max_columns', None)
print(data)