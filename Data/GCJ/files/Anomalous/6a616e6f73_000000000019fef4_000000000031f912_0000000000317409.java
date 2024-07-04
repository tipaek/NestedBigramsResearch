import java.util.Random;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Uncomment to use example or bigTest methods
        // scanner = example();
        // scanner = bigTest();

        int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            Input input = Input.fromScanner(scanner);
            printCase(t, solve(input));
        }
    }

    private static Scanner example() {
        String exampleInput = "7\n" +
                "4 4 SSSS\n" +
                "3 0 SNSS\n" +
                "2 10 NSNNSN\n" +
                "0 1 S\n" +
                "2 7 SSSSSSSS\n" +
                "3 2 SSSW\n" +
                "4 0 NESW";
        return new Scanner(exampleInput);
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
                // TODO: Generate random directions if needed
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

    private static void printCase(int t, String result) {
        System.out.printf("Case #%d: %s%n", t + 1, result);
    }

    private static int key(int x, int y, int index) {
        return (x + 1000 * y) * 1000 + index;
    }

    private static String solve(Input input) {
        int x = input.X;
        int y = input.Y;
        String M = input.M;

        for (int index = 0; index < M.length(); index++) {
            char c = M.charAt(index);
            switch (c) {
                case 'N' -> y++;
                case 'S' -> y--;
                case 'W' -> x--;
                case 'E' -> x++;
            }

            int distance = Math.abs(x) + Math.abs(y);
            if (distance <= index + 1) {
                return Integer.toString(index + 1);
            }
        }

        return "IMPOSSIBLE";
    }
}