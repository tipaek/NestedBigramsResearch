import java.util.*;

public class Solution{
    static boolean worksWith(ArrayList<Integer> P, int[][]arr,
    int start, int end){
        for(int i=0;i<P.size();i++)
        {
            int index = P.get(i);
            int stC= arr[index][0];
            int endC = arr[index][1];
            if((start> stC && start< endC) || (end>stC  && end< endC))
            {
                return false;
            }
        }
        return true;
    }
    static String calculateResult(int n, int[][]arr)
    {
        ArrayList<Integer> J= new ArrayList<Integer>();
        ArrayList<Integer> C= new ArrayList<Integer>();
        String res="";
        for(int i=0;i<n;i++)
        {
            if(worksWith(C, arr, arr[i][0], arr[i][1])){
                C.add(i);
                res+="C";
            }
            else if(worksWith(J, arr, arr[i][0], arr[i][1])){
                J.add(i);
                res+="J";
            }
            else
            {
                return "IMPOSSIBLE";
            }
        }
        return res;
    }
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        for(int i=1;i<=n;i++)
        {
            int q=sc.nextInt();
            int[][]arr=new int[q][2];
            for(int j=0;j<q;j++)
            {
                arr[j][0]=sc.nextInt();
                arr[j][1]=sc.nextInt();
            }
            System.out.println("Case #"+i+": "+
            calculateResult(q, arr));
        }
    }
}