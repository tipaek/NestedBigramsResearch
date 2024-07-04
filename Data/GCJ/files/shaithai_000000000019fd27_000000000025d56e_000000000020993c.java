import java.util.*;
class Main
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        int k=1;
        while(k<=t))
        {
            int n=sc.nextInt();
            int[][] arr=new int[n][n];
            int sum=0;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    arr[i][j]=sc.nextInt();
                    if(i==j)
                    sum=sum+arr[i][j];
                }
            }
            int count=0;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                   for(int k=0;k<j;k++)
                   {
                       if(arr[i][j]==arr[i][k])
                       count++;
                   }
                }
            }
            int count2=0;
            for(int j=0;j<n;j++)
            {
                for(int i=0;i<n;i++)
                {
                    for(int k=0;k<i;k++)
                    {
                        if(arr[i][j]==arr[k][j])
                        count2++;
                    }
                }
            }
            System.out.println("Case #"+k+": "+sum+" "+count1+" "+count2);
            k++;
        }
    }
}