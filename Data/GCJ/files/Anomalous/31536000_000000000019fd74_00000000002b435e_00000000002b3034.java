import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class Solution {

    public static void main(String[] args) {
        new Solution();
    }

    public Solution() {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        int T = fs.nextInt();
        outer: for (int t = 1; t <= T; ++t) {
            int N = fs.nextInt();
            char[][] patterns = new char[N][];
            for (int i = 0; i < N; ++i) {
                patterns[i] = fs.next().toCharArray();
            }
            ArrayList<Character> prefix = new ArrayList<>(), suffix = new ArrayList<>(), middle = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                char[] pattern = patterns[i];
                int start, end;
                for (start = 0; start < pattern.length; ++start) {
                    if (pattern[start] == '*') break;
                    if (prefix.size() <= start) {
                        prefix.add(pattern[start]);
                    } else if (prefix.get(start) != pattern[start]) {
                        out.println(String.format("Case #%d: *", t));
                        continue outer;
                    }
                }
                for (end = 0; end < pattern.length; ++end) {
                    int readIndex = pattern.length - 1 - end;
                    if (pattern[readIndex] == '*') break;
                    if (suffix.size() <= end) {
                        suffix.add(pattern[readIndex]);
                    } else if (suffix.get(end) != pattern[readIndex]) {
                        out.println(String.format("Case #%d: *", t));
                        continue outer;
                    }
                }
                for (; start < pattern.length - end; ++start) {
                    if (pattern[start] != '*') {
                        middle.add(pattern[start]);
                    }
                }
            }
            prefix.addAll(middle);
            while (!suffix.isEmpty()) {
                prefix.add(suffix.remove(suffix.size() - 1));
            }
            out.println(String.format("Case #%d: %s", t, String.join("", prefix.stream().map(String::valueOf).toArray(String[]::new))));
        }
        out.flush();
    }

    static class FastScanner {

        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1024];
        private int ptr = 0;
        private int buflen = 0;

        private boolean hasNextByte() {
            if (ptr < buflen) return true;
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (IOException e) {
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
            while (hasNextByte() && !isPrintableChar(buffer[ptr])) {
                ptr++;
            }
            return hasNextByte();
        }

        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
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
            boolean negative = false;
            byte b = readByte();
            if (b == '-') {
                negative = true;
                b = readByte();
            }
            if (!isNumber(b)) throw new NumberFormatException();
            do {
                n = n * 10 + b - '0';
            } while (isNumber(b = readByte()));
            return negative ? -n : n;
        }

        public final int nextInt() {
            if (!hasNext()) throw new NoSuchElementException();
            int n = 0;
            boolean negative = false;
            byte b = readByte();
            if (b == '-') {
                negative = true;
                b = readByte();
            }
            if (!isNumber(b)) throw new NumberFormatException();
            do {
                n = n * 10 + b - '0';
            } while (isNumber(b = readByte()));
            return negative ? -n : n;
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}