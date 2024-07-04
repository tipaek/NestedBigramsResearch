#include <iostream>
#include <vector>
using namespace std;

bool same(vector<int> &arr, int d){
    int count=1;
    int no=arr[0];
    for(int i=1; i<arr.size(); i++){
        if(no==arr[i]) count++;
        else {
            if(count>=d) return true;
            count=1;
        }
    }
    
    return false;
}


int main() {
// 	cout<<"GfG!";

    int t;
    cin>>t;
    for(int u=1; u<=t; u++){
        
        int n, d;
        cin>>n>>d;
        vector<int> arr(n);
        for(int i=0;i<n; i++) cin>>arr[i];
        
        int sum =0;
        for(int i=0; i<n; i++) sum+= arr[i];
        
        sort(arr.begin(), arr.end());
        
        if(same) {
            cout<<"Case #"<<u<<": "<<"0\n";
            continue;
        }
        
        int count=0;
        int start= 0;
        bool check= false;
        while(check==false){
            float temp =(float)(sum/d);
            int big =0;
            
            for(int i=start; i<arr.size(); i++){
                if(arr[i]>=temp){
                    count+= arr[i]/temp -1;
                    big++;
                }
            }
            if(big>1) break;
            sum-=arr[i];
            start++;
        }
        
        cout<<"Case #"<<u<<": "<<count<<"\n";
        
    }

	return 0;
}