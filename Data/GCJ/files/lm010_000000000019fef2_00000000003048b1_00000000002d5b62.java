#include <bits/stdc++.h>
using namespace std;
typedef long long int ll;
struct hash_pair { 
    template <class T1, class T2> 
    size_t operator()(const pair<T1, T2>& p) const
    { 
        auto hash1 = hash<T1>{}(p.first); 
        auto hash2 = hash<T2>{}(p.second); 
        return hash1 ^ hash2; 
    } 
}; 
int main() {
	// your code goes here
	int t;
	cin>>t;
	for(int i=1;i<=t;i++){
	    int x,y;
	    cin>>x>>y;
	    
	        if(abs(x)%2==0 && abs(y)%2==0 && x!=0 && y!=0){
	            cout<<"Case #"<<i<<": "<<"IMPOSSIBLE"<<endl;
	           // cout<<"IMPOSSIBLE"<<endl;
	        }
	        else if(abs(x)%2!=0 && abs(y)%2!=0 && x!=0 && y!=0){
	            cout<<"Case #"<<i<<": "<<"IMPOSSIBLE"<<endl;
	        }
	        else{
	            unordered_map<pair<int, int>, pair<int, int>, hash_pair> M; 
	            queue<pair<int,int> > q;
	            q.push(make_pair(0,0));
	            q.push(make_pair(INT_MIN,INT_MIN));
	            ll steps=1;
	            ll ans=0;
	            M[make_pair(0,0)]=make_pair(INT_MIN,INT_MIN);
	            while(!q.empty()){
	                pair<int,int> r=q.front();
	                q.pop();
	               if (ans >= 4)break;
	                if(r.first==x && r.second==y){
	                    break;
	                }
	                if(r.first!=INT_MIN){
	                    int x1=r.first;
	                    int y1=r.second;
	                    q.push(make_pair(x1+steps,y1));
	                    q.push(make_pair(x1-steps,y1));
	                    q.push(make_pair(x1,y1+steps));
	                    q.push(make_pair(x1,y1-steps));
	                    if(M.find(make_pair(x1+steps,y1))==M.end())
	                    M[make_pair(x1+steps,y1)]=r;
	                    if(M.find(make_pair(x1-steps,y1))==M.end())
	                    M[make_pair(x1-steps,y1)]=r;
	                    if(M.find(make_pair(x1,y1+steps))==M.end())
	                    M[make_pair(x1,y1+steps)]=r;
	                    if(M.find(make_pair(x1,y1-steps))==M.end())
	                    M[make_pair(x1,y1-steps)]=r;
	                    
	                }
	                else{
	                    ans++;
	                    steps=steps*2;
	                    if(!q.empty()){
	                        q.push(make_pair(INT_MIN,INT_MIN));
	                    }
	                    
	                }
	                
	            }
	            
	            string res="";
	            pair<int,int> start=make_pair(x,y);
	            while(start.first!=INT_MIN && start.second!=INT_MIN){
	                pair<int,int> prev=M[start];
	               // cout<<prev.first<<" "<<prev.second<<endl;
	                if(start.first-prev.first>0){
	                    res='E'+res;
	                }
	                else if(start.first-prev.first<0){
	                    res='W'+res;
	                }
	                else if(start.second-prev.second<0){
	                    res='S'+res;
	                }
	                else if(start.second-prev.second>0){
	                    res='N'+res;
	                }
	              //  cout<<res<<endl;
	                
	                start=prev;
	               /* if(start.first==0 && start.second==0)
	                break;*/
	                
	            }
	            res=res.substr(1);
	           // cout<<res<<endl;
	           cout<<"Case #"<<i<<": "<<res<<endl;
	            
	          // cout<<ans<<endl;
	        }
	    
	}
    return 0;
}
