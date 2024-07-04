import java.util.*;
class TestClass {
    public static void main(String args[] ) throws Exception {
         Scanner s = new Scanner(System.in);
         int t = s.nextInt();
         for(int i=0; i<t; i++){
             int n = s.nextInt();
             int[][] arr = new int[n][n];
             for(int j=0; j<n; j++){
                 for(int k=0; k<n; k++){
                     arr[j][k] = s.nextInt();
                 }
             }
             int trc=0;
             for(int j=0; j<n; j++){
                 trc = trc+arr[j][j];
             }
             //rows 
             int j =0;
             int r=0;
             while(j<n){
                 int k=0;
                 for(int l=0; i<n; l++){
                     if(k=n){
                         j++;
                         break;
                     }
                     if(arr[j][k] == arr[j][l] ){
                         r++;
                         j++;
                         break;
                     }
                     else{
                        k++; 
                     }
                 }
                 
             }
             // cols 
              int j =0;
             int c=0;
             while(j<n){
                 int k=0;
                 for(int l=0; i<n; l++){
                     if(k=n){
                         j++;
                         break;
                     }
                     if(arr[k][j] == arr[l][j] ){
                         c++;
                         j++;
                         break;
                     }
                     else{
                        k++; 
                     }
                 }
                 
             
             
         }
         //cols 
          System.out.print("Case #");
          System.out.println(+t+": "+trc+" "+r+" "+c+);
         }
    }
    
    }
    
        
    