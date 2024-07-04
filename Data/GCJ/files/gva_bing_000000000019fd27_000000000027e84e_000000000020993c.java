import java.io.*;
import java.util.*;
public class Solution {
   public static void main(String[] args) {
	   try
	   {
	   BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	   Integer T=Integer.parseInt(br.readLine());
	   int x=0;
	   while(T-->0)
	   {
		   Integer N=Integer.parseInt(br.readLine());
		   int [][] grid=new int[N][N];
		   int k=0;
		   for(int i=0;i<N;i++)
		   {
			   String [] strarr=br.readLine().trim().split("\\s+");
			   
			   for(int j=0;j<strarr.length;j++)
			   {
				  grid[k][j]=Integer.parseInt(strarr[j]);
			   }
			   k+=1;
		   }
		  x+=1;
		   System.out.println("Case #"+x+": "+sum(grid[][])+" "+duplicate(grid));
	   }
	
	   }
	   catch(Exception e)
	   {
		   
   }
}
   public static boolean rowutil(int [] arr)
   {
	   Set<Integer> set=new HashSet<>();
	   for(int i:arr)
	   {
		   
		   if(!set.add(i))
			   return true;
	   }
	   return false;
   }

   public static int duplicate(int [][] grid)
   {
	   int res=0,n=grid.length;
	   for(int [] arr:grid)
	   {
		  
		   if(rowutil(arr))
			   res+=1;
	   }
	   
	   return res;
   }
}