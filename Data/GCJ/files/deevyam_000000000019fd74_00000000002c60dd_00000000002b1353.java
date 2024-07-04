#include<iostream>
#include<algorithm>
#include<math.h>
#include<string>
#include<string.h>
#include<vector>
#include<string.h>
using namespace std;
#define FIO ios_base::sync_with_stdio(false); cin.tie(NULL)
#define pii pair<int, int>
#define pll pair<long long, long long>

int T, N;
long long value[505][505];
bool visit[505][505];
vector<pii> route;
int mv1[6] = { -1,-1,0,0,1,1 };
int mv2[6] = { -1,0,-1,1,0,1 };
int cnt = 1;

void init() {
  int p = 0;
  for (int i = 1; i < 501; i++)
  {
    value[i][1] = 1;
    value[i][0] = 0;

    value[i][1 + p] = 1;
    value[i][2 + p] = 0;
    p++;
  }
  p = 1;
  for (int i = 2; i < 501; i++)
  {
    for (int j = 1; j <= i + p; j++)
    {
      value[i][j] = value[i - 1][j - 1] + value[i - 1][j];
    }
    p++;
  }
}

bool DFS(int x, int y, long long sum) {
  
  if (sum > N)  return false;
  if (visit[x][y])  return false;
  visit[x][y] = true;

  if (sum == N) {
    cout << "Case #" << cnt++ << ":" << "\n";
    for (int i = 0; i < route.size(); i++)
    {
      cout << route[i].first << " " << route[i].second << "\n";
    }
    return true;
  }

  for (int i = 0; i < 6; i++)
  {
    int next_x = x + mv1[i];
    int next_y = y + mv2[i];

    if (next_x <= 0 || next_y <= 0 || next_x >= 501 || next_y >= 501)  continue;
    if (next_x < next_y)  continue;

    route.push_back(make_pair(next_x, next_y));
    if (DFS(next_x, next_y, sum + value[next_x][next_y]))  return true;
    route.pop_back();
  }
  visit[x][y] = false;
  return false;
}

int main(void) {
  FIO;

  cin >> T;

  init();

  while (T--) {

    cin >> N;

    memset(visit, false, sizeof(visit));
    route.clear();
    
    route.push_back(make_pair(1, 1));

    DFS(1, 1, 1);
  }
}
