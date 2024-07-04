#include <iostream> // includes cin to read from stdin and cout to write to stdout
#include <unordered_map>
using namespace std; // since cin and cout are both in namespace std, this saves some text
int main() {
  int t, n;
  cin >> t;
  for (int i = 1; i <= t; ++i) {
    int Diag = 0;
    cin >> n ; 
    int mat[n][n];
    for (int i = 0; i < n; ++i)
    {
      for (int j = 0; j < n; ++j)
      {
        cin >> mat[i][j];
      }
    }
    for (int i = 0; i < n; ++i)
    {
      Diag += mat[i][i];
    }
    int Row = 0, Column = 0;
    for (int i = 0; i < n; ++i)
    {
      unordered_map<int, int> freq; 

      for (int j = 0; j < n; j++) 
          freq[mat[i][j]]++; 
      unordered_map<int, int>:: iterator itr; 
      for (itr=freq.begin(); itr!=freq.end(); itr++) 
      {
          if (itr->second > 1) 
          { 
              Row++; 
              break;
          } 
      }
    } 

    for (int i = 0; i < n; ++i)
    {
      unordered_map<int, int> freq; 

      for (int j = 0; j < n; j++) 
          freq[mat[j][i]]++; 
      unordered_map<int, int>:: iterator itr; 
      for (itr=freq.begin(); itr!=freq.end(); itr++) 
      {
          if (itr->second > 1) 
          { 
              Column++; 
              break;
          } 
      }
    } 
    cout << "Case #" << i << ": " << Diag << " " << Row << " " << Column << endl;
      }
  return 0;
}