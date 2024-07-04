import java.util.*;
import java.io.*;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

class Solution{
	static class Reader 
    { 
        final private int BUFFER_SIZE = 1 << 16; 
        private DataInputStream din; 
        private byte[] buffer; 
        private int bufferPointer, bytesRead; 
  
        public Reader() 
        { 
            din = new DataInputStream(System.in); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public Reader(String file_name) throws IOException 
        { 
            din = new DataInputStream(new FileInputStream(file_name)); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public String readLine() throws IOException 
        { 
            byte[] buf = new byte[64]; // line length 
            int cnt = 0, c; 
            while ((c = read()) != -1) 
            { 
                if (c == '\n') 
                    break; 
                buf[cnt++] = (byte) c; 
            } 
            return new String(buf, 0, cnt); 
        } 
  
        public int nextInt() throws IOException 
        { 
            int ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do
            { 
                ret = ret * 10 + c - '0'; 
            }  while ((c = read()) >= '0' && c <= '9'); 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public long nextLong() throws IOException 
        { 
            long ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public double nextDouble() throws IOException 
        { 
            double ret = 0, div = 1; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
  
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
  
            if (c == '.') 
            { 
                while ((c = read()) >= '0' && c <= '9') 
                { 
                    ret += (c - '0') / (div *= 10); 
                } 
            } 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        private void fillBuffer() throws IOException 
        { 
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE); 
            if (bytesRead == -1) 
                buffer[0] = -1; 
        } 
  
        private byte read() throws IOException 
        { 
            if (bufferPointer == bytesRead) 
                fillBuffer(); 
            return buffer[bufferPointer++]; 
        } 
  
        public void close() throws IOException 
        { 
            if (din == null) 
                return; 
            din.close(); 
        } 
    }

    static int[] getColumn(int[][] matrix, int column) {
    return IntStream.range(0, matrix.length)
        .map(i -> matrix[i][column]).toArray();
	}

    public static void main(String[] args) throws IOException {
    	Reader r = new Reader();
    	int t = r.nextInt();
    	for (int z=0;z<t ;z++ ) {
    		int n = r.nextInt();
    		int[][] m = new int[n][n];
    		int trace = 0;
    		int rCnt=0;
    		int cCnt = 0;
    		for (int i=0;i<n ;i++ ) {
    			for (int j=0;j<n ;j++ ) {
    				m[i][j] = r.nextInt();
    				if(i == j){
    					trace += m[i][j];
    				}
    			}
    		}
    		for (int i=0;i<n ;i++ ) {
    			Integer [] temp = IntStream.of( m[i]).boxed().toArray(Integer[]::new);

    			Set<Integer> tempSet = new HashSet<>(Arrays.asList(temp));
    			if(tempSet.size()<n){
    				rCnt++;
    			}
    		}
    		for (int i=0;i<n ;i++ ) {
    			Integer [] temp = IntStream.of( getColumn(m,i)).boxed().toArray(Integer[]::new);

    			Set<Integer> tempSet = new HashSet<>(Arrays.asList(temp));
    			if(tempSet.size()<n){
    				cCnt++;
    			}
    		}
    		System.out.println("Case #"+(z+1)+": "+trace+" "+rCnt+" "+cCnt);
    	}
    }
}