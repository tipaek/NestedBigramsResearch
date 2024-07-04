import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {

    public static void main(String[] args) throws IOException, Exception {
        try (InputUtil input = new InputUtil()) {
            int t = input.readLineAsInt();
            for (int i = 0; i < t; i++) {
                solve(i + 1, input);
            }
        }
    }

    private static void solve(int t, InputUtil input) throws IOException {
        String[] dirs = input.readStrings();
        int x = Integer.parseInt(dirs[0]);
        int y = Integer.parseInt(dirs[1]);
        String movement = dirs[2];
        int catDistance = 0;

        if (Math.abs(x) + Math.abs(y) <= catDistance) {
            System.out.format("Case #%d: %d\n", t, catDistance);
            return;
        }

        for (int i = 0; i < movement.length(); i++) {
            switch (movement.charAt(i)) {
                case 'S':
                    y--;
                    break;
                case 'N':
                    y++;
                    break;
                case 'E':
                    x++;
                    break;
                case 'W':
                    x--;
                    break;
            }
            catDistance++;

            if (Math.abs(x) + Math.abs(y) <= catDistance) {
                System.out.format("Case #%d: %d\n", t, catDistance);
                return;
            }
        }
        System.out.format("Case #%d: IMPOSSIBLE\n", t);
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
            return Long.parseLong(reader.readLine());
        }

        public String getSpaceSeparatedInts(int[] ints) {
            return Stream.of(ints)
                    .map(String::valueOf)
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
            return !number.testBit(1);
        }

        public boolean isOdd(BigInteger number) {
            return !isEven(number);
        }

        public int[] readInts() throws IOException {
            return toIntArray(reader.readLine());
        }

        public int[] toIntArray(String s) {
            return Stream.of(s.split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        public long[] readLongs() throws IOException {
            return toLongArray(reader.readLine());
        }

        public String[] readStrings() throws IOException {
            return reader.readLine().split("\\s");
        }

        public long[] toLongArray(String s) {
            return Stream.of(s.split("\\s"))
                    .mapToLong(Long::parseLong)
                    .toArray();
        }

        public BigInteger[] readBigInts() throws IOException {
            return toBigIntArray(reader.readLine());
        }

        public BigInteger[] toBigIntArray(String s) {
            String[] nums = s.split("\\s");
            return IntStream.range(0, nums.length)
                    .mapToObj(index -> new BigInteger(nums[index]))
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