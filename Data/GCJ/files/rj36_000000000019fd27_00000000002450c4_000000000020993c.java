import java.util.*;
import java.lang.*;
public class Solution
{
public static void main(String args[])
{
    Scanner scan = new Scanner(System.in);
    int t= scan.nextInt();
    for(int i=0;i<t;i++)
    {
        int size = scan.nextInt();
        int trace=0;
        int rcount=0,colcount=0;
        int[][] mat=new int[size][size];
        int flag1=0;
        int flag2=0;
        int [] b= new int[100];
        for(int c=0;c<100;c++){
            b[c]=0;
        }
        //System.out.println("size "+size);
        for(int j=0;j<size;j++)
        {   flag1=0;
            for(int k=0;k<size;k++)
            {  if(j<4 && k<4)
               {
                mat[j][k]=scan.nextInt();
                if(j==k)
                {
                    trace=trace+mat[j][k];
                }
               // System.out.print(" "+mat[j][k]);}
               }
           // System.out.println();
            
    
        //System.out.println("trace "+trace);
        if(flag1==0)
        {
            for(int m=0;m<size-1;m++)
            {
                if(mat[j][k]==mat[j][m])
                {
                    rcount++;
                    temp1=1;
                    break;
                }
            }
        }
       if (b[j]==0){
           for(int n=0;n<j;n++)
           {
               if(mat[n][k]==mat[j][k])
               {
                   b[j]=1;
                   colcount++;
               }
           }
       }
     System.out.println("Case #"+i+1+":"+" "+trace+" "+rcount+" "+colcount);
            }
    }
}
}
