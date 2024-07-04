import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Scanner;

public class Solution {
    private static final String RESULT_PATTERN = "Case #{0}: {1}";

    private static String getSolution(final Scanner scanner) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        String sequence = scanner.next();

        for (int i = 0; i <= sequence.length(); i++) {
            if (x == 0 && y == 0) {
                return String.valueOf(i);
            }

            if (i == sequence.length()) {
                break;
            }

            char direction = sequence.charAt(i);
            switch (direction) {
                case 'N' -> y++;
                case 'S' -> y--;
                case 'E' -> x++;
                case 'W' -> x--;
            }

            if (x > 0) {
                x--;
            } else if (x < 0) {
                x++;
            } else {
                if (y > 0) {
                    y--;
                } else if (y < 0) {
                    y++;
                }
            }
        }

        return "IMPOSSIBLE";
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
//        Scanner scanner = new Scanner(new FileInputStream("A.in"));

        int testCases = scanner.nextInt();
        for (int i = 1; i <= testCases; i++) {
            String solution = getSolution(scanner);
            System.out.println(MessageFormat.format(RESULT_PATTERN, i, solution));
        }
    }

    private static String ask(final String question, final Scanner scanner) {
        System.out.println(question);
        return scanner.next();
    }
}