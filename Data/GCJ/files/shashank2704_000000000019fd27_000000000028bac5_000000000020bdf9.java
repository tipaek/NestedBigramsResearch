include <bits/stdc++.h>
using namespace std;
bool comp(const pair<pair<int,int>  ,int> &a,const pair<pair<int,int> ,int> &b)
{
    return a.first.second<b.first.second;
    
}
int main()
{
    int f;
    cin>>f;
    for(int i=0;i<f;i++)
    {
        count<<"Case #"<<i+1<"; ";
        string a="";
        int m,s0,d;
        cin>>m;
        map<char,int> np;
        np['J']=0;
        np['C']=0;
        for(int i=0;i<m;i++)
        a+="0";
        vector<pair<pair<int,int>  ,int> > v1;
        for(int i=0;i<m;i++)
        {
            cin>>s0>>d;
            v1.push_back(make_pair(make_pair(s0,d),i));
        }
        sort(v1.begin(),v1.end(),comp);
        a[v1[0].second]='C';
        np['C']=v1[0].first.second;
        for(int i=1;i<m;i++)
        {
            if(a=="IMPOSSIBLE")
            continue;
            else
            {
                if(v1[i].first.first>=v2[i-1].fisrt.second)
                {
                    ans[v1[i].second]=ans[v1[i-1].second];
                    np[ans[v1[i].second]]=v1[i].first.second;
                    
                }
                else
                {
                    char z=a[v1[i-1].second]=='J'?'C':'J';
                    if(np[z]<=v1[i].fisrt.fisrt)
                    {
                        a[v1[i].second]=z;
                        np[z]=v1[i].first.second;
                        
                    }
                    else
                    {
                        a="IMPOSSIBLE";
                        break;
                    }
                }
            }
            count<<a<<endl;
        }
        return 0;
    }
}