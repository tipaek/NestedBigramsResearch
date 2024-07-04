#include <bits/stdc++.h>

using namespace std;

bool sortbysec(const pair<int, pair<int,int> > &a, 
              const pair<int, pair<int,int> > &b) 
{ 
    return (a.second.second < b.second.second); 
} 

void process(vector<pair<int, pair<int, int> > > &v, int l, int caseno){
    sort(v.begin(), v.end(), sortbysec);

    string assigned_tasks = string(l, ' ');

    int c_task = v[0].second.second;
    assigned_tasks[v[0].first] =  'C';
    int j_task = 0;
    

    for(int i = 1; i < l; i++){
        if(v[i].second.first >= c_task){ //if c is free at start of task
            c_task = v[i].second.second;
            assigned_tasks[v[i].first] =  'C';
        }
        else if(v[i].second.first >= j_task){
            j_task = v[i].second.second;
            assigned_tasks[v[i].first] =  'J';
        }
        else{
            assigned_tasks.clear();
            assigned_tasks.append("IMPOSSIBLE");
            break;
        }
    }

    cout << "Case #" << caseno << ": " << assigned_tasks << endl;
}

int main(){
    int cases; 
    cin >> cases;
    
    int mSize;
    for(int i = 0; i < cases; i++){
        cin >> mSize;
        vector<pair<int, pair<int, int> > > v(mSize);
        for(int j = 0; j < mSize; j++){
            pair<int, int> p;
            cin >> p.first >> p.second;
            v[j] = make_pair(j, p);
        }
        process(v, mSize, i+1);
    }
    return 0;
}
