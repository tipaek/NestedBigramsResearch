import java.util.*;
public class Main
{
    public static void main(String args[])
    {
        int i,j
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(i=0;i<t;i++)
        {
            int n=sc.nextInt();
            int[][] arr=new int[n][n];
            for(j=0;j<n;j++)
            {
                for(k=0;k<n;k++)
                {
                    arr[j][k]=sc.nextInt();
                }
            }
            for(j=0;j<n;j++)
            {
                for(k=0;k<n;k++)
                {
                    if(j==k)
                    {
                        sum=sum+arr[j][k];
                    }
                }
            }
        }
    }
}