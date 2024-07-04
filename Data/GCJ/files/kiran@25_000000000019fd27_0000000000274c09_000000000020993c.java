import java.util.*;
public class Main{
    public static void main(String[] args){
    int n,m,i,j;
    Scanner st=new Scanner(System.in);
    n=st.nextInt();
    while(n--)
    {
        int count=0,trace=0;
        m=st.nextInt();
        int a[m][m];
        for(i=0;i<m;i++)
        {
            for(j=0;j<m;j++)
            {
                a[i][j]=st.nextInt();
            }
        }
        for(i=0;i<m;i++)
        {
            for(j=0;j<m;j++)
            {
                if(i==j)
                    trace+=a[i][j];
            }
        }
        System.out.print(trace+" ");
        HashMap<Integer,Integer> row=new HashMap<Integer,Integer>();
        int r1=0
        for(i=0;i<m;i++)
        {
            int r=0;
            for(j=0;j<m;j++)
            {
                if(!row.containsKey(a[i][j])
                    row.put(a[i][j],j);
                else
                    r=1;
            }
            if(r==0)
                r1=0;
            else
                r1=r1+1;
            map.clear();
        }
         HashMap<Integer,Integer> col=new HashMap<Integer,Integer>();
        int c1=0;
        for(j=0;j<m;j++)
        {
            int c=0;
            for(i=0;i<m;i++)
            {
                if(!col.containsKey(a[j][i])
                    col.put(a[j][i],i);
                else
                    c=1;
            }
            if(c==0)
                c1=0;
            else
                c1=c1+1;
            map.clear();
        }
        System.out.println(r1+" "+c1);
    }
  }
}