import java.util.*;
import java.io.*;

public class Solution {
    private static final long REM = 1000000000L;
    private final InputReader sc;
    private final boolean singleTest = false;

    public Solution() {
        sc = new InputReader();
        int tests = singleTest ? 1 : sc.nextInt();
        for (int t = 1; t <= tests; t++) {
            solve(t);
        }
    }

    public static void main(String[] args) {
        new Solution();
    }

    private void solve(int test) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        String m = sc.next();
        int n = m.length();

        int[] xMin = new int[n + 1];
        int[] yMin = new int[n + 1];

        xMin[0] = x;
        yMin[0] = y;

        int lastX = x;
        int lastY = y;
        for (int i = 0; i < n; i++) {
            char ch = m.charAt(i);
            switch (ch) {
                case 'N' -> lastY++;
                case 'S' -> lastY--;
                case 'E' -> lastX++;
                case 'W' -> lastX--;
            }
            xMin[i + 1] = lastX;
            yMin[i + 1] = lastY;
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            int minTime = Math.abs(xMin[i]) + Math.abs(yMin[i]);
            if (minTime <= i) {
                res = Math.min(res, i);
            }
        }

        if (res < Integer.MAX_VALUE) {
            System.out.println("Case #" + test + ": " + res);
        } else {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
        }
    }

    private static class InputReader {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputReader() {
            this(System.in);
        }

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}