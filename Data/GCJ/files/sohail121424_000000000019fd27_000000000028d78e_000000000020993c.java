#include<bits/stdc++.h>
using namespace std;

int a[1001][1001];

int main()
{
    int t;
    cin>>t;
    int t1=t;
    int q = 1;
    while(t--){
        int n;
        cin>>n;
		int trace = 0;
        for(int i=0;i<n;++i){
        	for(int j=0;j<n;++j){
        		cin>>a[i][j];
        		if(i==j) trace +=a[i][j];	
			}
		}
		int r,c;
		r=c=0;
		for(int i=0;i<n;++i){
			set<int> s;
			int flag = 0;
			for(int j=0;j<n;++j){
				if(s.find(a[i][j])!=s.end()){
					flag = 1;
					break;
				}
				s.insert(a[i][j]);
			}
			r += flag;
		}
			for(int j=0;j<n;++j){
				set<int> s;
				int flag = 0;
				for(int i=0;i<n;++i){
					if(s.find(a[i][j])!=s.end()){
						flag = 1;
						break;
					}
					s.insert(a[i][j]);
				}
				c += flag;
			}
		cout<<"Case #"<<q<<": "<<trace<<' '<<r<<" "<<c<<endl;
    ++q;
        
    }
    return 0;
}