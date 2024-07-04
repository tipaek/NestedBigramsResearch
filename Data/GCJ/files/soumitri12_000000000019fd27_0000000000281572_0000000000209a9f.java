import java.util.*;
import java.lang.*;
import java.io.*;
import java.text.*;

/* Name of the class has to be "Main" only if the class is public*/
public class Solution
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
    
    static class Node {
        
        long pp;
        long a, b;
        
        Node(long x, long y) {
            a = x;
            b = y;
            pp = a * b;
        }
    }
    static class Comp implements Comparator<Node> {
        
        public int compare(Node o1, Node o2) {
            if (o1.pp > o2.pp) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    static int gcd(int x, int y)
    {
        if(y==0) return x;
        else return gcd(y,x%y);
    } 
    
    public static void main(String[] args) {
        FastReader sc=new FastReader();
        PrintWriter out=new PrintWriter(System.out);
        //your code starts here
        int t=sc.nextInt();
        for(int q=1;q<=t;q++)
        {
            String s=sc.next(); 
            StringBuffer sb=new StringBuffer();
            if(s.charAt(0)=='1')
            sb.append("(");
            for(int i=0;i<s.length()-1;i++)
            {
                if(s.charAt(i)=='1')
                {
                    sb.append(s.charAt(i));
                    if(s.charAt(i+1)=='1') continue;
                    else sb.append(")");
                }
                if(s.charAt(i)=='0')
                {
                    sb.append(s.charAt(i));
                    if(s.charAt(i+1)=='1') sb.append("(");
                    else continue;
                }
            }
            sb.append(s.charAt(s.length()-1));
            if(s.charAt(s.length()-1)=='1') sb.append(")");
            out.println("Case #"+q+": "+sb.toString());
        }

        out.close();
    }
}
