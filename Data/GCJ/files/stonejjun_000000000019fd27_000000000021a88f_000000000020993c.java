#include<bits/stdc++.h>
using namespace std;
typedef long long int ll;

ll arr[101][101];
vector<ll> vr[101];
vector<ll> vc[101];
int main(){
	ll t,z;
	scanf("%lld",&z);
	for(t=1;t<=z;t++){
		ll i,j,k,l,m,n,ans1=0,ans2=0,ans3=0;
		scanf("%lld",&n);

		for(i=1;i<=n;i++)
			for(j=1;j<=n;j++){
				scanf("%lld",&arr[i][j]);
				vr[i].push_back(arr[i][j]);
				vc[j].push_back(arr[i][j]);
				if(i==j) ans1+=arr[i][j];
			}
		for(i=1;i<=n;i++){
			sort(vr[i].begin(), vr[i].end());
			for(j=1;j<n;j++){
				if(vr[i][j]==vr[i][j-1]){
					ans2++;
					break;
				}
			}
		}

		for(i=1;i<=n;i++){
			sort(vc[i].begin(), vc[i].end());
			for(j=1;j<n;j++){
				if(vc[i][j]==vc[i][j-1]){
					ans3++;
					break;
				}
			}
		}

		printf("Case #%lld: %lld %lld %lld\n",t,ans1,ans2,ans3);
		for(i=1;i<=n;i++){
			vr[i].clear();
			vc[i].clear();
		}

	}
}