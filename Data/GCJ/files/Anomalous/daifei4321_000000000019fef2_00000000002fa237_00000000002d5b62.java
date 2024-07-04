import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int t = 0; t < testCases; t++) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();
            final int MAX_STEPS = 8;
            int[] directions = new int[MAX_STEPS];
            int stepsTaken = 1;
            boolean targetFound = false;

            while (true) {
                int currentX = 0;
                int currentY = 0;

                for (int i = 0, stepSize = 1; i < stepsTaken; i++, stepSize *= 2) {
                    switch (directions[i]) {
                        case 0 -> currentY -= stepSize; // South
                        case 1 -> currentX += stepSize; // East
                        case 2 -> currentY += stepSize; // North
                        case 3 -> currentX -= stepSize; // West
                    }
                }

                if (currentX == targetX && currentY == targetY) {
                    targetFound = true;
                    break;
                }

                int overflowIndex = 0;
                while (directions[overflowIndex] == 3) {
                    overflowIndex++;
                    if (overflowIndex == MAX_STEPS) {
                        break;
                    }
                }

                if (overflowIndex == MAX_STEPS) {
                    break;
                }

                for (int i = 0; i < overflowIndex; i++) {
                    directions[i] = 0;
                }

                if (overflowIndex == stepsTaken) {
                    stepsTaken++;
                } else {
                    directions[overflowIndex]++;
                }
            }

            if (!targetFound) {
                System.out.println("CASE #" + (t + 1) + ": IMPOSSIBLE");
            } else {
                String[] directionLabels = { "S", "E", "N", "W" };
                System.out.print("CASE #" + (t + 1) + ": ");
                for (int i = 0; i < stepsTaken; i++) {
                    System.out.print(directionLabels[directions[i]]);
                }
                System.out.println();
            }
        }

        scanner.close();
    }
}