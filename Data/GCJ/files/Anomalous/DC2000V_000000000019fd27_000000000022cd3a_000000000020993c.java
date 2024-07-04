import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {

    public static void main(String[] args) throws Exception {
        try (InputUtil input = new InputUtil()) {
            int t = input.readLineAsInt();
            for (int i = 0; i < t; i++) {
                solve(i, input);
            }
        }
    }

    private static void solve(int t, InputUtil input) throws Exception {
        int n = input.readLineAsInt();
        int[][] matrix = new int[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = input.readInts();
        }

        int rowsWithRepeatedNums = 0;
        int colsWithRepeatedNums = 0;
        int trace = 0;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (hasRepeatedNumbers(matrix[i], visited)) {
                rowsWithRepeatedNums++;
            }

            if (hasRepeatedNumbers(getColumn(matrix, i), visited)) {
                colsWithRepeatedNums++;
            }

            trace += matrix[i][i];
        }

        System.out.println("Case #" + t + ": " + trace + " " + rowsWithRepeatedNums + " " + colsWithRepeatedNums);
    }

    private static boolean hasRepeatedNumbers(int[] array, boolean[] visited) {
        Arrays.fill(visited, false);
        for (int num : array) {
            if (visited[num - 1]) {
                return true;
            }
            visited[num - 1] = true;
        }
        return false;
    }

    private static int[] getColumn(int[][] matrix, int colIndex) {
        return IntStream.range(0, matrix.length)
                .map(rowIndex -> matrix[rowIndex][colIndex])
                .toArray();
    }

    public static class InputUtil implements Closeable {

        private final BufferedReader reader;

        public InputUtil() throws Exception {
            this.reader = new BufferedReader(new InputStreamReader(System.in));
        }

        public String nextLine() throws IOException {
            return reader.readLine();
        }

        public int readLineAsInt() throws Exception {
            return Integer.parseInt(reader.readLine().trim());
        }

        public long readLineAsLong() throws Exception {
            return Long.parseLong(reader.readLine().trim());
        }

        public String getSpaceSeparatedInts(int[] ints) {
            return Arrays.stream(ints)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "));
        }

        public String getSpaceSeparatedBigInteger(BigInteger[] nums) {
            return Arrays.stream(nums)
                    .map(BigInteger::toString)
                    .collect(Collectors.joining(" "));
        }

        public boolean isEven(long number) {
            return number % 2 == 0;
        }

        public boolean isOdd(long number) {
            return !isEven(number);
        }

        public boolean isEven(BigInteger number) {
            return !number.testBit(0);
        }

        public boolean isOdd(BigInteger number) {
            return !isEven(number);
        }

        public int[] readInts() throws IOException {
            return toIntArray(reader.readLine());
        }

        public int[] toIntArray(String s) {
            return Arrays.stream(s.split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        public long[] readLongs() throws IOException {
            return toLongArray(reader.readLine().trim());
        }

        public long[] toLongArray(String s) {
            return Arrays.stream(s.split("\\s"))
                    .mapToLong(Long::parseLong)
                    .toArray();
        }

        public BigInteger[] readBigInts() throws IOException {
            return toBigIntArray(reader.readLine());
        }

        public BigInteger[] toBigIntArray(String s) {
            return Arrays.stream(s.split("\\s"))
                    .map(BigInteger::new)
                    .toArray(BigInteger[]::new);
        }

        public char[] readChars() throws IOException {
            return reader.readLine().toCharArray();
        }

        public BufferedReader getReader() {
            return reader;
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }
}