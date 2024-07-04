import java.util.*;

class Test
{
    public static void main(String [] s)
    {
        int a[100][100];
        int n,i,j,trace;
        Scanner sc=new Scanner(System.in);
        int t = sc.nextInt();
        for(int temp=0;temp<t;temp++)
        {
            int n = sc.nextInt();
            for(i=0;i<n;i++)
            {
                for(j=0;j<n;j++)
                {
                    int a[i][j] = sc.nextInt();
                    if(i==j)
                    {trace+=a[i][j];}
                }
            }
            System.out.println("Case #"+t+": "+trace);
        }
        
        
    }
    
}