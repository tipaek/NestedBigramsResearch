import java.io.*;
class GFG
{
    public static void printPascal(int)
    {
        for(int l=1;l<=n;l++)
        {
            int c=1;
            c(l,1)
            for(int i=1;i<=l;i++)
            {
                System.out.println(c+" ");
                c=c(l-i)/i;
            }
            System.out.println();
        }
    }
    public static void main(String args[])
    {
        int n=5;
        printPascal(n);
    }
}