import java.util.*;
public class Solution
{
    static int expogo(int x,int y,int step, int destx, int desty)
    {
        if (Math.abs(source) > (dest))  
            return Integer.MAX_VALUE; 
      
        if (source == dest)  
            return step; 
            int pos = steps(source + step+Math.pow(2,steps-1), 
                        step+Math.pow(2,steps-1), dest); 
                int neg = steps(source - step-Math.pow(2,steps-1),  
                        step+Math.pow(2,steps-1), dest);
                        
        
    }
    static int steps(int source, int step, 
                                int dest) 
    { 
        // base cases 
        if (Math.abs(source) > (dest))  
            return Integer.MAX_VALUE; 
      
        if (source == dest)  
            return step; 
        int neg = steps(source - step-Math.pow(2,steps-1),  
                        step+Math.pow(2,steps-1), dest);
        
  
        // at each point we can go either way 
  
        // if we go on positive side 
        int pos = steps(source + step+Math.pow(2,steps-1), 
                        step+Math.pow(2,steps-1), dest); 
  
        // if we go on negative side 
        int neg = steps(source - step-Math.pow(2,steps-1),  
                        step+Math.pow(2,steps-1), dest); 
  
        // minimum of both cases 
        return Math.min(pos, neg); 
    } 
    
     public static void main (String[] args)
     {
         Scanner sc = new Scanner(System.in);
         int t=sc.nextInt();
         for(int cmn=1;cmn<=t;cmn++)
         {
             int x=sc.nextInt();
             int y=sc.nextInt();
             int posx=0;
             int posy=0;
             for(int i=0;i<x;i++)
             {
                 for(int j=0;j<)
                 {
                     
                 }
             }
             
             System.out.println("Case #"+cmn+": ");
             
         }
     }
}
