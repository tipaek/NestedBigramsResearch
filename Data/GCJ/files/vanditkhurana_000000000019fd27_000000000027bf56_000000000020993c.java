import java.util.Scanner;
public class Main
{
    public class void main(String args[])
    {
        int i,j,trace=0,row=0,col=0,k,l,m,d,count=1;
        Scanner obj = new Scanner(System.in);
        int n = obj.nextInt();
        int d
        for(i=1;i<=n;i++)
        {
            trace=0;
            row=0;
            col=0;
            d = obj.nextInt();
            int a[][] = new int[d][d];
            for(i=1;i<=d;i++)
            {
                for(j=1;j<=d;j++)
                {
                    a[i][j] = obj.nextInt();
                }
            }
            for(i=1;i<=d;i++)
            {
                for(j=1;j<=d;j++)
                {
                    if(i==j)
                    {
                        trace=trace+a[i][j];
                    }
                }
            }
            for(i=1;i<=d;i++)
            {
                row=0;
                m = d;
                for(j=1;j<=d;j++)
                {
                    count=1;
                    for(k=j+1;k<m;k++)
                    {   
                        if(a[j][k]==a[i][j])
                        {
                            count++;
                            for(l=k;l<m;l++)
                            {
                                a[l]=a[l+1];
                            }
                            k--;
                            m--;
                        }
                    }
                    if(count>1)
                    {
                        break;
                        row++;
                    }
                    else
                    {
                        continue;
                    }
                }
            }
            for(j=1;j<=d;j++)
            {
                col=0;
                m = d;
                for(i=1;i<=d;i++)
                {
                    count=1;
                    for(k=i+1;k<m;k++)
                    {   
                        if(a[i][k]==a[j][i])
                        {
                            count++;
                            for(l=k;l<m;l++)
                            {
                                a[l]=a[l+1];
                            }
                            k--;
                            m--;
                        }
                    }
                    if(count>1)
                    {
                        break;
                        col++;
                    }
                    else
                    {
                        continue;
                    }
                }
            }
            System.out.println("Case #"+i+": "+trace+" "+row+" "+" "col);
        }
    }
}