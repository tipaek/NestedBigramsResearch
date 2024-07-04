import java.util.*;
public class Matrix
{
    public static void main(String[] args)
    {
      Scanner k=new Scanner(System.in);
      int t=k.nextInt();
      int n,trace=0,count=0,r=0,c=0;
      int a[]=new int[t];
      for(int i=0;i<t;i++)
      {
          n=k.nextInt();
         int ar[][]=new int[n][n];
         for(int j=0;j<n;j++)
         {
             for(int l=0;l<n;l++)
             {
               ar[j][l]=k.nextInt();  
               if(j==l)
             {
              trace=trace+ar[j][l];  
             }
             }
             
             }
              a[i]=trace;
             trace=0;
          }
          for(int i=0;i<t;i++)
          {
            for(int j=1;j<n;j++)
          {
           for(int l=1;l<n;l++)
             {
                 if(ar[j][l]==ar[j][l+1])
                 {
                     count++;
                     if(count==1)
                     {
                         r=r+1;
                     }
                 }
    }
    }
  
                 
}