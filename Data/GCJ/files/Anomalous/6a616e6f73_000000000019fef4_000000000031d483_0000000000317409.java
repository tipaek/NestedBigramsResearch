import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // scanner = example();
        // scanner = bigTest();

        int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            Input input = Input.fromScanner(scanner);
            printCase(t, solve(input));
        }
    }

    private static Scanner example() {
        return new Scanner("7\n" +
                "4 4 SSSS\n" +
                "3 0 SNSS\n" +
                "2 10 NSNNSN\n" +
                "0 1 S\n" +
                "2 7 SSSSSSSS\n" +
                "3 2 SSSW\n" +
                "4 0 NESW");
    }

    private static Scanner bigTest() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int T = 100;
        int A = 255;
        int maxLength = 500;
        sb.append(T).append('\n');
        for (int t = 0; t < T; t++) {
            sb.append(A).append('\n');
            for (int i = 0; i < A; i++) {
                int length = 1 + random.nextInt(maxLength);
                // TODO
            }
        }
        return new Scanner(sb.toString());
    }

    private static class Input {
        private final int X;
        private final int Y;
        private final String M;

        public Input(int X, int Y, String M) {
            this.X = X;
            this.Y = Y;
            this.M = M;
        }

        public static Input fromScanner(Scanner scanner) {
            int X = scanner.nextInt();
            int Y = scanner.nextInt();
            String M = scanner.next();
            return new Input(X, Y, M);
        }
    }

    private static void printCase(int t, String s) {
        System.out.printf("Case #%d: %s%n", t + 1, s);
    }

    private static int key(int x, int y, int index) {
        return (x + 1000 * y) * 1000 + index;
    }

    private static String solve(Input input) {
        return new Solver(input).solve();
    }

    static class Solver {
        private final Input input;
        private final int[][] points;
        private final Map<Integer, Integer> memo;

        Solver(Input input) {
            this.input = input;
            this.points = new int[input.M.length() + 1][2];
            this.memo = new HashMap<>();
            initializePoints();
        }

        private void initializePoints() {
            int x = input.X, y = input.Y;
            points[0][0] = x;
            points[0][1] = y;

            for (int i = 0; i < input.M.length(); i++) {
                switch (input.M.charAt(i)) {
                    case 'N' -> y++;
                    case 'S' -> y--;
                    case 'W' -> x--;
                    case 'E' -> x++;
                }
                points[i + 1][0] = x;
                points[i + 1][1] = y;
            }
        }

        private String solve() {
            int solution = solve(0, 0, 0);
            return solution == Integer.MAX_VALUE ? "IMPOSSIBLE" : Integer.toString(solution);
        }

        private int solve(int x, int y, int index) {
            int key = key(x, y, index);
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            int bestMove = findBestMove(x, y, index);
            memo.put(key, bestMove);
            return bestMove;
        }

        private int findBestMove(int x, int y, int index) {
            if (x == points[index][0] && y == points[index][1]) {
                return 0;
            }

            if (index == input.M.length()) return Integer.MAX_VALUE;

            int[][] moves = {
                    {0, 0}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}
            };

            int min = Integer.MAX_VALUE;

            for (int[] move : moves) {
                int x2 = x + move[0];
                int y2 = y + move[1];
                min = Math.min(min, solve(x2, y2, index + 1));
            }

            return min == Integer.MAX_VALUE ? min : min + 1;
        }
    }
}