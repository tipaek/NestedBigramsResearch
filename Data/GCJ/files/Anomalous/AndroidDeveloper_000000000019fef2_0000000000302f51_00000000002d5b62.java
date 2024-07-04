import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int test = 0; test < tests; ++test) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();

            int border = 1_000_000_000;
            int maxSteps = 10;

            boolean isImpossible = true;

            char[] bestPath = new char[maxSteps];
            char[] currentPath = new char[maxSteps];
            int[] currentSet = new int[maxSteps];
            char[] directions = {'N', 'E', 'S', 'W'};

            Arrays.fill(currentSet, 0);

            int minSteps = Integer.MAX_VALUE;

            while (generateNextSet(currentSet, directions.length - 1, maxSteps)) {
                int sumX = 0;
                int sumY = 0;
                int step = 1;

                for (int i = 0; i < maxSteps; i++) {
                    currentPath[i] = directions[currentSet[i]];

                    switch (currentSet[i]) {
                        case 0 -> sumY += step; // N
                        case 1 -> sumX += step; // E
                        case 2 -> sumY -= step; // S
                        case 3 -> sumX -= step; // W
                    }

                    if (Math.abs(sumX) > border || Math.abs(sumY) > border) {
                        break;
                    }

                    if (sumX == targetX && sumY == targetY) {
                        if (i + 1 < minSteps) {
                            minSteps = i + 1;
                            System.arraycopy(currentPath, 0, bestPath, 0, minSteps);
                            isImpossible = false;
                        }
                    }
                    step <<= 1;
                }
            }

            if (isImpossible) {
                System.out.printf("Case #%d: IMPOSSIBLE%n", test + 1);
            } else {
                System.out.printf("Case #%d: %s%n", test + 1, new String(bestPath, 0, minSteps));
            }
        }
    }

    private static boolean generateNextSet(int[] array, int maxValue, int size) {
        int index = size - 1;
        while (index >= 0 && array[index] == maxValue) index--;
        if (index < 0) return false;
        array[index]++;
        if (index == size - 1) return true;
        Arrays.fill(array, index + 1, size, 0);
        return true;
    }
}