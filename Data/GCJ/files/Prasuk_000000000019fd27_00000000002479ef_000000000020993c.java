#include<bits/stdc++.h>
using namespace std;
int main()
{
    ios_base::sync_with_stdio(0);
	int t;
	cin>>t;
	for (int i = 0; i < t; ++i)
	{
		
		int n;
	    cin>>n;
    	int m[n+1][n+1];
	    int trace=0,r=0,c=0;
	    int x[101][101]= {0};
	    int flag=0;
        int flag2[101]= {0};
	    for (int i = 0; i < n; ++i)
	    {	flag=0;
	        int countr[256] = {0};
		    for (int j = 0; j < n; ++j)
		    {
			    cin>>m[i][j];
			    if(i==j)
				    trace = trace + m[i][j];

			    countr[m[i][j]]++;
                x[m[i][j]][j]++;
                if(countr[m[i][j]]>1 && flag==0)
                {
                    r++;
                    flag=1;
                }
                if((x[m[i][j]][j])>1 && flag2[j]==0)
                {
                    flag2[j]=1;
                    c++;
                }		
		    }
	    }
	    cout<<"Case #"<<i+1<<": "<<trace<<" "<<r<<" "<<c<<" "<<endl;
	}
	return 0;
}