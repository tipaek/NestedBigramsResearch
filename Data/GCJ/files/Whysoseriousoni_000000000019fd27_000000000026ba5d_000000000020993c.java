import java.util.*;
import java.io.*;

class Solution
{
    static class FastReader {

        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    public static void main(String args[])
    {
        FastReader s=new FastReader();
        int T=s.nextInt();
        while(t--!=0)
        {
            int n=s.nextInt();
            int arr[][]=new int[n][n];
            for(int i=0;i<n;i++)
                for(int j=0;i<n;j++)    
                    arr[i][j]=s.nextInt();
            
            
            for(int row=0;)    
            
            
            
            
            
        }
        
        
    }
    
}