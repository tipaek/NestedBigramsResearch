#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
int main(){
  int T,n;
  cin>>T;
  for(int i =0; i<=T;i++){
    cin>>n;
    vector<pair<pair<int,int>,int> > assignment(n);
    vector<int> position(1441,0);
    for(int j =0; j<n;j++){
      cin>>assignment[j].first.first;
      cin>>assignment[j].first.second;
      assignment[j].second = j;
      for(int k = assignment[j].first.first;k<assignment[j].first.second;k++) position[k]++;
    }
    sort(assignment.begin(), assignment.begin()+n);
    vector<char> string(n,'J');
    string[0] = 'C';
    int exist = assignment[0].first.second;
    for(int j = 1; j<n;j++){
      if(exist<=assignment[j].first.first){
        string[j] = 'C';
        exist = assignment[j].first.second;
      }
    }
    bool poss = true;
    for(int k =0; k<position.size();k++){
      if(position[k] >= 3) poss = false;
    }
    if(poss){
      for(int i =0; i<n; i++)
      cout<<string[i];
    }
    else cout<<"IMPOSSIBLE"<< "\n";
  }
}
