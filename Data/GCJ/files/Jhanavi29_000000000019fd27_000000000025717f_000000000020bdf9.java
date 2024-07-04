import java.util.*;
class Solution
{
public static void main(String[] args)
{
 Scanner sc=new Scanner(System.in);
 int t=sc.nextInt();
 for(int l=1;l<=t;l++)
 {
     int n=sc.nextInt();
     Interval a[]=new Interval[n];
     int min=Integer.MAX_VALUE;
     int max=Integer.MIN_VALUE;
     for(int i=0;i<n;i++)
     {a[i]=new Interval(sc.nextInt(),sc.nextInt());
     if(a[i].s<min)
     min=a[i].s;
     if(a[i].e>max)
     max=a[i].e;
     }
     int ind[]=new int[max+2];
     boolean flag=false;
     String ans;
         Arrays.sort(a,new Comparator<Interval>(){
        public int compare(Interval a,Interval b)
        {
            return a.s-b.s;
        }
    });
    for(int i=0;i<n;i++)
    {
        int s=a[i].s;
        int e=a[i].e;
        for(int j=s;j<=e;j++)
        {
            ind[j]++;
            if(ind[j]>2)
            {flag=true;break;}
        }
        if(flag)break;
        int k[]=overlap(i);
        
    }
     print(l,ans,flag);
 }
}
static int[] overlap(int i)
{
    //0 indicates no overlap
    //1 indicates partial overlap
    //2 indicates full overlap
    //3 indicates continuity
    for(int j=i+1;j<n;j++)
    {
        if(a[j].s<=a[i].e&&a[j].e<=a[i].e)
        return {2,j};
        if(a[j].s==a[i].e)
        return {3,j};
        if(a[j].s<=a[i].e&&a[j].e>a[i].e)
        return {1,j};
        if(a[j].s>a[i].s)
        return {0,j};
    }
}
static void print(int i,String s,boolean flag)
{
    if(flag)
    System.out.println("IMPOSSIBLE");
    else
    System.out.println("Case #"+i+": "+s);
}
}
class Interval
{
    int s,e;
    String who="";
    Interval(int s,int e)
    {
        this.s=s;
        this.e=e;
    }
}