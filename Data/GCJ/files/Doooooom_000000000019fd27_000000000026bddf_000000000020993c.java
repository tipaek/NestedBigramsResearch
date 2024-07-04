#include<iostream>
#include<bits/stdc++.h>
#include<vector>
#define int long long int
#define rdj ios_base::sync_with_stdio(false);cin.tie(0);cout.tie(0);
using namespace std;

int main()
{
    rdf
    int T;
    cin>>T;
    for(int tc=0;tc<T;tc++)
    {
        int N,t=0,r=0,c=0;
        cin>>N;
        vector<vector<int> > v(N);
        for(int i=0;i<N;i++)
        {
            v[i].resize(N);
            for(int j=0;j<N;j++)
            {
                cin>>v[i][j];
            }
        }
        
        for(int i=0;i<N;i++)
        {
            t = t + v[i][i];
        }
        for(int i=0;i<N;i++)
        {
            map<int ,int> mp;
            map<int, int> mp2;
            map<int, int>::iterator it;
            map<int, int>::iterator it2;
            for(int j=0;j<N;j++)
            {
                mp[v[i][j]] = mp[v[i][j]]+1;
                mp2[v[j][i]] = mp2[v[j][i]]+1;
                // cout<<"MP"<<(v[i][j])<<"="<<(mp[v[i][j]])<<"MP2["<<(v[j][i])<<"]="<<(mp2[v[j][i]])<<endl;
            }
            int r_fl=0,c_fl=0;
            for(it=mp.begin(),it2=mp2.begin(); it!=mp.end(),it2!=mp2.end(); it++,it2++)
            {
                if(it->second > 1)
                {
                // 	cout<<"mp = "<<it->second ;
                    r_fl = 1;
                }
                if(it2->second > 1)
                {
                // 	cout<<"mp2 = "<<it2->second;
                    c_fl = 1;
                }
        	}
        	if(r_fl == 1)
        		r++;
        	if(c_fl == 1)
				c++;	
        }
        cout<<"Case #"<<tc+1<<": "<<t<<" "<<r<<" "<<c<<endl;
    }
    return 0;
}