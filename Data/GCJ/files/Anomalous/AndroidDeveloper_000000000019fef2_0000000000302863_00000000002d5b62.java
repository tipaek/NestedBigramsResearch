import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int test = 0; test < tests; ++test) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();

            final int BORDER = 100;
            final int MAX_STEPS = 8;

            boolean impossible = true;

            char[] result = new char[MAX_STEPS];
            char[] currentResult = new char[MAX_STEPS];
            int[] currentSet = new int[MAX_STEPS];
            char[] directions = {'N', 'E', 'S', 'W'};

            Arrays.fill(currentSet, 0);

            int bestSteps = Integer.MAX_VALUE;

            while (generateNextSet(currentSet, directions.length - 1, MAX_STEPS)) {
                int sumX = 0;
                int sumY = 0;
                int step = 1;

                for (int i = 0; i < MAX_STEPS; i++) {
                    currentResult[i] = directions[currentSet[i]];

                    switch (currentSet[i]) {
                        case 0 -> sumY += step; // N
                        case 1 -> sumX += step; // E
                        case 2 -> sumY -= step; // S
                        case 3 -> sumX -= step; // W
                    }

                    if (Math.abs(sumX) > BORDER || Math.abs(sumY) > BORDER) {
                        break;
                    }

                    if (sumX == targetX && sumY == targetY) {
                        if (i + 1 < bestSteps) {
                            bestSteps = i + 1;
                            System.arraycopy(currentResult, 0, result, 0, bestSteps);
                            impossible = false;
                        }
                    }

                    step <<= 1;
                }
            }

            if (impossible) {
                System.out.printf("Case #%d: IMPOSSIBLE%n", test + 1);
            } else {
                System.out.printf("Case #%d: %s%n", test + 1, new String(result, 0, bestSteps));
            }
        }
    }

    private static boolean generateNextSet(int[] array, int maxValue, int size) {
        int index = size - 1;

        while (index >= 0 && array[index] == maxValue) {
            index--;
        }

        if (index < 0) {
            return false;
        }

        array[index]++;

        for (int i = index + 1; i < size; i++) {
            array[i] = 0;
        }

        return true;
    }
}