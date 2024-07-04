#include <iostream>
using namespace std;

int main() {
	// your code goes here
	int tes,size,x=1;
	
	cin>>tes;
	while(tes--)
	{ int diasum=0,r=0,c=0;
	    cin>>size;
	    int arr[size][size];
	    for(int i=0;i<size;i++)
	        for(int j=0;j<size;j++)
	            cin>>arr[i][j];
	   
	    for(int i=0;i<size;i++){
	        char fla='f';
	        diagadd+=arr[i][i];
	        
	        for(int j=0;j<size-1;j++)
	        {
	            for(int k=j+1;k<size;k++)
	            {
	                if(arr[i][j]==arr[i][k]){
	                    r++;
	                    fla='t';
	                    break;
	                }
	            }
	            if(fla=='t')
	                break;

	           }
	        
	    } 
	    for(int i=0;i<size;i++){
	        char fla='f';
	        for(int j=0;j<size-1;j++)
	        {
	           for(int k=j+1;k<size;k++)
	            {
	                if(arr[j][i]==arr[k][i]){
	                    c++;
	                    fla='t';
	                    break;
	                }
	            }
	            if(fla=='t')
	                break;

	           }
	        
	    } 
	   cout<<"Case #"<<x++<<":"<<" "<<diasum<<" "<<r<<" "<<c<<endl;    
	    }
	return 0;
}