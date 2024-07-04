import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;
 
import static java.lang.Math.*;
 
public class Solution implements Runnable 
{
	static class InputReader 
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		public InputReader(InputStream stream) 
		{
			this.stream = stream;
		}
		
		public int read()
		{
			if (numChars==-1) 
				throw new InputMismatchException();
            
			if (curChar >= numChars) 
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
                
				if(numChars <= 0)               
					return -1;
			}
			return buf[curChar++];
		}
     
		public String nextLine()
		{
			String str = "";
			try
			{
				str = br.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}	
			return str;
		}
		public int nextInt() 
		{
			int c = read();
            
			while(isSpaceChar(c)) 
				c = read();
		
			int sgn = 1;
        
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
            
			int res = 0;
			do
			{
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
        
			return res * sgn;
		}
        
		public long nextLong() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-')
			{
				sgn = -1;
				c = read();
			}
			long res = 0;
			
			do 
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}	
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-')
			{
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') 
			{
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') 
			{
				c = read();
				double m = 1;
				while (!isSpaceChar(c))
				{
					if (c == 'e' || c == 'E')
						return res * Math.pow(10, nextInt());
					if (c < '0' || c > '9')
						throw new InputMismatchException();
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}
    
		public String readString() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do 
			{
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
            
			return res.toString();
		}
     
		public boolean isSpaceChar(int c) 
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
     
		public String next()
		{
			return readString();
		}
        
		public interface SpaceCharFilter
		{
			public boolean isSpaceChar(int ch);
		}
	}
	public static void main(String args[]) throws Exception 
	{
		new Thread(null, new Solution(),"Main",1<<27).start();
	}	
	
	public static long gcd(long a, long b) 
	{ 
		if (a == 0) 
			return b; 
		return gcd(b % a, a); 
	} 
  
	public static long findGCD(long arr[], int n) 
	{ 
		long result = arr[0]; 
		for (int i = 1; i < n; i++) 
			result = gcd(arr[i], result); 
		return result; 
	}	 
	
	static void sortbycolomn(long arr[][], int col)
    { 
         
        Arrays.sort(arr, new Comparator<long[]>() { 
            
          @Override              
           
          public int compare(final long[] entry1,  
                             final long[] entry2) { 
  
            
            if (entry1[col] > entry2[col]) 
                return 1; 
            else
                return -1; 
          } 
        });   
    } 
	
	public void run()
	{
		InputReader in = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t=in.nextInt();
		int q=1;
		while(t--!=0)
		{
		    String s=in.next();
		    int n=s.length();
		    int op=0;
		    int x=0;
		    String ans="";
		    for(int i=0;i<n;i++){
		        x=((int)s.charAt(i)-48);
		        if(x!=0){
		            if(op>x){
		                for(int j=0;j<op-x;j++){
		                    ans+=")";
		                }
		                op=x;
		                ans+=Integer.toString(x);
		            }
		            else{
		                for(int j=0;j<x-op;j++){
		                    ans+="(";
		                }
		                op=x;
		                ans+=Integer.toString(x);
		            }
		        }
		        else{
		            for(int j=0;j<op;j++){
		                ans+=")";
		            }
		            ans+="0";
		            op=0;
		        }
		    }
		    for(int i=0;i<op;i++){
		        ans+=")";
		    }
		    
		    w.println("Case #"+q+": "+ans);
		    q++;
		    
		}
		w.flush();
		w.close();
	}
}