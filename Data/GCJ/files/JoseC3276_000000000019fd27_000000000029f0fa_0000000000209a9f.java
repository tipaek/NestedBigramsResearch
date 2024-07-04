#include <iostream>
#include <vector>
#include <windows.h>
using namespace std;
struct result
{
    int a;
    int b;
    int c;
};

int main() {
    int i,j,k,l,n,m;
    bool row;
    bool *col;
    int **matrix;
    vector<result> res;
    result r;
    cin>>n;
    for (i=0;i<n;i++)
    {
        cin>>m;
        matrix = new int *[m];  
        for (j = 0;j < m; j++) matrix[j] = new int[m];
        col = new bool[m];
        for (j = 0;j < m; j++) col[j] = false;
        memset(&r, 0, sizeof(result));

        for (j=0;j<m;j++)
        {
            row = false;
            for (k=0;k<m;k++)
            {
                cin>>matrix[j][k];
                if(j==k) r.a+=matrix[j][k];
                if (k>0 && !row)
                {
                    for (l=0;l<k;l++)
                    {
                        if(matrix[j][l] == matrix[j][k])
                        {
                            row = true;
                            r.b++;
                            break;
                        }
                    }
                }

                if (j>0 && !col[k])
                {
                    for (l=0;l<j;l++)
                    {
                        if(matrix[l][k] == matrix[j][k])
                        {
                            col[k] = true;
                            r.c++;
                            break;
                        }
                    }
                }
            }
        }

        for (j = 0;j < m;j++)  delete []matrix[j];
        delete []matrix;
        delete []col;
        res.push_back(r);
    }

    for (i=0;i<res.size();i++)
    {
        cout<<"Case #" << i+1 <<":"<<res[i].a<<" "<<res[i].b<<" "<<res[i].c<<" "<<endl;
    }
    res.clear();
    system("pause");
    return 0;
}