import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        new Solution().solve();
    }

    public void solve() {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCases = scanner.nextInt();

        for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();
            String moves = scanner.next();
            char[] directions = moves.toCharArray();
            int moveCount = directions.length;
            int currentX = 0;
            int currentY = 0;

            // Simulate the movement of the cat
            for (char direction : directions) {
                switch (direction) {
                    case 'E' -> currentX++;
                    case 'W' -> currentX--;
                    case 'S' -> currentY++;
                    case 'N' -> currentY--;
                }
            }

            int minimumTime = Integer.MAX_VALUE;

            // Check from the end of the string to the beginning
            for (int i = moveCount - 1; i >= 0; i--) {
                int distance = Math.abs(targetX - currentX) + Math.abs(targetY - currentY);
                if (distance <= i + 1) {
                    minimumTime = Math.min(minimumTime, i + 1);
                }

                // Reverse the last move
                switch (directions[i]) {
                    case 'E' -> currentX--;
                    case 'W' -> currentX++;
                    case 'S' -> currentY--;
                    case 'N' -> currentY++;
                }
            }

            if (minimumTime == Integer.MAX_VALUE) {
                System.out.printf("Case #%d: IMPOSSIBLE\n", caseNumber);
            } else {
                System.out.printf("Case #%d: %d\n", caseNumber, minimumTime);
            }
        }
    }
}