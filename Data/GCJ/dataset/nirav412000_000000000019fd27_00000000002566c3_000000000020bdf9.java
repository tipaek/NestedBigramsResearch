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
	
	static void sortbycolomn(int arr[][], int col)
    { 
         
        Arrays.sort(arr, new Comparator<int[]>() { 
            
          @Override              
           
          public int compare(final int[] entry1,  
                             final int[] entry2) { 
  
            
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
		    int n=in.nextInt();
		    int[][] arr=new int[n][3];
		    for(int i=0;i<n;i++){
		        arr[i][0]=in.nextInt();
		        arr[i][1]=in.nextInt();
		        arr[i][2]=i;
		    }
		    sortbycolomn(arr,0);
		    char coll='J';
		    char[] ans=new char[n];
		    ans[arr[0][2]]=coll;
		    if(arr[1][0]<arr[0][1]){
		        coll=(coll=='J'?'C':'J');
		        ans[arr[1][2]]=coll;
		    }
		    else{
		        ans[arr[1][2]]=coll;
		    }
		    int flag=0;
		    for(int i=2;i<n;i++){
		        if(arr[i][0]<arr[i-1][1]){
		            coll=(coll=='J'?'C':'J');
		            if(arr[i][0]<arr[i-2][1]){
		                flag=1;
		                break;
		            }
		            else{
		                ans[arr[i][2]]=coll;
		            }
		        }
		        else{
		            ans[arr[i][2]]=coll;
		        }
		    }
		    if(flag==0){
		        w.println("Case #"+q+": "+new String(ans));    
		    }
		    else{
		        w.println("Case #"+q+": "+"IMPOSSIBLE");
		    }
		   
		    
		    q++;
		    
		}
		w.flush();
		w.close();
	}
}