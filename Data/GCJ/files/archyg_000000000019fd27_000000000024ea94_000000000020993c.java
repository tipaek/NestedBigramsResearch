import java.util.*;

public class Solution{
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        int n=sc.nextInt();
        for(int i=0;i<n;i++)
        {
            int size=sc.nextInt();
            int[][] arr= new int[size][size];
            int diagonal=0;
            int rowCount=0;
            int colCount=0;
            for(int j=0;j<size;j++)
            {
                HashSet<Integer> hs= new HashSet<Integer>();
                for(int k=0;k<size;k++)
                {
                    arr[j][k]=sc.nextInt();
                    if(j==k)
                    {
                        diagonal+=arr[j][k];
                    }
                    hs.add(arr[j][k]);
                }
                if(hs.size()<size)
                {
                    rowCount++;
                }
            }
            
            for(int j=0;j<size;j++)
            {
                HashSet<Integer> hs= new HashSet<Integer>();
                for(int k=0;k<size;k++)
                {
                    if(hs.contains(arr[k][j]))
                    {
                        colCount++;
                        break;
                    }
                    hs.add(arr[k][j]);
                }
            }
            System.out.println("Case #"+i+1"+: "+diagonal+
            " "+rowCount+" "+colCount);
        }
        
    }
}