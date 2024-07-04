import java.lang.*;
public class Q
{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in)
        int n = sc.nextInt()
        while(n>0){
            int t = sc.nextInt()
            for(int i=0;i<t;i++)
                for(int j=0;j<t;j++)
                {
                  if(i=j)
                    {
                        sum = sum + i;
                    }
                }
            n=n-1
        }
    }
}