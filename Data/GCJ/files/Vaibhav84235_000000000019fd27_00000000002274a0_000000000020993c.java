import java.io.*;
class trace
{
    public static void main(String[] args) throws IOException
    {int i,j;
        int sum=0;
        int k,r=0,c=0;
        int f=0;
        int g=0;
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in))
        int t=Integer.parseInt(br.readLine());
        for(k=1;k<=t;k--)
        {
            int n=Integer.parseInt(br.readLine());
            int[][] a=new int[n][n];
            for(i=0;i<n;i++)
            {String str=br.readLine();
                for(j=0;j<n;j++)
                {
                    a[i][j]=Integer.parseInt(str.trim().split(" "));
                }
                str="";
            }
            for(i=0;i<n;i++)
            {
                sum=sum+a[i][i];
            }
            Set<Integer> s= new HashSet<>();
            for(i=0;i<n;i++)
            {
                for(j=0;j<n;j++)
                {
                    if(s.contains(a[i][j]))
                    {
                        r=r+1;
                    }
                    else
                    {
                        s.add(a[i][j]);
                    }
                }
                if(r>f)
                {
                f=r+1;
                }
                r=0;
                s.clear();
            }
            Set<Integer> d=new HashSet<>();
            for(i=0;i<n;i++)
            {
                for(j=0;j<n;j++)
                {
                    if(d.contains(a[j][i]))
                    {
                        c=c+1;
                    }
                    else
                    {
                        d.add(a[j][i]);
                    }
                }
                if(c>g)
                {
                    g=c+1;
                }
                c=0;
                d.clear();
            }
            System.out.println("Case #"+t+": "+sum+" "+f+" "+g);
            sum=0;
        }
    }
}