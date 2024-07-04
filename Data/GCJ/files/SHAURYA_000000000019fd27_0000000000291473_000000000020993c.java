import java.io.*;
class Vestigium
{
    public static int duplicate_row(int d[][], int l)
    {
        int d=0;
        for(int i=0;i<l;i++)
        {
            int k=0,count=0;
            for(int j=1;j<l;j++)
            {
                if(d[i][k]==d[i][j])
                ++count;
            }
            k++;
            if(count>0)
            {
                d++;
            }
        }
        return d;
    }
    public static int duplicate_column(int e[][], int z)
    { 
        int d=0;
        for(int i=0;i<l;i++)
        {
            int k=0,count=0;
            for(int j=1;j<l;j++)
            {
                if(d[k][i]==d[j][i])
                ++count;
            }k++;
            if(count>0)
            {
                d++;
            }
        }
        return d;
    }
    
    public static int trace(int c[][],int i,int j)
    {
        int sum=sum+trace(c[][],i+1,j+1);
        return sum;
    }
    public static output(int c,int b[][])
    {
        int t = trace(b,0,0);
        int dr= duplicate_row(b,size);
        int dc= duplicate_column(b,size);
        System.out.println("Case #"+c+":"+" "+t+" "+dr+" "+dc);
    }
    public static void main(String[] args)throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in())
        int t=Integer.parseInt(br.readLine());
        for(int i=1;i<=t;i++)
        {
            int size=Integer.parseInt(br.readLine());
            int a[][]=new int[size][size];
            for(int j=0;j<size;j++)
            {
                String s=br.readLine();
                for(int k=0;k<size;k++)
                {
                    a[k][j]=s.split(" ");
                }
                output(i,a);
            }
        }
    }
}
    