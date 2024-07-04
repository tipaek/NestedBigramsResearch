#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

int main() {
    int q, size;
    cin >> q;
    for( int _ = 0; _ < q; _++){
        cin >> size;
        vector<vector<int>> columns;
        int firstTotal = 0;
        int sameRows = 0;
        int sameColumns = 0;
        for (int i = 0 ; i < size; i++){
            vector<int> row;
            for(int j = 0 ; j < size; j++){
                int a;
                cin >> a;
                row.push_back(a);
                if(j == i){
                    firstTotal += a;
                }
                if(i == 0){
                    vector<int> v;
                    columns.push_back(v);
                }
                columns[j].push_back(a);
            }
            sort(row.begin(), row.end());
            int temp = row[0];
            for(int p = 1; p < row.size(); p++){
                if(temp == row[p]){
                    sameRows++;
                    break;
                }
                temp = row[p];
            }
        }
        for(int i = 0; i < columns.size(); i++){
            sort(columns[i].begin(), columns[i].end());
            int temp = columns[i][0];
            for(int j = 1; j < columns[i].size(); j++){
                if(temp == columns[i][j]){
                    sameColumns++;
                    break;
                }
                temp = columns[i][j];
            }
        }
        cout << "Case #" << q+1 << ": " << firstTotal << " " << sameRows << " " << sameColumns << endl;
    }
    return 0;
}
