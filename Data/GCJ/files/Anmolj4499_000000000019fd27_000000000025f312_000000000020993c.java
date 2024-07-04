#include <bits/stdc++.h>


using namespace std;

int main() {
 int testcases;
 cin >> testcases;
 for (int a = 1; a <= testcases; x++) {
  int k = 0, r = 0, c = 0;
  int n;
  cin >> n;
  vector<vector<int>> arr(n, vector<int>(n));
   bool redone = false;
  set<int> row;  
  for (int l = 0; l < n; l++) {
   row.clear();
   redone = false;
   for (int m = 0; m < n; m++) {
    int values;
    cin >> values;
    arr[l][m] = values;
    if (l == m) {
     k += values;
    }
    if (!redone && row.count(value) == 1) {
     r++;
     redone = true;
    }
    else {
     row.insert(values);
    }
   }
  }

  set<int> column; bool cdone = false;
  for (int l = 0; l < n; l++) {
   column.clear();
   cdone = false;
   for (int m = 0; m < n; m++) {
    if (!cdone && column.count(arr[m][l]) == 1) {
     c++;
     cdone = true;
    }
    else
     column.insert(arr[m][n]);
   }
  }
  cout << "Case #" << a << ": " << k << " " << r << " " << c << "\n";
 }
 return 0;
}