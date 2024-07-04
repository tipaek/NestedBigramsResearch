#include <iostream>
#include <vector>
#include <windows.h>
using namespace std;
struct result
{
    int S;
    int count_row;
    int count_col;
};

int main() 
{
    int i,j,k,s,n,m;
    bool row;
    bool *col;
    int **a;
    vector<result> res;
    result r;
    cin>>m;
    for (i=0;i<m;i++)
    {
        cin>>n;
        a = new int *[n];  
        for (j = 0;j < n; j++)
	   a[j] = new int[n];
        col = new bool[n];
        for (j = 0;j < n; j++) col[j] = false;
        memset(&r, 0, sizeof(result));

        for (j=0;j<n;j++)
        {
            row = false;
            for (k=0;k<n;k++)
            {
                cin>>a[j][k];
                if(j==k) r.S+=a[j][k];
                if (k>0 && !row)
                {
                    for (s=0;s<k;s++)
                    {
                        if(a[j][s] == a[j][k])
                        {
                            row = true;
                            r.count_row++;
                            break;
                        }
                    }
                }
                if (j>0 && !col[k])
                {
                    for (s=0;s<j;s++)
                    {
                        if(a[s][k] == a[j][k])
                        {
                            col[k] = true;
                            r.count_col++;
                            break;
                        }
                    }
                }
            }
        }
        for (j = 0;j < n;j++) delete []a[j];
        delete []a;
        delete []col;
        res.push_back(r);
    }
    for (i=0;i<res.size();i++)
    {
        cout<<"Case #" << i+1 <<":"<<res[i].S<<" "<<res[i].count_row<<" "<<res[i].count_col<<" "<<endl;
    }
    res.clear();
    system("pause");
    return 0;
}