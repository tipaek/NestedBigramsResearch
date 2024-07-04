import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
        String s = input.nextLine();
        int prevState = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int newState = s.charAt(i) - '0';
            if (newState < prevState) {
                sb.append(")".repeat(prevState - newState));
            } else if (newState > prevState) {
                sb.append("(".repeat(newState - prevState));
            }
            sb.append(newState);
            prevState = newState;
        }
        sb.append(")".repeat(prevState));
        System.out.println("Case #" + (t + 1) + ": " + sb.toString());
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
            return IntStream.of(ints)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" "));
        }

        public String getSpaceSeparatedBigInteger(BigInteger[] nums) {
            return Stream.of(nums)
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
            return Stream.of(s.split("\\s+"))
                         .mapToInt(Integer::parseInt)
                         .toArray();
        }

        public long[] readLongs() throws IOException {
            return toLongArray(reader.readLine().trim());
        }

        public String[] readStrings() throws IOException {
            return reader.readLine().split("\\s+");
        }

        public long[] toLongArray(String s) {
            return Stream.of(s.split("\\s+"))
                         .mapToLong(Long::parseLong)
                         .toArray();
        }

        public BigInteger[] readBigInts() throws IOException {
            return toBigIntArray(reader.readLine());
        }

        public BigInteger[] toBigIntArray(String s) {
            return Stream.of(s.split("\\s+"))
                         .map(BigInteger::new)
                         .toArray(BigInteger[]::new);
        }

        public char[] readChars() throws IOException {
            return reader.readLine().toCharArray();
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }
}