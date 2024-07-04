#include<bits/stdc++.h>
using namespace std;

int main(){
    int t;
    cin>>t;
    int l=1;
    while(t--){
        int n,x,y;
        cin>>n;
        pair <int,int> p[n];
        for(int i=0;i<n;i++){
            cin>>x>>y;
            p[i]=make_pair(x,y);
        }
        pair <int,int> d[n];
        for(int i=0;i<n;i++){
            d[i]=p[i];
        }
        sort(p,p+n);
        string s="CJ",z="";
        int c=0,j=1;
        for(int i=2;i<n;i++){
            if(p[i].first>=p[c].second){
                s+='C';
                c=i;
                continue;
            }
            if(p[i].first>=p[j].second){
                s+='J';
                j=i;
                continue;
            }
            s="IMPOSSIBLE";
            break;
        }
        if(s=="IMPOSSIBLE")
            z=s;
        else{
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(p[j]==d[i]){
                        z+=s[j];
                        p[j]=make_pair(-1,-1);
                        break;
                    }
                }
            }
        }
        cout<<"Case #"<<l<<": "<<z<<endl;
        l++;
    }
}