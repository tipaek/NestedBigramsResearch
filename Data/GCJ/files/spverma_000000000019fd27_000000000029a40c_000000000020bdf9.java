#include <bits/stdc++.h>
using namespace std;
#define ll long long int
#define maxy __LONG_LONG_MAX__
#define mod 1000000007
int main()
{
	int tc;
    cin>>tc;
    int cnt=0;
    while(tc--)
    {
        cnt++;
        int n;
        cin>>n;
        int flag=0;
        int a[n+5],b[n+5],c[1445]={},ja[1445]={};
        //string s[1445] = "";
        string ans="";
        for(int i=0;i<n;i++)
        {
            cin>>a[i]>> b[i];
            int cc=0,jj=0;
            for(int j=a[i];j<b[i];j++)
            {
                if(c[j]==1)
                {
                    cc=1;
                    
                }
                if(ja[j]==1)
                jj=1;
            }
            if(cc==1)
            {
                if(jj==1)
                flag=1;
                else 
                for(int j=a[i];j<b[i];j++)
                {
                    ja[j]=1;
                }
                ans+="J";
            }
            else 
            {
                for(int j=a[i];j<b[i];j++)
                {
                    c[j]=1;
                }
                ans+="C";
            }
        }
        if(flag==1)
        ans = "IMPOSSIBLE";
        cout<< "Case #"<< cnt<< ": "<< ans<<endl;
    }
	return 0;
}
