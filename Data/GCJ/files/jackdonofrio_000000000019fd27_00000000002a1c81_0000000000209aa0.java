#include <bits/stdc++.h>

using namespace std;

int square[60][60], n, k, t;
bool r_safe[60][60], c_safe[60][60], solved;

void solver(int r, int c, int m) {
    if (r == n && c == n + 1 && m == k && !solved) {
        solved = true;
        cout << "Case #" << t << ": " << "POSSIBLE\n";
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                cout << square[i][j] << " ";
            }
            cout << "\n";
        }
        return;
    } else if (r > n) {
        return;
    } else if (c > n) {
        solver(r + 1, 1, m);
    }
    for (int i = 1; i <= n && !solved; ++i) {
        if (!r_safe[r][i] && !c_safe[c][i]) {
            r_safe[r][i] = c_safe[c][i] = true;
            if (r == c) {
                m += i;
            }
            square[r][c] = i;

            solver(r, c + 1, m);

            r_safe[r][i] = c_safe[c][i] = false;
            if (r == c) {
                m -= i;
            }
            square[r][c] = 0;
        }
    }
}

int main() {
    int T;
    scanf(" %d", &T);
    for (t = 1; t <= T; ++t) {
        scanf(" %d %d", &n, &k);
        solver(1, 1, 0);
        if (!solved) {
            cout << "Case #" << t << ": " << "IMPOSSIBLE\n";
        }
        solved = false;
    }
    return 0;
}