#include <iostream>
#include <iomanip>
#include <vector>
#include <wchar.h>
#include <stdio.h>
#include <string.h>
using namespace std;

struct result
{
    int a,b,c;
};

int main()
{
    int num;
    bool row;
    bool * collumn;
    int ** matrix;
    vector<result>res;
    result r;
    cin >> num;
    
    for(int i = 0; i < num; i++)
    {
        int value; // value = m
        cin >> value;
        matrix = new int *[value];
        
        for(int j = 0; j < value; j++)
        {
            matrix[j] = new int[value];
            collumn = new bool[value];
        }
        
        for(int j = 0; j < value; j++)
        {
         collumn[j] = false;
         memset (&r, 0, sizeof(result));
        }
        
        for(int j = 0; j < value; j++)
        {
            matrix[j] = new int[value];
            collumn = new bool[value];
        }
        
        for(int j= 0; j < value; j++)
        {
            row = false;
            
            for(int k = 0; k < value; k++)
            {
                cin >> matrix[j][k];
                if(j == k)
                {
                   r.a+=matrix[j][k];  
                }
                
                 if (k > 0 && !row)
                {
                    for (int l = 0; l < k; l++)
                    { 
                        if(matrix[j][l] == matrix[j][k])
                        {
                            row = true;
                            r.b++;
                            break;
                        }
                    }
                }
                
                  if (j > 0 && !collumn[k])
                {
                    for (int l = 0; l < j; l++)
                    {
                        if(matrix[l][k] == matrix[j][k])
                        {
                            collumn[k] = true;
                            r.c++;
                            break;
                        }
                    }
                }
            }
        }

for (int j = 0; j < value; j++)
 {
    delete []matrix[j];
    delete []matrix;
    delete []collumn;
    res.push_back(r);
 }
}

    for (int i = 0; i < res.size(); i++)
    {
        cout << "Case #" << i+1 << ":" << res[i].a <<" "<< res[i].b <<" "<< res[i].c <<" "<< '\n';
    }
    res.clear();
    return 0;
}