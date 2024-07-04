#include<bits/stdc++.h>

using namespace std;

int main(){
    int T;
    cin>>T;
    fflush(stdout);
    string str[T];
    for(int t=0;t<T;t++){
        cin>>str[t];
    }
    for(int t=0;t<T;t++){
        string S=str[t];
        string p="";
        int num=0, braces=0;
        for(int i=0;i<S.length();i++){
            num = (int)S[i] - (int)'0';
            int temp = num - braces;
            while(temp!=0){
                if(temp>0){
                    p=p+"(";
                    temp--;}
                else if(temp<0){
                    p=p+")";
                    temp++;}

                }
                p=p+S[i];
                braces = num;
        }
            while(braces>0){
                p=p+")";
                braces--;
            }

            cout<<"Case #"<<(t+1)<<": "<<p<<endl;
        }

    return 0;
}

