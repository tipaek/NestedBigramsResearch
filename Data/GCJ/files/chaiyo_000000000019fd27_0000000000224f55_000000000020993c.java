#include <iostream>
#include <set> 
#include <string>

using namespace std;

void go (int *arr2dR, int *arr2dC, int N, int T) {
    int numberOfRepeatedRow = 0;
    int numberOfRepeatedColumn = 0;
    int traceSum = 0;
    for (int t = 0; t < N; t++) {
        set<int> hang;
        set<int> cot;
        for (int e = 0; e < N; e++) {
            if (e == t)
                traceSum += arr2dR[t*N + e];
            
            hang.insert(arr2dR[t*N + e]);
            cot.insert(arr2dC[t*N + e]);
        }
        if (hang.size() != N)
            numberOfRepeatedRow++;
        if (cot.size() != N)
            numberOfRepeatedColumn++;			
    }

    cout << "Case #" << T << ": " << traceSum << " " << numberOfRepeatedRow << " " << numberOfRepeatedColumn << "\n";
}

int32_t main() {
    ios_base::sync_with_stdio(false);
    int T, N, num;

	cin >> T;
    for (int t = 1; t <= T; t++)
	{
		cin >> N;
		int arr2dR[N*N];
		int arr2dC[N*N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cin >> num;
                arr2dR[i*N + j] = num;
                arr2dC[j*N + i] = num;
            }
        }
		go(arr2dR, arr2dC, N, t);
	}
}