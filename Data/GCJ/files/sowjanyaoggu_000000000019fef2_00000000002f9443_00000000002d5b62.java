import java.util.*;
public class Main
{
    public static void main(String[] args)
    {
        Scanner s=new Scanner(System.in);
        int t=s.nextInt();
        for(int i=0;i<t;i++)
        {
            int a=s.nextInt();
            int b=s.nextInt();
            if(a>0 && b>0 ||a<0 && b<0)
            {
                if(a>0 && b==0)
                    System.out.print("EE");
                else if(a<0 && b<0)
                    System.out.print("NWS");
                else if((a<0 && b>0) ||(a>0 && b<0))
                    System.out.print("IMPOSSIBLE");
                else if(a==0 && b>0)
                    System.out.print("WW");
            }
            System.out.println("Case #"+i+": ")
        }
    }
}