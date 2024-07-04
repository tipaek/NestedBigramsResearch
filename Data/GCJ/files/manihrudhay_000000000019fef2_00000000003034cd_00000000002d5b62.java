import java.util.*;

public class sol
{
    
    static String steps(int s_x,int s_y, StringBuffer step,int dest_x,int dest_y,int count) 
     { 
         if (Math.abs(s_x) > (dest_x) || Math.abs(s_y) > (dest_y))  
            { 
            String s= new String("IMPOSSIBLE");
            return s;
            }
          if (s_x == dest_x && s_y == dest_y)  
               return step;
        StringBuffer str=new  
        int east = steps(s_x+pow(2,count),s_y,step + 1, dest); 
        int west = steps(source - step - 1,step + 1, dest); 
        count+=1;
        return Math.min(pos, neg); 

    }
    public static void main()
    {
        scanner sc = new scanner(System.in);
        
     //no of test cases
     int i=0,t=sc.nextInt();
     for(i=1;i<=t;i++)
     {
         int x=sc.nextInt();
         int y=sc.nextInt();
         
         StringBuffer step= new StringBuffer();
         
         System.out.println(steps(0, 0,step, dest_x,dest_y));
         int sum=x+y;
         while
         
         
     }
     
    }
}