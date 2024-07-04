
#include<bits/stdc++.h>
using namespace std;
int main()
{
    
  
  int t,q=0;
  scanf("%d",&t);
  while(t!=0)
  
  {
    int a=0,b=0,c=0;
    int n;
    scanf("%d",&n);
    int z[n][n];
    
    
    for (int i = 0; i < n; ++i)
    {  
         int cr[100] = {0};
        for (int j = 0; j < n; ++j)
        {
            scanf("%d",&z[i][j]);
            if(i==j)
                a = a + z[i][j];

            cr[z[i][j]]++;
            if(cr[z[i][j]]>1)
            {  
                if(b<=i)
                b++;
            }    
        }
    }
      for (int i = 0; i < n; ++i)
      {
        int cc[100] = {0};
        for (int j = 0;j < n; ++j)
        {
          cc[z[j][i]]++;
          if(cc[z[j][i]] >1)
          {
            if(c<=j)
              c++;
          }
        }
      }
      q++;

  printf("case #%d : %d %d %d",q,a,b,c);
  printf("\n");
  
  t--;
  
    
  }
  
  return 0;
}