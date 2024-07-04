import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {

    private static final char[] DIRECTIONS = {'N', 'S', 'W', 'E'};
    private static char[] path;
    private static String bestPath;
    private static int minSteps;
    private static boolean foundSolution;
    private static int targetX, targetY;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCases = scanner.nextInt();

        for (int i = 1; i <= testCases; i++) {
            path = new char[35];
            foundSolution = false;
            minSteps = 100;
            targetX = scanner.nextInt();
            targetY = scanner.nextInt();

            System.out.print("Case #" + i + ": ");
            findPath(0, 0, 0);

            if (!foundSolution) {
                System.out.println("IMPOSSIBLE");
            }
        }
    }

    private static boolean findPath(int currentX, int currentY, int step) {
        if (currentX == targetX && currentY == targetY) {
            foundSolution = true;
            if (minSteps > step) {
                bestPath = new String(path, 0, step);
                minSteps = step;
            }
        }

        if (foundSolution && minSteps <= step) {
            return true;
        }

        if (step > 33 || isOutOfBounds(currentX, currentY)) {
            return false;
        }

        for (char direction : DIRECTIONS) {
            int nextX = currentX, nextY = currentY;
            switch (direction) {
                case 'N' -> nextY += 1 << step;
                case 'S' -> nextY -= 1 << step;
                case 'W' -> nextX -= 1 << step;
                case 'E' -> nextX += 1 << step;
            }
            path[step] = direction;
            findPath(nextX, nextY, step + 1);
        }

        if (step == 0 && foundSolution) {
            System.out.println(bestPath);
        }

        return false;
    }

    private static boolean isOutOfBounds(int currentX, int currentY) {
        return Math.abs(currentY) >= Math.max(100, Math.abs(targetY)) || Math.abs(currentY - targetY) > Math.max(1000, Math.abs(targetY + currentY))
                || Math.abs(currentX) >= Math.max(100, Math.abs(targetX)) || Math.abs(currentX - targetX) > Math.max(1000, Math.abs(targetX + currentX));
    }
}