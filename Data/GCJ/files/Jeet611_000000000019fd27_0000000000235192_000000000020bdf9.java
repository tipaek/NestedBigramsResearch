#include <bits/stdc++.h>

using namespace std;

void solve(){
    int n, ie[1001], j, i, t;
    int s[1001], e[1001];
    bool u[1001], ok[1001];
    cin>>n;
    for(i=0;i<n;i++){
        cin>>s[i]>>e[i];
        ie[i]=i;
        ok[i]=false;
        u[i]=false;
    }
    for(i=1;i<n;i++){
        for(j=0;j<n-i;j++){
            if(e[j]>e[j+1]){
                t=e[j];
                e[j]=e[j+1];
                e[j+1]=t;
                t=s[j];
                s[j]=s[j+1];
                s[j+1]=t;
                t=ie[j];
                ie[j]=ie[j+1];
                ie[j+1]=t;
            }
        }
    }
    j=0;
    for(i=0;i<n;i++){
        if(s[i]<j){
            continue;
        }
        j=e[i];
        u[i]=true;
    }
    int k=0;
    for(i=0;i<n;i++){
        if(u[i]==true){
            ok[ie[i]]=true;
            k++;
        }
    }
    if(k==n){
        for(i=0;i<n;i++){
            cout<<"C";
        }
    }
    else{
        j=0;
        int di=0;
        for(i=0;i<n;i++){
            if(u[i]==true){
                continue;
            }
            else{
                if(s[i]<j){
                    cout<<"IMPOSSIBLE";
                    di=1;
                    break;
                }
                j=e[i];
            }
        }
        if(di==0){
            for(i=0;i<n;i++){
                if(ok[i]==true){
                    cout<<"C";
                }
                else{
                    cout<<"J";
                }
            }
        }
        
    }
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