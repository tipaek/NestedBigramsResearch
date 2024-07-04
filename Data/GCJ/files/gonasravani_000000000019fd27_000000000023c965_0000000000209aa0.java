import java.util.*;
class Solution
{
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(int c=1;c<=t;c++)
        {
            int n=sc.nextInt(),k=sc.nextInt();
            int[][] a=new int[n][n];
            ArrayList<Integer> list=new ArrayList<>();
            for(int i=0;i<n;i++)
            {
                int start=i+1;
                list.add(i);
                for(int j=0;j<n;j++)
                {
                    a[i][j]=start;
                    start++;
                    if(start==n) start=1;
                }
            }
            System.out.print("Case #1"+": ");
            if(!possible(a,0,list,k))
                System.out.print("IM");
            System.out.println("POSSIBLE");
        }
    }
    public static boolean possible(int[][] a,int index,ArrayList<Integer> list,int k)
    {
        if(index==a.length && k==0)
            return true;
        if(k<0)
            return false;
        for(int i=0;i<A.size();i++)
        {
            k=k-a[index][list.get(i)];
            if(possible(a,index+1,list.remove(i),k)
                return true;
            k=k+a[index][list.get(i)];
        }
        return false;
    }
}