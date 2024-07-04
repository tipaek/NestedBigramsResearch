import java.util.*;
public class fore
{
public static int rchec(int a[][])
{
    int count=0;
    for(int i=0;i<a.length;i++)
    {
        int rep=a[i][[0];
        int ans=1;
        for(int j=1;j<a.length;j++)
        {
            rep=rep^a[i][j];
            ans=ans*rep;
        }
        if(ans==0)
        count=count+1;
    }
    return count;
}
public static lchec(int a[][])
{
    int count=0;
    for(int j=0;j<a.length;j++)
    {
        int rep=a[j][0];
        int ans=1;
        for(int i=1;i<a.length;i++)
        {
            rep=rep^a[i][j];
            ans=ans*rep;
        }
        if(ans==0)
        count=count+1;
    }
    return count;
}
public static int trace(int a[][])
{
    int sum=0;
    for(int i=0;i<a.length;i++)
    {
        sum=sum+a[i][j];
    }
    return sum;
}
public static void main(String args[])
{
 Scanner sc=new Scanner(System.in);
 int t=sc.nextInt();
 for(int i=0;i<t;i++)
 {
    int n=sc.nextInt();
    int a[][]=new int[n][n];
    int tr=trace(a);
    int le=lchec(a);
    int re=rchec(a);
    System.out.println("Case #"+i+": "+tr+" "+le+" "+re);
 }
}
}