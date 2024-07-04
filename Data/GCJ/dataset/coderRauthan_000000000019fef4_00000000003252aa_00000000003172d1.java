import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class Solution
{
	private static class InputReader 
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
		
		public int read() {
			if (numChars == -1) {
				throw new InputMismatchException();
			}
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}
		
		public int readInt() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
		
		public String readString() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}
		
		public double readDouble() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') {
				if (c == 'e' || c == 'E') {
					return res * Math.pow(10, readInt());
				}
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') {
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) {
					if (c == 'e' || c == 'E') {
						return res * Math.pow(10, readInt());
					}
					if (c < '0' || c > '9') {
						throw new InputMismatchException();
					}
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}
		
		public long readLong() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
		
		public boolean isSpaceChar(int c) {
			if (filter != null) {
				return filter.isSpaceChar(c);
			}
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
		
		public String next() {
			return readString();
		}
		
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}
	
	private static class OutputWriter {
		private final PrintWriter writer;
		
		public OutputWriter(OutputStream outputStream) {
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
		}
		
		public OutputWriter(Writer writer) {
			this.writer = new PrintWriter(writer);
		}
		
		public void print(Object... objects) {
			for (int i = 0; i < objects.length; i++) {
				if (i != 0) {
					writer.print(' ');
				}
				writer.print(objects[i]);
			}
			writer.flush();
		}
		
		public void printLine(Object... objects) {
			print(objects);
			writer.println();
			writer.flush();
		}
		
		public void close() {
			writer.close();
		}
		
		public void flush() {
			writer.flush();
		}
	}
	
	
		public static void main(String[] args) throws Exception 
	   {
	        InputReader in 		= new InputReader(System.in);
	        OutputWriter out 	= new OutputWriter(System.out);
	        
			int testCases = in.readInt();
			
			n:for(int i=1; i<= testCases; i++)
			{
				int pizzas 		= in.readInt();
				int requiredPizzas = in.readInt();
				
				int[] size = new int[pizzas];
				
				for(int j=1; j<= pizzas; j++)
				{
					size[j-1] = in.readInt();
				}
				
				
				
				Map<Integer,Integer> map = new HashMap<>();
				
				int biggerPresent = 0;
				
				for(int j=0; j<size.length; j++)
				{
					if(map.containsKey(size[j]))
					{
						map.put(size[j], map.get(size[j]) + 1);
						
						if(map.get(size[j]) == requiredPizzas)
						{
							biggerPresent++;
						}
					}
					else
					{
						map.put(size[j], 1);
					}
				}
				
				if(biggerPresent > 0)
				{
					out.printLine("Case #"+i+": 0");
					continue n;
				}
				
				if(requiredPizzas == 2)
				{
					out.printLine("Case #"+i+": 1");
					continue n;
				}
				
				int ans = 1;
				
				int cut = 2;
				while(true)
				{
					for(int j=0; j< size.length; j++)
					{
						int sz = size[j];
						
						int share = sz/cut;
						
						int fullSize = map.getOrDefault(share, 0) + cut;
						
						if(fullSize >= requiredPizzas)
						{
							out.printLine("Case #"+i+": "+ans);
							continue n;
						}
					 }
					
					cut++;
					ans++;
				}
				
				
				
				
			}
			
 
			//out.printLine(a);
		}
	}
	