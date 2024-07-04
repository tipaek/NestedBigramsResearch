import java.io.*;
class pedigree
{
    stati void printlatin(int n)
    {
        int k=n+1;
    
        for(int i=1;i<=n;i++)
        {
            int temp=k;
            while(temp<=n)
            {
                System.out.print(temp + " ");
                temp++
            }
            for(int j=1;j<=k;j++)
            {
                System.out.print(j + " ");
            }
            k--;
            System.out.println();
        }
    }

    public static void main(String args[])
    {
        int n=0;
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in());
        n=Integer.parseInt(br.readLine());
        printlatin(n);
    }
}