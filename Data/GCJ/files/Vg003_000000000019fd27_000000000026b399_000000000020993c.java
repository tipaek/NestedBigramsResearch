#include<iostream>
#include<vector>
using namespace std;
int main()
{
    int t;
    cin>>t;
    for(int i=0;i<t;i++)
    {
        int n;
        cin>>n;
        int arr[n][n],sum=0,index1=0,r=0,c=0,index2=0;
        
        vector <bool> ma(n,false);
        for(int j=0;j<n;j++)
        {
            vector <bool> mark(n,false);
            for(int k=0;k<n;k++)
            {
                cin>>arr[j][k];
                if(j==k)
                  sum=sum+arr[j][k];
                index1=arr[j][k];
                mark[index1-1]=true;
            }
            for(int k=0;k<n;k++)
            {
                if(mark[k]==false){
                  r=r+1;
                  break;
                }
            }
        }
        for(int j=0;j<n;j++)
        {
            vector <bool> ma(n,false);
            for(int k=0;k<n;k++)
            {
                index2=arr[k][j];
                ma[index2-1]=true;
            }
            for(int k=0;k<n;k++)
            {
                if(ma[k]==false){
                   c=c+1;
                   break;
                }
            }
        }
     
        cout<<"Case #"<<i+1<<": "<<sum<<" "<<r<<" "<<c<<endl;
    }
}