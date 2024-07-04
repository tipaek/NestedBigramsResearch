#include<bits/stdc++.h>
using namespace std;
#define INIT  ios_base :: sync_with_stdio(0); cin.tie(); cout.tie();mt19937 rng(chrono::steady_clock::now().time_since_epoch().count());
#define mp make_pair
#define pb push_back
#define ft first
#define sc second
#define ll long long
#define pii pair<int, int>
#define int ll



int T; vector<ll> a; int n, D;








int32_t main(){
INIT
cin>>T;

for(int t=1; t<=T; t++){
    cin>>n>>D;
    a.resize(n+5);
    for(int i=1; i<=n; i++){
        cin>>a[i];
    }

    sort(a.begin()+1, a.begin()+n+1 );
    ll cuts=D-1;



    for(int i=1; i<=n; i++){
            ll x=0;
            ll d=1;
        for(int j=i+1; j<=n; j++){
            if(a[j]%a[i]==0 ){  x+=min(a[j]/a[i]-1, D-d  ); d+=a[j]/a[i]; if(d>=D){break;}   }
        }

        if(d>=D){cuts=min(cuts, x);}
        else{

        for(int j=i+1; j<=n; j++){
                if(a[j]%a[i]>0 ){ x+=min(a[j]/a[i], D-d ); d+=a[j]/a[i]; if(d>=D){break;} }
        }

        if(d>=D){cuts=min(cuts, x); }

        }

    }




    cout<<"Case #"<<t<<": "<<cuts<<"\n";

}



return 0;
}
