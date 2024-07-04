import java.util.*;
class main{
    public static void main(String args[]){
        Scanner s=new Scanner(System.in);
        int t=s.nextInt();
        for(int x=1;x<=t;x++){
            int n=s.nextInt();
            String a[]=new String[n];
            int max=0,j;
            for(int i=0;i<n;i++){
                a[i]=s.next();
                if(a[i].length())>max){
                    max=a[i].length();
                    j=i;
                }
            }
                int c=0;
                for(int i=0;i<n;i++){
                    if(isSubstring(a[i],a[j])){
                        c++;
                        
                    }
                }
                
                if(c==n){
                    System.out.println("Case "+x+": "+a[j].substring(1,max));
                }
                
                
            }
            
            
        }
        
static int isSubstring(string s1, string s2) 
{ 
    int M = s1.length(); 
    int N = s2.length(); 
  
   
    for (int i = 0; i <= N - M; i++) { 
        int j; 
  
       
        for (j = 0; j < M; j++) 
            if (s2[i + j] != s1[j]) 
                break; 
  
        if (j == M) 
            return 1; 
    } 
  
    return -1; 
} 
        
    }
}