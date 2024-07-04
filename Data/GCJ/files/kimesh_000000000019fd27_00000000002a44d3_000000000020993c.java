#import java.util*;
class latin
{
    public static void main(String [] args)
    {
        Scanner sc= new Scanner(System.in);
        int t= sc.nextInt();
         int n,i,j,k,sum=0;
         int row=0, col=0;
         HashSet <Integer> hs= new HashSet();
         for (k=1;k<=t;k++)
         {
            n=sc.nextInt();
            for (i=0;i<n;i++)
            {
                for(j=0;j<n;j++)
                {
                    if(j==i)
                    sum= a[i]+a[j];
                    
                    if(!hs.contains(a[i][j]))
                        hs.add(a[i][j]);
                        
                        else
                        row=row+1;
                    
                }
                hs.removeAll();
            }
            for (j=0;j<n;j++)
            {
                for (i=0;i<n;i++)
                {
                    if (!hs.contains(n[i][j])
                    hs.add(n[i][j]);
                    
                    else 
                    col=col+1;
                }
                hs.removeall();
            }
            
            System.out.println("Case #t:"+ sum +row+col);
         }
    }
}