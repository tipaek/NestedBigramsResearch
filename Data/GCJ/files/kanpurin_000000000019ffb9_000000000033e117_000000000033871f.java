/**
 *   @FileName	b.cpp
 *   @Author	kanpurin
 *   @Created	2020.05.17 00:53:22
**/

#include "bits/stdc++.h" 
using namespace std; 
typedef long long ll;

int main() {
    int t;cin >> t;
    for (int i = 0; i < t; i++) {
        int c,d;cin >> c >> d;
        vector<int> a(c,0);
        vector<int> ans;
        for (int i = 0; i < c-1; i++) {
            cin >> a[i + 1];
            a[i + 1] *= -1;
        }
        for (int i = 0; i < d; i++) {
            int u,v;cin >> u >> v;
            u--;v--;
            ans.push_back(abs(a[u] - a[v]));
        }
        cout << "Case #" << i + 1 << ": ";
        for(auto p : ans) {
            cout << p << " ";
        }
        cout << endl;
    }
    return 0;
}