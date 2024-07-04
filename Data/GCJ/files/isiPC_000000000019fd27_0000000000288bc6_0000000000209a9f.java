#include<bits/stdc++.h>

using namespace std;

string getSolutions(string line) {
	string res;
	bool opened = false;
	for (int i = 0; i < line.length(); i++) {
		if (line[i] == '1') {
			if (opened) {
				res += line[i];
			} else {
				opened = true;
				res += "(";
				res += line[i];
			}
		} else {
			if (opened) {
				opened = false;
				res += ")";
				res += line[i];
			} else {
				res += line[i];
			}
		}
		if (line[i] == '1' && (i == line.length() - 1)){
			res += ")";
		}
	}
	
	return res;
}

void printVec(vector<vector<int> > vec) {
	for ( const vector<int> &v : vec )
	{
	   for ( int x : v ) cout << x << ' ';
	   cout << endl;
	}
}

int main() {
	int T;
	cin >> T;
	
	for (int testNo = 1; testNo <= T; testNo++) {
		string line;
		cin >> line;
		
		string sol = getSolutions(line);
		cout << "Case #" << testNo << ": " << sol << endl;
	}
}