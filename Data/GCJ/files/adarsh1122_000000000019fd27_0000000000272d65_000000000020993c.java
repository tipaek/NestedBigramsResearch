#include <iostream>
using namespace std;
int main()
{
    int t;
    cin>>t;
    int c[t][3];
    int n;
    for(int k=0;k<t;k++)//Test Case
        {
   
       
        cin>>n;
        int arr[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                cin>>arr[i][j]; //Input Matrix
            }
        }
       
        c[k][0]=0;
        int b=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i==j)
                {
                    c[k][0]=c[k][0]+arr[i][j];//Diagonal Addition
                   
                }
            }
           
        }
       
        //Row repeat Calculation
        int d=0;
        c[k][1]=0;
        for(int i=0;i<n;i++)
        {
            int m=0;
            for(int j=0;j<n;j++)
            {
                b=arr[i][j];
                d=0;
                 for(int p=0;p<n;p++)
                 {
                     if(b==arr[i][p])
                     {
                         d++;
                       
                     }
                     
                     
                 }
                 if(d>1 && m==0)
                 {
                     m=1;
                     c[k][1]++;
                 }
            }
        }//exit for row
       
        //Col repeat cal
        d=0;
        c[k][2]=0;
        for(int i=0;i<n;i++)
        {
            int m=0;
            for(int j=0;j<n;j++)
            {
                b=arr[j][i];
                d=0;
                 for(int p=0;p<n;p++)
                 {
                     if(b==arr[p][i])
                     {
                         d++;
                      }
                     
                 }
                 if(d>1 && m==0)
                 {
                     m=1;
                     c[k][2]++;
                 }
            }
        }//exit for col
     cout<<"Case #"<<(k+1)<<": ";
    for(int q=0;q<3;q++){
        //
        cout<<c[k][q]<<" ";
       
    }
    cout<<"\n";
       
  }//k Loop  

    return 0;
}
