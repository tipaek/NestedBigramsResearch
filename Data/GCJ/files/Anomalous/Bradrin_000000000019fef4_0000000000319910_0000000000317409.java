import java.util.Scanner;

public class Solution {

    private static final int IMPOSSIBLE = Integer.MAX_VALUE / 2;

    private void solve(Scanner scan) {
        int sa = scan.nextInt();
        int sb = scan.nextInt();
        char[] moves = scan.next().toCharArray();
        int minSteps = IMPOSSIBLE;

        for (int targetX = sa - 1000; targetX <= sa + 1000; targetX++) {
            int currentX = sa;
            int currentY = sb;
            int targetY = 0;
            int steps = 0;

            for (char move : moves) {
                steps++;
                switch (move) {
                    case 'N' -> currentY++;
                    case 'S' -> currentY--;
                    case 'E' -> currentX++;
                    case 'W' -> currentX--;
                }

                if (currentX == targetX && currentY == targetY) {
                    minSteps = Math.min(minSteps, steps);
                    break;
                }

                if (targetX > currentX) targetX--;
                else if (targetX < currentX) targetX++;
                else if (targetY < currentY) targetY++;
                else targetY--;

                if (currentX == targetX && currentY == targetY) {
                    minSteps = Math.min(minSteps, steps);
                    break;
                }
            }
        }

        if (minSteps == IMPOSSIBLE) {
            System.out.println("IMPOSSIBLE");
        } else {
            System.out.println(minSteps);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int numberOfProblems = scan.nextInt();
        for (int problemIndex = 0; problemIndex < numberOfProblems; problemIndex++) {
            System.out.print("Case #" + (problemIndex + 1) + ": ");
            new Solution().solve(scan);
        }
        scan.close();
    }
}