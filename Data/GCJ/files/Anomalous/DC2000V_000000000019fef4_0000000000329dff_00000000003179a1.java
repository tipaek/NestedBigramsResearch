import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException, Exception {
        try (InputUtil input = new InputUtil()) {
            int t = input.readLineAsInt();
            for (int i = 0; i < t; i++) {
                solve(input, i + 1);
            }
        }
    }

    private static void solve(InputUtil input, int t) throws IOException {
        input.nextLine();
        String[][] question = new String[10_000][];
        Set<Character> allChars = new HashSet<>();
        for (int i = 0; i < question.length; i++) {
            question[i] = input.readStrings();
            for (char c : question[i][1].toCharArray()) {
                allChars.add(c);
            }
        }
        Map<Character, Integer> mapping = new HashMap<>();
        for (int i = 0; i < question.length; i++) {
            if (!"-1".equals(question[i][0]) && question[i][0].length() == question[i][1].length()) {
                char key = question[i][1].charAt(0);
                int value = question[i][0].charAt(0) - '0';
                mapping.merge(key, value, Math::min);
            }
        }
        allChars.removeAll(mapping.keySet());
        Iterator<Character> it = allChars.iterator();
        char[] result = new char[10];
        Arrays.fill(result, (char) 11);
        for (Map.Entry<Character, Integer> entry : mapping.entrySet()) {
            char c = entry.getKey();
            int index = entry.getValue();
            if (result[index] != (char) 11) {
                allChars.add(c);
                continue;
            }
            result[index] = c;
        }
        for (int i = 0; i < 10; i++) {
            if (result[i] == (char) 11) {
                result[i] = it.next();
            }
        }
        System.out.println("Case #" + t + ": " + new String(result));
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
            return toLongArray(reader.readLine());
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

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }
}