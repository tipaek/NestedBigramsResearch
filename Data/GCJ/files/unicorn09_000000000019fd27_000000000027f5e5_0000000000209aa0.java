#include<bits/stdc++.h>
using namespace std;

int main(){
     int tt;cin>>tt;for(int kr=0;kr<tt;kr++){ 
    	long long int n;cin>>n;
    	long long int  k;cin>>k;
    	
    	if(n==2){
    		if(k==3){
    		cout<<"Case #"<<kr+1<<": ";
    		cout<<"IMPOSSIBLE"<<endl;
    		}
    		if(k==2){
    			cout<<"Case #"<<kr+1<<": ";
    			cout<<"POSSIBLE"<<endl;
    			cout<<1<<" "<<2<<endl<<2<<" "<<1<<endl;
    		}
    		if(k==4){
    			cout<<"Case #"<<kr+1<<": ";
    			cout<<"POSSIBLE"<<endl;
    			cout<<2<<" "<<1<<endl<<1<<" "<<2<<endl;
    		}
    	}
    	if(n==3)
		{
    		if(k==4||k==5||k==7||k==8){
    			cout<<"Case #"<<kr+1<<": ";
    			cout<<"IMPOSSIBLE"<<endl;
    		}
    		if(k==6){
    			cout<<"Case #"<<kr+1<<": ";
    			cout<<"POSSIBLE"<<endl;
    			printf("2 1 3\n3 2 1\n1 3 2\n" );
    		}
    		if(k==3){
    			cout<<"Case #"<<kr+1<<": ";
    			cout<<"POSSIBLE"<<endl;
    			printf("1 2 3\n3 1 2\n2 3 1\n" );
    		}
    		if(k==9){
    			cout<<"Case #"<<kr+1<<": ";
    			cout<<"POSSIBLE"<<endl;
    			printf("3 1 2\n2 3 1\n1 2 3\n" );
    		}
        }
        if(n==4){
        	if(k==15||k==5){cout<<"Case #"<<kr+1<<": ";cout<<"IMPOSSIBLE"<<endl;}
        	if(k==4){
        		cout<<"Case #"<<kr+1<<": ";
        		cout<<"POSSIBLE"<<endl;
    			printf("1 2 3 4\n2 1 4 3\n3 4 1 2\n4 3 2 1\n" );
        	}
        	if(k==6){
        		cout<<"Case #"<<kr+1<<": ";
        		cout<<"POSSIBLE"<<endl;
        		printf("1 2 3 4\n2 1 4 3\n3 4 2 1\n4 3 1 2\n");
        	}
        	if(k==10){
        		cout<<"Case #"<<kr+1<<": ";
        		cout<<"POSSIBLE"<<endl;
        		printf("1 2 3 4\n2 4 1 3\n3 1 4 2\n4 3 2 1\n");
        	}
        	if(k==8){
        		cout<<"Case #"<<kr+1<<": ";
        		cout<<"POSSIBLE"<<endl;
        		printf("1 2 3 4\n2 3 4 1\n3 4 1 2\n4 1 2 3\n");
        	}
        	if(k==9){
        		cout<<"Case #"<<kr+1<<": ";
        		cout<<"POSSIBLE"<<endl;
        		printf("1 2 3 4\n2 4 1 3\n4 3 2 1\n3 1 4 2\n");
        	}
        	if(k==7){
        		cout<<"Case #"<<kr+1<<": ";
        		cout<<"POSSIBLE"<<endl;
        		printf("1 2 3 4\n3 1 4 2\n4 3 2 1\n2 4 1 3\n");
        	}
        	if(k==12){
        		cout<<"Case #"<<kr+1<<": ";
                 cout<<"POSSIBLE"<<endl;
                 printf("1 2 3 4\n3 4 1 2\n2 3 4 1\n4 1 2 3\n");
        	}
        	if(k==11){
        		cout<<"Case #"<<kr+1<<": ";
                 cout<<"POSSIBLE"<<endl;
                 printf("1 2 3 4\n3 4 2 1\n2 1 4 3\n4 3 1 2\n");
        	}
        	if(k==13){
        		 cout<<"Case #"<<kr+1<<": ";
                 cout<<"POSSIBLE"<<endl;
                 printf("2 1 3 4\n3 4 2 1\n1 3 4 2\n4 2 1 3\n");
        	}	
        	if(k==14){
        		cout<<"Case #"<<kr+1<<": ";
                 cout<<"POSSIBLE"<<endl;
                 printf("3 1 2 4\n1 4 3 2\n2 3 4 1\n4 2 1 3\n");
        	}
        	if(k==16){
        		cout<<"Case #"<<kr+1<<": ";
                  cout<<"POSSIBLE"<<endl;
                  printf("4 1 2 3\n1 4 3 2\n2 3 4 1\n3 2 1 4\n");
              }
        }
        if(n==5)
		{
        	cout<<"Case #"<<kr+1<<": ";
        	if(k==6||k==24)cout<<"IMPOSSIBLE"<<endl;
        	else cout<<"POSSIBLE"<<endl;
        	 if(k==14){
                 printf("1 2 3 4 5\n2 1 4 5 3\n3 4 5 1 2\n4 5 2 3 1\n5 3 1 2 4\n");
        	 }
        	 if(k==10){
        	 	printf("1 2 3 4 5\n2 1 4 5 3\n3 4 5 1 2\n5 3 1 2 4\n4 5 2 3 1\n");
        	 }
        	 if(k==5){
        	 	printf("1 2 3 4 5\n2 1 4 5 3\n3 5 1 2 4\n4 3 5 1 2\n5 4 2 3 1\n");
        	 }
        	 if(k==8){
        	 	printf("1 2 3 4 5\n2 1 4 5 3\n3 5 1 2 4\n5 4 2 3 1\n4 3 5 1 2\n");
        	 }
        	 if(k==9){
        	 	printf("1 2 3 4 5\n2 1 4 5 3\n4 5 1 3 2\n3 4 5 2 1\n5 3 2 1 4\n");
        	 }
        	 if(k==15){
        	 	printf("1 2 3 4 5\n2 3 1 5 4\n3 4 5 1 2\n4 5 2 3 1\n5 1 4 2 3\n");
        	 }
        	 if(k==12){
        	 	printf("1 2 3 4 5\n2 3 1 5 4\n3 4 5 1 2\n5 1 4 2 3\n4 5 2 3 1\n");
        	 }
        	 if(k==11){
        	 	printf("1 2 3 4 5\n2 3 1 5 4\n3 5 4 1 2\n4 1 5 2 3\n5 4 2 3 1\n");
        	 }
        	 if(k==13){
        	 	printf("1 2 3 4 5\n2 3 1 5 4\n5 1 4 2 3\n4 5 2 3 1\n3 4 5 1 2\n");
        	 }
        	 if(k==16){
        	 	printf("1 2 3 4 5\n2 3 4 5 1\n4 1 5 2 3\n5 4 1 3 2\n3 5 2 1 4\n");
        	 }
        	 if(k==17){
        	 	printf("1 2 3 4 5\n2 4 1 5 3\n4 3 5 2 1\n5 1 4 3 2\n3 5 2 1 4\n");
        	 }
        	 if(k==18){
        	 	printf("1 2 3 4 5\n2 4 5 1 3\n3 5 4 2 1\n4 3 1 5 2\n5 1 2 3 4\n");
        	 }
        	 if(k==19){
        	 	printf("1 2 3 4 5\n2 5 1 3 4\n3 4 5 1 2\n4 3 2 5 1\n5 1 4 2 3\n");
        	 }
        	 if(k==20){
        	 	printf("1 2 3 4 5\n2 5 4 1 3\n3 4 5 2 1\n4 3 1 5 2\n5 1 2 3 4\n");
        	 }
        	 if(k==7){
        	 	printf("1 2 3 4 5\n3 1 4 5 2\n4 5 2 1 3\n5 3 1 2 4\n2 4 5 3 1\n");
        	 }
        	 if(k==21){
        	 	printf("2 1 3 4 5\n1 5 4 2 3\n3 4 5 1 2\n4 3 2 5 1\n5 2 1 3 4\n");
        	 }
        	 if(k==22){
        	 	printf("3 1 2 4 5\n1 5 4 2 3\n2 4 5 3 1\n4 3 1 5 2\n5 2 3 1 4\n");
        	 }
        	 if(k==23){
        	 	printf("4 1 2 3 5\n1 5 3 4 2\n2 4 5 1 3\n3 2 4 5 1\n5 3 1 2 4\n");
        	 }
        	 if(k==25){
        	 	printf("5 4 3 2 1\n1 5 4 3 2 \n2 1 5 4 3\n3 2 1 5 4\n4 3 2 1 5\n");
        	 }
        }
	}
	return 0;
}