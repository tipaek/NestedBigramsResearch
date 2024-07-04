#include <iostream>
#include <string>
#include <vector>
#include <windows.h>
using namespace std;


struct timesection
{
    int st;
    int et;
};

bool isoverlap(timesection t1, timesection t2)
{
    if (t1.st<=t2.st && t1.et>t2.st) return true;
    if (t1.st<t2.et && t1.et>=t2.et) return true;
    if (t1.st>=t2.st && t1.et<=t2.et) return true;
    return false;
}

int main() {
    int i,j,k,l,n,m;
    bool ret;
    bool *col;
    int **matrix;
    vector<string> res;
    vector<timesection> Csec, Jsec;
    string r;
    timesection *sec;
    cin>>n;
    for (i=0;i<n;i++)
    {
        cin>>m;
        r = "";
        sec = new timesection[m];
        for (j=0;j<m;j++)
        {
            cin>>sec[j].st>>sec[j].et;
            if (j==0) 
            {
                Csec.push_back(sec[j]);
                r+="C";
            }
            else
            {
                ret = true;
                for (k=0;k<Csec.size();k++)
                {
                    if (isoverlap(sec[j], Csec[k]))
                    {
                        ret = false;
                        break;
                    }
                }

                if(ret) 
                {
                    Csec.push_back(sec[j]);
                    r+="C";
                    continue;
                }

                ret = true;
                for (k=0;k<Jsec.size();k++)
                {
                    if (isoverlap(sec[j], Jsec[k]))
                    {
                        ret = false;
                        break;
                    }
                }

                if(ret) 
                {
                    Jsec.push_back(sec[j]);
                    r+="J";
                }
                else
                {
                    r = "IMPOSSIBLE";
                    break;
                }
            }
        }
        res.push_back(r);
        delete []sec;
        Csec.clear();
        Jsec.clear();
    }

    for (i=0;i<res.size();i++)
    {
        cout<<"Case #" << i+1 <<":"<<res[i]<<endl;
    }
    res.clear();
    system("pause");
    return 0;
}