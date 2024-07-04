import java.util.*;
 class Main
{
    public static void main(String agrs[])
    {
        Scanner in=new Scanner(System.in);
        int i,j,k,l,m;
        int T=in.nextInt();
        int rc=0;
        int cc=0;
        for ( k=1;k<=T;k++)
        {
            int trace=0;
            int n=in.nextInt();
            int a[][]=new int[n][n];
            int b[][]=new int[n][n];
            for(i=0;i<n;i++)
            {
                for(j=0;j<n;j++)
                {
                    a[i][j]=in.nextInt();
                }
            }
            
            for ( i=0;i<n;i++)
        {
            for (j=0;j<n;j++)
            {
                for (l=j+1;l<n;l++)
                {
                    if(a[i][j]==a[i][l])
                    {
                    rc++;
                    break;
                    }
                }
                break;
            }
          
            for(int i=0;i<n;i++){    
    for(int j=0;j<n;j++)
    {    
b[i][j]=a[j][i];  
}    
}    

   
            for ( i=0;i<n;i++)
        {
            for (j=0;j<n;j++)
            {
                for (l=j+1;l<n;l++)
                {
                    if(b[i][j]==b[i][l])
                    {
                    cc++;
                    break;
                    }
                }
                break;
            }
       
        for (i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                if(i==j)
                
                trace+=a[i][j];
            }
        }
      System.out.println("Case #"+k+": "+trace+" "+rc+" "+cc);  
    }
}
}