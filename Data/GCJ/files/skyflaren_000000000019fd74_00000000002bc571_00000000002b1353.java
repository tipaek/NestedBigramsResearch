#include <bits/stdc++.h>
using namespace std;
#define pb push_back
#define f first
#define s second
#define ln "\n"
#define pii pair <int, int>
typedef long long ll;
typedef unsigned long long ull;
#define FILL(a, b) memset(a, b, sizeof(a))
const int INF = 0x3F3F3F3F;
const int MAX = 1e3 + 5;

ll t, n;

int main() {
    ios_base::sync_with_stdio(0), cin.tie(0), cout.tie(0);
    cin >> t;
    for(int z = 1; z <= t; z++){
        cin >> n; cout << "Case #" << z << ":" << ln << "1 1\n";
        ll r = 2, k = 1, sum = 1, prev = 0;

        while(sum + prev + 1 <= n){
            cout << r << " " << k << ln;
            r++; k++; prev++; sum += prev; 
            // cout << " " << sum << ln;
        }
        k++;
        // cout << sum << "lo\n";
        while(sum + 1 <= n){
            // cout << r << " " << k << ln;
            r++; k++; sum++;
        }
        cout << sum;
    }
}