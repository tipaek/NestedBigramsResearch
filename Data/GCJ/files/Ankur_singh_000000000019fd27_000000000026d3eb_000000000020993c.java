import java.io.*;
import java.util.*;
class Traces
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(int i=1;i<=t;i++)
        {
            int n=sc.nextInt();
            int arr[][]=new int[n][n];
            int arr2[]=new int[n][n];
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    arr[i][j]=sc.nextInt();
                }
            }
            Arrays.sort(arr);
            int sum=0,count1=0,count2=0;
             for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    arr2[i][j]=arr[j][i];
                    sum=sum+arr[i][i];
                    count1++;
                    if(arr[i][j]==arr[i][j+1])
                    {
                        break;
                    }
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    count2++;
                   if(arr2[i][j]==arr2[i][j+1])
                    {
                        break;
                    }
                }
                
            }
            System.out.print(sum+" "+count1+" "+count2);
        }
    }
}