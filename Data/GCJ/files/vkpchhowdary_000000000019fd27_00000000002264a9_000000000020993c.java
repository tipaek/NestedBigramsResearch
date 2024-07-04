import java.util.*;
class trace
{
    public static void main(String args[])
    {
        int a[][],k,n,i,j,r,c,t,sum=0;
        Scanner x=new Scanner(System.in);
        t=x.nextInt();
        n=x.nextInt();
        a=new int[n][n];
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                a[i][j]=x.nextInt();
            }
        }
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                int b=a[i][j];
                for(k=0;k<n;k++)
                {
                    if(b==a[i][k])
                    {
                        r+=1;
                    }
                }
            }
        }
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                b=a[i][j];
                for(k=0;k<n;k++)
                {
                    if(b==a[k][j])
                    {
                        c+=1;
                    }
                }
            }
        }
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                if(i==j)
                {
                    sum+=a[i][j];
                }
            }
        }
        System.out.print("Case #"+t+": "+ sum);
        System.out.print(" ");
        System.out.print(r);
        System.out.print(" ");
        System.out.print(c);