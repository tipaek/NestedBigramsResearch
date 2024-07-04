import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Sparsh Sanchorawala
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Expogo solver = new Expogo();
        int testCount = 1;
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class Expogo {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            HashMap<Integer, HashMap<Integer, int[]>> hm = new HashMap<>();
            for (int k = 1; k <= 10; k++) {
                int lim = (int) Math.pow(4, k);
                for (int mask = 0; mask < lim; mask++) {
                    int x = 0, y = 0;
                    int temp = mask;
                    for (int i = 0; i < k; i++) {
                        int a = temp % 4;
                        if (a <= 1) {
                            x += (2 * a - 1) * (1 << i);
                        } else {
                            a -= 2;
                            y += (2 * a - 1) * (1 << i);
                        }
                        temp /= 4;
                    }
                    if (!hm.containsKey(x))
                        hm.put(x, new HashMap<>());
                    if (!hm.get(x).containsKey(y))
                        hm.get(x).put(y, new int[]{mask, k});
                }
            }

            int t = s.nextInt();
            for (int ti = 1; ti <= t; ti++) {
                int x = s.nextInt(), y = s.nextInt();
                w.print("Case #" + ti + ": ");
                if (hm.containsKey(x) && hm.get(x).containsKey(y)) {
                    int[] ans = hm.get(x).get(y);
                    int temp = ans[0];
                    for (int i = 0; i < ans[1]; i++) {
                        int a = temp % 4;
                        if (a == 0) {
                            w.print("W");
                        } else if (a == 1) {
                            w.print("E");
                        } else if (a == 2) {
                            w.print("S");
                        } else {
                            w.print("N");
                        }
                        temp /= 4;
                    }
                    w.println();
                } else
                    w.println("IMPOSSIBLE");
            }


        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

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

        public int nextInt() {
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

