import java.util.*;
class Solution{
    public static void main(String args[])
    {
        Scanner s=Scanner(System.in);
        int n=s.nextInt();
        int i,j,k;
        for(k=0;k<n;k++)
        {
          int size=s.nextInt();
          ArrayList<ArrayList<Integer>><Integer> l=new ArrayList<Integer>(size);
          for(i=0;i<size;i++)
          {
              for(j=0;j<size;j++)
              l.add(s.nextInt());
          }
          int trace=0;
          for(i=0;i<n;i++)
          {
              trace+=l[i][i];
          }
          System.out.println(trace);
        }
    }
}