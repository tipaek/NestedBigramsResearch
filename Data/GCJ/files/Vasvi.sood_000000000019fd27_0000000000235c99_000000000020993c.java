import java.util.Scanner;
class Solution{
     public static void main(String args[])
     {
         Scanner in=new Scanner(System.in);
         int t=in.nextInt;
         for(int k=1;k<=t;k++)
         {
           int n=in.nextInt(); 
           int diag=0,count=0,r=0,c=0;
           int arr[]=new int[n][n];
           for(int i=0,i<n;i++)
           for(int j=0,j<n;j++)
          arr[i][j]=in.nextInt;
           for(int l=0,l<n;l++)
           diag=diag+arr[l][l];
            for(i=0,i<n;i++){
           for(j=0,j<n;j++){
           for( l=1,l<=n;l++){
           if(arr[i][j]==l){
           count++;
           l=n+1;
           }
           }
           
           }
           if(count!=n)
           r++;
            }
            count=0;
            
             for(j=0,j<n;j++){
           for(i=0,i<n;i++){
           for( l=1,l<=n;l++){
           if(arr[i][j]==l){
           count++;
           l=n+1;
           }
           }
           
           }
           if(count!=n)
           c++;
            }
            System.out.println("Case #"+k+": "+diag+" "+
            r+" "+c);
          
         }
     }
}