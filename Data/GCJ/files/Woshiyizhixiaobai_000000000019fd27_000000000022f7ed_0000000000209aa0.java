#include <bits/stdc++.h>

using namespace std;

void solve(){
    int n, a[101][101];
    long long ans=0, ans1=0, ans2=0;
    cin>>n;
    for(int i=0;i<n;i++){
        int map[101]={0};
        int d=0;
        for(int j=0;j<n;j++){
            cin>>a[i][j];//y, x
            if(i==j){
                ans+=a[i][j];
            }
            if(map[a[i][j]]!=0){
                d=1;
            }
            map[a[i][j]]=-1;
        }
        if(d==1){
            ans1++;
        }
    }
    for(int j=0;j<n;j++){
        int map[101]={0};
        int d=0;
        for(int i=0;i<n;i++){
            if(map[a[i][j]]!=0){
                d=1;
            }
            map[a[i][j]]=-1;
        }
        if(d==1){
            ans2++;
        }
    }
    cout<<ans<<" "<<ans1<<" "<<ans2;
    cout<<endl;
}
int main()
{
    int t, i=1;
    cin>>t;
    while(t--){
        cout<<"Case #"<<i<<": ";
        solve();
        i++;
    }
    return 0;
}