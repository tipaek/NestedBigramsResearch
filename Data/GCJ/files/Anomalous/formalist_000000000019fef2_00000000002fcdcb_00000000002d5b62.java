import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(1, in, out);
        out.close();
    }

    static class Task {
        private static final int MAX_LEN = 8;
        private int targetX, targetY;
        private int[] path;
        private StringBuilder result;
        private int minPathLength;

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int testCases = in.nextInt();
            for (int caseNum = 1; caseNum <= testCases; caseNum++) {
                targetX = in.nextInt();
                targetY = in.nextInt();
                path = new int[MAX_LEN];
                minPathLength = -1;
                out.println("Case #" + caseNum + ": " + findPath());
            }
        }

        private String findPath() {
            searchPath(0, 0, 0);
            return minPathLength == -1 ? "IMPOSSIBLE" : result.toString();
        }

        private void searchPath(int currentX, int currentY, int step) {
            if (currentX == targetX && currentY == targetY) {
                updateResult(step);
            } else if (step < MAX_LEN) {
                for (int direction = 0; direction < 4; direction++) {
                    int moveDistance = 1 << step;
                    path[step] = direction;
                    switch (direction) {
                        case 0 -> searchPath(currentX, currentY + moveDistance, step + 1); // North
                        case 1 -> searchPath(currentX + moveDistance, currentY, step + 1); // East
                        case 2 -> searchPath(currentX, currentY - moveDistance, step + 1); // South
                        case 3 -> searchPath(currentX - moveDistance, currentY, step + 1); // West
                    }
                }
            }
        }

        private void updateResult(int steps) {
            if (minPathLength == -1 || steps < minPathLength) {
                minPathLength = steps;
                result = new StringBuilder();
                for (int i = 0; i < steps; i++) {
                    result.append(switch (path[i]) {
                        case 0 -> "N";
                        case 1 -> "E";
                        case 2 -> "S";
                        case 3 -> "W";
                        default -> throw new IllegalStateException("Unexpected value: " + path[i]);
                    });
                }
            }
        }
    }

    static class InputReader {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}