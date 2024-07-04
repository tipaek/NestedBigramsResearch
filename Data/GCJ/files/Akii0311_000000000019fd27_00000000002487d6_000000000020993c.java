import java.util.*;
Class Solution{
     public static int countrows(int ar[][], int n)
     {
         int sum=0,k=0,c=0,count=0;
          for(int i=0;i<n;i++)
       {
           count=0;
           for(int j=0;j<n;j++)
           {
               
               for(k=j+1;k<n;k++)
               {
                   if(ar[i][j]==ar[i][k])
                   {
                       //System.out.println(ar[i][j]);
                        //System.out.println(ar[i][k]);
                       count++;
                       break;
                   }
               }
               //System.out.println("Count:"+count);
               
           }
           if(count>0)
           {
               a++;
           }
           return a;
     }
   public static int countcol(int a[][], int n)
    {
        boolean flag;
        int count=0;
        for(int i=0; i<n; i++)
        {
            flag=false;
            for(int j=0; j<n-1; j++)
            {
                int first = a[j][i];
                for(int k=j+1; k<n; k++)
                {
                    if(first==a[k][i])
                    {
                        count++;
                        flag=true;
                        break;
                    }
                }//closing k loop
               
                if(flag==true)
                {
                    break;
                }
            }//closing j loop

           
        }
         return count;
       
    }// countcol closing
   public static void main(String[]args)
   {
       Scanner sc=new Scanner(System.in);
       int t=sc.nextInt();
       int c=1;
       while(t-->0)
       {
           int n=sc.nextInt();
            int ar[][]=new int[n][n];
            int trace=0;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<nj++)
                {
                   ar[i][j]=sc.nextInt();
                   if(i==j)
                   {
                       trace+=ar[i][j];
                   }
                }
            }
               System.out.println("Case #" +c +": " +trace +" "+countrows(ar,n) +" "+countcol(ar,n));
            c++;
       }//while loop closing
   }



}