import java.util.*;
 class Solution
{
	public static void main(String[] args) 
	{ 
	    Scanner in=new Scanner(System.in);
	    int testcases=in.nextInt();
	    long A=in.nextLong();
        long B=in.nextLong();
	        
	    for(int tc=1;tc<=testcases;tc++)
	    {
	        int f=0;
	        long x=0,y=0;
	        long xL=0,xR=0,yU=0,yB=0;
	        for( x=-1000000000;x<=-999999950;x++,xL++)
	            {
	                System.out.println(x+" "+0);
	                String input=in.next();
	                if(input.equals("HIT")break;
	            }
	        
	        for( x=1000000000;x>=999999950;x--,xR++)
	            {
	                System.out.println(x+" "+0);
	                String input=in.next();
	                if(input.equals("HIT")break;
	            }
	            
	           for( y=1000000000;y>=999999950;y--,yU++)
	            {
	                System.out.println(0+" "+y);
	                String input=in.next();
	                if(input.equals("HIT")break;
	            } 
	            
	            for( y=-1000000000;y<=-999999950;y++,uB++)
	            {
	                System.out.println(0+" "+y);
	                String input=in.next();
	                if(input.equals("HIT")break;
	            }
	            
	                System.out.println((0+xL-xR)+" "+(0+yB-yU));
	                String input=in.next();
	                
	            
	    }
	}
}
