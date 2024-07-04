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

        public String[] readStrings() throws IOException {
            return reader.readLine().split("\\s");
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

    public static void main(String[] args) throws Exception {
        try (InputUtil input = new InputUtil()) {
            int t = input.readLineAsInt();
            for (int i = 0; i < t; i++) {
                solve(i + 1, input);
            }
        }
    }

    private static class Span {
        final int start, end;
        byte occupiedBy = 0;

        public Span(int start, int end) {
            this.start = start;
            this.end = end;
        }

        void setOccupiedByCameron() {
            this.occupiedBy = 'C';
        }

        void setOccupiedByJamie() {
            this.occupiedBy = 'J';
        }
    }

    private static void solve(int t, InputUtil input) throws Exception {
        int n = input.readLineAsInt();
        byte[] minutes = new byte[24 * 60];
        Span[] spans = new Span[n];

        for (int i = 0; i < n; i++) {
            int[] times = input.readInts();
            spans[i] = new Span(times[0], times[1]);
        }

        Span[] sortedSpans = Arrays.copyOf(spans, n);
        Arrays.sort(sortedSpans, (a, b) -> a.start != b.start ? Integer.compare(a.start, b.start) : Integer.compare(a.end, b.end));

        for (Span span : sortedSpans) {
            boolean cameronAvailable = true;
            boolean jamieAvailable = true;

            for (int i = span.start; i < span.end; i++) {
                if (minutes[i] == 'C') {
                    cameronAvailable = false;
                } else if (minutes[i] == 'J') {
                    jamieAvailable = false;
                }
                if (!cameronAvailable && !jamieAvailable) {
                    break;
                }
            }

            if (cameronAvailable) {
                Arrays.fill(minutes, span.start, span.end, (byte) 'C');
                span.setOccupiedByCameron();
            } else if (jamieAvailable) {
                Arrays.fill(minutes, span.start, span.end, (byte) 'J');
                span.setOccupiedByJamie();
            } else {
                System.out.println("Case #" + t + ": IMPOSSIBLE");
                return;
            }
        }

        System.out.print("Case #" + t + ": ");
        for (Span span : spans) {
            System.out.print((char) span.occupiedBy);
        }
        System.out.println();
    }
}