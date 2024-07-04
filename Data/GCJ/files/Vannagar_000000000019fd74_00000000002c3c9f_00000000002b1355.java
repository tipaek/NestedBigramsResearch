import java.util.*;
public class Solution
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(int y=1;y<=t;y++)
        {
            int o=sc.nextInt();int k=sc.nextInt();
            int[][]a=new int[o][k];
            int s=0; int m=r*c+1;
            for(int x=0;x<o;x++)
            {
                for(int z=0;z<k;z++)
                {
                    a[x][z]=sc.nextInt();
                }
            }
            while(recurse(a)<m)
            {
                m=recurse(a);int wow=0;
                for(int r=0;r<a.length;r++)
                {
                    double sum=0;double p=0;
                    for(int c=0;c<a[0].length;c++)
                    {
                        if(a[r][c]!=-1)
                        {sum+=a[r][c];p++;}
                    }
                    if(p==0)
                    {continue;}
                    double w=sum/p;
                    for(int c=0;c<a[0].length;c++)
                    {
                        if(a[r][c]<w)
                        {a[r][c]=-1;}
                    }
                    wow+=sum;
                }
                for(int r=0;r<a[0].length;r++)
                {
                    double sum=0;double p=0;
                    for(int c=0;c<a.length;c++)
                    {
                        if(a[c][r]!=-1)
                        {sum+=a[c][r];p++;}
                    }
                    if(p==0)
                    {continue;}
                    double w=sum/p;
                    for(int c=0;c<a[0].length;c++)
                    {
                        if(a[c][r]<w)
                        {a[c][r]=-1;}
                    }
                }
                s+=wow;
            }
            System.out.println("Case #"+y+":"wow);
        }
    }
    public static int recurse(int [][]a)
    {
        int sum=0;
        for(int r=0;r<a.length;r++)
        {
            for(int c=0;c<a[0].length;c++)
            {
                sum++;
            }
        }
        return sum;
    }
}