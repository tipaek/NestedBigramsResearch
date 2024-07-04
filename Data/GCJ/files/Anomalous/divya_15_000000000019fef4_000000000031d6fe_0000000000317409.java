import java.util.Scanner;

public class GFG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {
            int c = scanner.nextInt();
            int r = scanner.nextInt();
            String directions = scanner.next();
            int steps = 0;
            boolean reached = false;

            System.out.print("Case #" + caseNumber + ": ");

            for (char direction : directions.toCharArray()) {
                steps++;
                switch (direction) {
                    case 'S' -> r--;
                    case 'N' -> r++;
                    case 'E' -> c++;
                    case 'W' -> c--;
                }

                if (Math.abs(r) + Math.abs(c) <= steps) {
                    reached = true;
                    break;
                }
            }

            if (reached) {
                System.out.println(steps);
            } else {
                System.out.println("IMPOSSIBLE");
            }
        }
    }
}