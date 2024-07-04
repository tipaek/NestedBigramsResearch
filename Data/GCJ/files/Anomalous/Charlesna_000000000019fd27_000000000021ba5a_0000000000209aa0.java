import java.io.*;
import java.util.*;

public class Solution implements Runnable {

    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int testCases = in.nextInt();
        for (int i = 1; i <= testCases; i++) {
            int N = in.nextInt();
            int K = in.nextInt();
            solve(N, K, w, i);
        }
        w.flush();
        w.close();
    }

    private static boolean foundSolution;

    private static void solve(int N, int K, PrintWriter w, int testCaseNumber) {
        foundSolution = false;
        List<List<Integer>> results = new ArrayList<>();
        findSolution(results, new ArrayList<>(), 0, 0, N, K, new boolean[N + 1]);
        if (!foundSolution) {
            w.println("Case #" + testCaseNumber + ": IMPOSSIBLE");
        } else {
            w.println("Case #" + testCaseNumber + ": POSSIBLE");
            for (List<Integer> list : results) {
                w.println(String.join(" ", list.stream().map(String::valueOf).toArray(String[]::new)));
            }
        }
    }

    private static void findSolution(List<List<Integer>> results, List<Integer> current, int row, int col, int N, int K, boolean[] used) {
        if (foundSolution) return;
        if (row == N) {
            int sum = 0;
            for (int i = 0; i < N; i++) {
                sum += results.get(i).get(i);
            }
            if (sum == K) {
                foundSolution = true;
            }
            return;
        }
        if (col == N) {
            results.add(new ArrayList<>(current));
            findSolution(results, new ArrayList<>(), row + 1, 0, N, K, new boolean[N + 1]);
            if (!foundSolution) {
                results.remove(results.size() - 1);
            }
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (used[i]) continue;
            boolean valid = true;
            for (List<Integer> list : results) {
                if (list.get(col) == i) {
                    valid = false;
                    break;
                }
            }
            if (!valid) continue;
            used[i] = true;
            current.add(i);
            findSolution(results, current, row, col + 1, N, K, used);
            if (!foundSolution) {
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buffer = new byte[1024];
        private int currentChar;
        private int numberOfChars;
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numberOfChars == -1) throw new InputMismatchException();
            if (currentChar >= numberOfChars) {
                currentChar = 0;
                try {
                    numberOfChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numberOfChars <= 0) return -1;
            }
            return buffer[currentChar++];
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new InputMismatchException();
            }
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int result = 0;
            do {
                if (c < '0' || c > '9') throw new InputMismatchException();
                result = result * 10 + (c - '0');
                c = read();
            } while (!isSpaceChar(c));
            return result * sign;
        }

        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long result = 0;
            do {
                if (c < '0' || c > '9') throw new InputMismatchException();
                result = result * 10 + (c - '0');
                c = read();
            } while (!isSpaceChar(c));
            return result * sign;
        }

        public double nextDouble() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            double result = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E') return result * Math.pow(10, nextInt());
                if (c < '0' || c > '9') throw new InputMismatchException();
                result = result * 10 + (c - '0');
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') return result * Math.pow(10, nextInt());
                    if (c < '0' || c > '9') throw new InputMismatchException();
                    m /= 10;
                    result += (c - '0') * m;
                    c = read();
                }
            }
            return result * sign;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            StringBuilder result = new StringBuilder();
            do {
                result.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return result.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }
    }

    public static void main(String[] args) throws Exception {
        new Thread(null, new Solution(), "Main", 1 << 27).start();
    }
}