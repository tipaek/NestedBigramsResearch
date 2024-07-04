#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

bool overlap(int x1, int y1, int x2, int y2) {
    
    if(x2 > x1 && x2 < y1) {
        return true;
    }
    if(y2 > x1 && y2 < y1) {
        return true;
    }
    if(x1 > x2 && x1 < y2) {
        return true;
    }
    if(y1 > x2 && y1 < y2) {
        return true;
    }
    if(x1 == x2 && y1 == y2) {
        return true;
    }
    return false;
}

bool sortinrev(const pair<int, pair<int,int>> &a,  
               const pair<int, pair<int,int>> &b) 
{ 
       return (a.second.first < b.second.first); 
} 

int main()
{
    int N;
    cin >> N;
    for(int i = 0; i < N; i++) {
        int M;
        cin >> M;
        vector<pair<int, pair<int,int>>> schedule;
        vector<pair<int, int>> first_tasks;
        vector<pair<int, int>> second_tasks;
        string str = "";
        for(int j = 0; j < M; j++) {
            int start, finish;
            cin >> start >> finish;
            schedule.push_back(make_pair(j, make_pair(start, finish)));
            str += " ";
        }
        sort(schedule.begin(), schedule.end(), sortinrev);
        // last, first
        for(int j = 0; j < M; j++) {
            //cout << schedule[j].second.second << endl;
            bool first_entered = false;
            for(int k = 0; k < first_tasks.size(); k++) {
                if(overlap(schedule[j].second.first, schedule[j].second.second, first_tasks[k].first, first_tasks[k].second)) {
                    first_entered = true;
                    continue;
                }
            }
            if(!first_entered) {
                str[schedule[j].first] = 'C';
                first_tasks.push_back(make_pair(schedule[j].second.first, schedule[j].second.second));
                continue;
            }
            bool second_entered = false;
            for(int k = 0; k < second_tasks.size(); k++) {
                if(overlap(schedule[j].second.first, schedule[j].second.second, second_tasks[k].first, second_tasks[k].second)) {
                    second_entered = true;
                    continue;
                }
            }
            if(!second_entered) {
                str[schedule[j].first] = 'J';
                second_tasks.push_back(make_pair(schedule[j].second.first, schedule[j].second.second));
                continue;
            }
            str = "IMPOSSIBLE";
            break;
        }
        cout << "CASE #" << i + 1 << ": " << str << endl;
    }

    return 0;
}