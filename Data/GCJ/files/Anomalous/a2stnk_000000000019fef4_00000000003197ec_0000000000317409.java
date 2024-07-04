import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {
                System.out.printf("Case #%d: ", caseNumber);
                processTestCase(scanner);
            }
        }
    }

    private static void processTestCase(Scanner scanner) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        char[] movements = scanner.next().toCharArray();

        for (int step = 0; step < movements.length; step++) {
            switch (movements[step]) {
                case 'N' -> y++;
                case 'S' -> y--;
                case 'E' -> x++;
                case 'W' -> x--;
            }
            if (Math.abs(x) + Math.abs(y) <= step + 1) {
                System.out.println(step + 1);
                return;
            }
        }
        System.out.println("IMPOSSIBLE");
    }
}