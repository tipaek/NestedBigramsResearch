import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Solution {
    static int targetX, targetY, found;
    static int[] result;

    static void findPath(int index, int[] path, int limit) {
        if (index == limit) {
            int power = 1;
            int x = 0, y = 0;
            for (int direction : path) {
                switch (direction) {
                    case 1 -> y -= power;
                    case 2 -> y += power;
                    case 3 -> x -= power;
                    case 4 -> x += power;
                }
                power *= 2;
            }
            if (x == targetX && y == targetY) {
                found = 1;
                System.arraycopy(path, 0, result, 0, path.length);
            }
            return;
        }
        if (found == 1) return;

        for (int direction = 1; direction <= 4; direction++) {
            path[index] = direction;
            findPath(index + 1, path, limit);
            if (found == 1) return;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader in = new FastReader(System.in);
        StringBuilder output = new StringBuilder();
        int testCases = in.nextInt();

        for (int testCase = 1; testCase <= testCases; testCase++) {
            targetX = in.nextInt();
            targetY = in.nextInt();

            if (targetX % 2 == targetY % 2) {
                output.append("Case #").append(testCase).append(": IMPOSSIBLE\n");
                continue;
            }

            found = 0;
            result = new int[10];
            for (int length = 1; length <= 10; length++) {
                findPath(0, new int[length], length);
                if (found == 1) break;
            }

            if (result[0] == 0) {
                output.append("Case #").append(testCase).append(": IMPOSSIBLE\n");
            } else {
                StringBuilder path = new StringBuilder();
                for (int direction : result) {
                    switch (direction) {
                        case 1 -> path.append('S');
                        case 2 -> path.append('N');
                        case 3 -> path.append('W');
                        case 4 -> path.append('E');
                    }
                }
                output.append("Case #").append(testCase).append(": ").append(path).append("\n");
            }
        }
        System.out.print(output);
    }
}

class FastReader {
    private final byte[] buffer = new byte[2048];
    private int index, total;
    private final InputStream input;

    FastReader(InputStream inputStream) {
        this.input = inputStream;
    }

    private int scan() throws IOException {
        if (index >= total) {
            index = 0;
            total = input.read(buffer);
            if (total <= 0) return -1;
        }
        return buffer[index++];
    }

    String next() throws IOException {
        int c;
        while ((c = scan()) <= 32);
        StringBuilder sb = new StringBuilder();
        do {
            sb.append((char) c);
        } while ((c = scan()) > 32);
        return sb.toString();
    }

    int nextInt() throws IOException {
        int c, value = 0;
        while ((c = scan()) <= 32);
        boolean negative = c == '-';
        if (c == '-' || c == '+') c = scan();
        do {
            value = (value << 3) + (value << 1) + (c & 15);
        } while ((c = scan()) >= '0' && c <= '9');
        return negative ? -value : value;
    }

    long nextLong() throws IOException {
        int c;
        long value = 0;
        while ((c = scan()) <= 32);
        boolean negative = c == '-';
        if (c == '-' || c == '+') c = scan();
        do {
            value = (value << 3) + (value << 1) + (c & 15);
        } while ((c = scan()) >= '0' && c <= '9');
        return negative ? -value : value;
    }
}