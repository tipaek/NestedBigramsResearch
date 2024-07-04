import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Solution {

	public static void main(String[] args) {
		new Solution();
	}

	public Solution() {
		FastScanner fs = new FastScanner();
		java.io.PrintWriter out = new java.io.PrintWriter(System.out);
		int T = fs.nextInt();
		cont: for (int t = 1;t <= T;++ t) {
			int N = fs.nextInt();
			char[][] P = new char[N][];
			for (int i = 0;i < N;++ i) {
				P[i] = fs.next().toCharArray();
			}
			ArrayList<Character> first = new ArrayList<>(), last = new ArrayList<>(), middle = new ArrayList<>();
			for (int i = 0;i < N;++ i) {
				char[] p = P[i];
				int begin, end;
				for (begin = 0;begin < p.length;++ begin) {
					if (p[begin] == '*') break;
					if (first.size() <= begin) first.add(p[begin]);
					else if (first.get(begin) != p[begin]) {
						out.println(String.format("Case #%d: *", t));
						continue cont;
					}
				}
				for (end = 0;end < p.length;++ end) {
					int read = p.length - 1 - end;
					if (p[read] == '*') break;
					if (last.size() <= end) last.add(p[read]);
					else if (last.get(end) != p[read]) {
						out.println(String.format("Case #%d: *", t));
						continue cont;
					}
				}
				for (;begin < p.length - end;++ begin) if (p[begin] != '*') middle.add(p[begin]);
			}
			first.addAll(middle);
			while(!last.isEmpty()) first.add(last.remove(last.size() - 1));
			out.println(String.format("Case #%d: %s", t, String.join("", first.stream().map(m -> String.valueOf(m)).toArray(String[]::new))));
		}
		out.flush();
	}

	static class FastScanner {

		private final java.io.InputStream in = System.in;
		private final byte[] buffer = new byte[1024];
		private int ptr = 0;
		private int buflen = 0;

		private boolean hasNextByte() {
			if (ptr < buflen) return true;
			ptr = 0;
			try {
				buflen = in.read(buffer);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
			return buflen > 0;
		}

		private byte readByte() {
			return hasNextByte() ? buffer[ptr++] : -1;
		}

		private static boolean isPrintableChar(byte c) {
			return 32 < c || c < 0;
		}

		private static boolean isNumber(int c) {
			return '0' <= c && c <= '9';
		}

		public boolean hasNext() {
			while (hasNextByte() && !isPrintableChar(buffer[ptr]))
				ptr++ ;
			return hasNextByte();
		}

		public String next() {
			if (!hasNext()) throw new java.util.NoSuchElementException();
			StringBuilder sb = new StringBuilder();
			byte b = readByte();
			while (isPrintableChar(b)) {
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		public final long nextLong() {
			if (!hasNext()) throw new NoSuchElementException();
			long n = 0;
			try {
				byte b = readByte();
				if (b == '-') {
					do n = n * 10 + '0' - b; while(isNumber(b = readByte()));
					return n;
				} else if (!isNumber(b)) throw new NumberFormatException();
				do n = n * 10 + b - '0'; while(isNumber(b = readByte()));
				return n;
			} catch (NoSuchElementException e) {
				return n;
			}
		}

		public final int nextInt() {
			if (!hasNext()) throw new NoSuchElementException();
			int n = 0;
			try {
				byte b = readByte();
				if (b == '-') {
					do n = n * 10 + '0' - b; while(isNumber(b = readByte()));
					return n;
				} else if (!isNumber(b)) throw new NumberFormatException();
				do n = n * 10 + b - '0'; while(isNumber(b = readByte()));
				return n;
			} catch (NoSuchElementException e) {
				return n;
			}
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}
