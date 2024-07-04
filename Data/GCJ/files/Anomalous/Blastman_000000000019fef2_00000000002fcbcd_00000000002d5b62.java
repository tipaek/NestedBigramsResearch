import java.io.*;
import java.util.*;

public class Solution {

    static final boolean USE_STDIN = true;
    static final String FILENAME = "";
    static FastScanner scanner;
    static PrintWriter writer;

    public static void main(String[] args) throws IOException {
        if (USE_STDIN) {
            scanner = new FastScanner();
            writer = new PrintWriter(new OutputStreamWriter(System.out));
        } else {
            scanner = new FastScanner(FILENAME + ".in");
            writer = new PrintWriter(new FileWriter(FILENAME + ".out"));
        }

        Solver solver = new Solver();
        int testCases = scanner.nextInt();
        for (int i = 1; i <= testCases; i++) {
            System.out.print("Case #" + i + ": ");
            solver.solve(scanner, writer);
        }
        writer.close();
    }

    static class Solver {
        static final long MOD = (long) 1e20 + 7;

        public void solve(FastScanner scanner, PrintWriter writer) throws IOException {
            long x = scanner.nextLong();
            long y = scanner.nextLong();
            int[] result = null;

            if (Math.abs(x) != Math.abs(y)) {
                for (int i = 0; i < 31; i++) {
                    result = findPath(x, y, true, i);
                    if (result != null) break;

                    result = findPath(x, y, false, i);
                    if (result != null) break;
                }
            }

            if (result == null) {
                System.out.println("IMPOSSIBLE");
            } else {
                for (int direction : result) {
                    switch (direction) {
                        case 1 -> System.out.print("S");
                        case 2 -> System.out.print("N");
                        case 3 -> System.out.print("W");
                        case 4 -> System.out.print("E");
                    }
                }
                System.out.println();
            }
        }

        static int[] findPath(long x, long y, boolean vertical, int jump) {
            int[] path = new int[jump + 1];
            if (vertical) {
                if (y < 0) {
                    y += power(2, jump);
                    path[jump] = 1;
                } else if (y > 0) {
                    y -= power(2, jump);
                    path[jump] = 2;
                } else {
                    return null;
                }
            } else {
                if (x < 0) {
                    x += power(2, jump);
                    path[jump] = 3;
                } else if (x > 0) {
                    x -= power(2, jump);
                    path[jump] = 4;
                } else {
                    return null;
                }
            }

            if (jump == 0) {
                return (x == 0 && y == 0) ? path : null;
            } else {
                jump--;
                int[] result = findPath(x, y, true, jump);
                if (result != null) {
                    System.arraycopy(result, 0, path, 0, jump + 1);
                } else {
                    result = findPath(x, y, false, jump);
                    if (result != null) {
                        System.arraycopy(result, 0, path, 0, jump + 1);
                    } else {
                        path = null;
                    }
                }
                return path;
            }
        }

        static long power(long base, long exp) {
            if (exp == 0) return 1;
            long half = power(base, exp / 2);
            return (exp % 2 == 0) ? half * half % MOD : half * half % MOD * base % MOD;
        }
    }

    static class Point implements Comparable<Point> {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point other) {
            return (x == other.x) ? y - other.y : x - other.x;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                Point other = (Point) obj;
                return x == other.x && y == other.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(String filename) {
            try {
                br = new BufferedReader(new FileReader(filename));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public int nextInt() {
            return Integer.parseInt(nextToken());
        }

        public long nextLong() {
            return Long.parseLong(nextToken());
        }

        public double nextDouble() {
            return Double.parseDouble(nextToken());
        }

        public String nextToken() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
    }
}