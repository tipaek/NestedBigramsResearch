#include<bits/stdc++.h>
using namespace std;
int main()
{
    int t,sq=1;
    cin>>t;
    while(t-->0)
    {
        int n;
        cin>>n;
        int a[n],b[n],i;
        for(i=0;i<n;i++)
        {
            cin>>a[i]>>b[i];
        }
        int c=0,j=0,summ=0,j1;
        char ch[n];
        for(j1=0;j1<=1440;j1++)
        {
            if(c!=0)
            c--;
            if(j!=0)
            j--;
            for(int k=0;k<n;k++)
            {
                if(j1==a[k])
                {
                    if(c==0)
                    {
                        c=b[k]-a[k];
                        ch[k]='J';
                    }
                    else if(j==0)
                    {
                        j=b[k]-a[k];
                        ch[k]='J';
                    }
                    else
                    {
                        cout<<"Case #"<<sq<<": "<<"IMPOSSIBLE"<<endl;
                        summ=-1;
                        break;
                    }
                }
            }
        }
        if(summ==0)
        { string s(ch);
        cout<<"Case #"<<sq<<": "<<s<<endl;
        }
        sq++;
        
    }
}