import java.io.*;
import java.util.*;

public class Solution {

    private static final long MOD = 1_000_000_007L;
    private static final long LARGE_MOD = 1_000_000_000L;

    private static ArrayList<Integer>[] adjacencyList;
    private static double[][] dp;
    private static long[] factorialArray;
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static PrintWriter out;
    private static long[] factorial = new long[(int) 1e6];
    private static boolean hasCycle = false;
    private static int[] colors;
    private static String inputString;
    private static int minimumAnswer = Integer.MAX_VALUE;
    private static ArrayList<Pair> pairs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        out = new PrintWriter(System.out);

        int testCases = scanner.nextInt();

        for (int testCase = 1; testCase <= testCases; testCase++) {
            long x = scanner.nextLong();
            long y = scanner.nextLong();
            String directions = scanner.next();

            int answer = -1;

            for (int i = 0; i < directions.length() && answer == -1; i++) {
                switch (directions.charAt(i)) {
                    case 'N' -> y++;
                    case 'S' -> y--;
                    case 'E' -> x++;
                    case 'W' -> x--;
                }

                if (Math.abs(x) + Math.abs(y) <= i + 1) {
                    answer = i + 1;
                }
            }

            if (answer == -1) {
                out.println("Case #" + testCase + ": IMPOSSIBLE");
            } else {
                out.println("Case #" + testCase + ": " + answer);
            }
        }

        out.close();
    }

    private static class Pair implements Comparable<Pair> {
        int first, second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair other) {
            return 1;
        }
    }

    private static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static class Reader {
        private static final int BUFFER_SIZE = 1 << 16;
        private DataInputStream dataInputStream;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            dataInputStream = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String fileName) throws IOException {
            dataInputStream = new DataInputStream(new FileInputStream(fileName));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[1000001];
            int count = 0, character;
            while ((character = read()) != -1) {
                if (character == '\n') break;
                buf[count++] = (byte) character;
            }
            return new String(buf, 0, count);
        }

        public int nextInt() throws IOException {
            int result = 0;
            byte character = read();
            while (character <= ' ') character = read();
            boolean negative = (character == '-');
            if (negative) character = read();
            do {
                result = result * 10 + character - '0';
            } while ((character = read()) >= '0' && character <= '9');
            return negative ? -result : result;
        }

        public long nextLong() throws IOException {
            long result = 0;
            byte character = read();
            while (character <= ' ') character = read();
            boolean negative = (character == '-');
            if (negative) character = read();
            do {
                result = result * 10 + character - '0';
            } while ((character = read()) >= '0' && character <= '9');
            return negative ? -result : result;
        }

        public double nextDouble() throws IOException {
            double result = 0, divisor = 1;
            byte character = read();
            while (character <= ' ') character = read();
            boolean negative = (character == '-');
            if (negative) character = read();
            do {
                result = result * 10 + character - '0';
            } while ((character = read()) >= '0' && character <= '9');
            if (character == '.') {
                while ((character = read()) >= '0' && character <= '9') {
                    result += (character - '0') / (divisor *= 10);
                }
            }
            return negative ? -result : result;
        }

        private void fillBuffer() throws IOException {
            bytesRead = dataInputStream.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (dataInputStream == null) return;
            dataInputStream.close();
        }

        public int[] nextIntArray(int n) throws IOException {
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = nextInt();
            }
            return array;
        }

        public long[] nextLongArray(int n) throws IOException {
            long[] array = new long[n];
            for (int i = 0; i < n; i++) {
                array[i] = nextLong();
            }
            return array;
        }

        public int[][] nextInt2DArray(int rows, int cols) throws IOException {
            int[][] array = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    array[i][j] = nextInt();
                }
            }
            return array;
        }

        public long[][] nextLong2DArray(int rows, int cols) throws IOException {
            long[][] array = new long[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    array[i][j] = nextLong();
                }
            }
            return array;
        }
    }
}