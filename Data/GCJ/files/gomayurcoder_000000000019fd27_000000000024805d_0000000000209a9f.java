import java.util.*;
import java.io.*;

public class Solution {
	
	public static void main(String[] args) {
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = s.nextInt();
		for(int ti=1;ti<=t;ti++)
		{
			String str = s.next();
			StringBuilder sb = new StringBuilder();
			int p = 0;
			for(int i=0;i<str.length();i++) {
				int val = str.charAt(i)-'0';
				while(p<val) {
					sb.append('(');
					p++;
				}
				while(p>val) {
					sb.append(')');
					p--;
				}
				sb.append(str.charAt(i));
			}
			while(p!=0) {
				sb.append(')');
				p--;
			}
			
			String ans = sb.toString();
			System.out.println("Case #"+ti+": "+ans);

		}
	}
}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
/*			
			int n = s.nextInt();
			long h = s.nextLong();
			long[] a = new long[n];
			for(int i=0;i<n;i++) {
				a[i] = s.nextLong();
			}
			long[] b = new long[n];
			for(int i=0;i<n;i++) {
				b[i] = s.nextLong();
			}
			
			long ans = func(n, h, a, b, 0, 0, 0);
			
			System.out.println("Case #"+ti+": "+ans);
		}
	}
	
	public static long func(int n, long h, long[] a, long[] b, int si, long va, long vb) {
		if(si==n) {
			if(va>=h && vb>=h) {
				return 1;
			}
			else {
				return 0;
			}
		}
		int retval = 0;
		retval += func(n, h, a, b, si+1, va+a[si], vb);
		retval += func(n, h, a, b, si+1, va, vb+b[si]);
		retval += func(n, h, a, b, si+1, va+a[si], vb+b[si]);
		return retval;
	}
}*/
				
/*	static int lowerbound(double[] arr, int s, int e, int val)
	{
		int ans = arr.length;
		while(s<=e)
		{
			int m = (s+e)/2;
			if(arr[m]>=val)
			{
				ans = m;
				e = m-1;
			}
			else
				s = m+1;
		}
		return ans;
	}
	
	static int getparent(int[] parent, int x)
	{
		if(parent[x]==x)
			return x;
		int px = getparent(parent, parent[x]);
		parent[x] = px;
		return px;
	}
	
	static void union(int[] parent, int[] rank, int px, int py)
	{
		if(rank[px]>rank[py])
			parent[py] = px;
		else if(rank[px]<rank[py])
			parent[px] = py;
		else
		{
			rank[px] += 1;
			parent[py] = px;
		}
	}

	static class Pair implements Comparable<Pair>{
		int first;
		int second;

		Pair(int first, int second)
		{
			this.first = first;
			this.second = second;
		}

		Pair(){}
		
		public int compareTo(Pair other)
		{
			if(this.first<other.first)
				return 1;
			else
				return -1;
		}
	}
}