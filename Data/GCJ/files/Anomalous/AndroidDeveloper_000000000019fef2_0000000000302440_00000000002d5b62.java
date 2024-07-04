import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int testCase = 0; testCase < testCases; ++testCase) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();

            final int BORDER = 1_000;
            final int MAX_STEPS = 15;

            boolean isImpossible = true;
            char[] result = new char[MAX_STEPS];
            char[] currentResult = new char[MAX_STEPS];
            int[] currentSet = new int[MAX_STEPS];
            char[] directions = {'N', 'E', 'S', 'W'};
            Arrays.fill(currentSet, 0);

            int minimumSteps = Integer.MAX_VALUE;

            while (getNextSet(currentSet, directions.length - 1, MAX_STEPS)) {
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
                        if (i < minimumSteps) {
                            minimumSteps = i + 1;
                            System.arraycopy(currentResult, 0, result, 0, minimumSteps);
                            isImpossible = false;
                        }
                    }
                    step <<= 1;
                }
            }

            if (isImpossible) {
                System.out.printf("Case #%d: IMPOSSIBLE%n", testCase + 1);
            } else {
                String path = new String(Arrays.copyOfRange(result, 0, minimumSteps));
                System.out.printf("Case #%d: %s%n", testCase + 1, path);
            }
        }
    }

    private static boolean getNextSet(int[] array, int maxValue, int size) {
        int index = size - 1;
        while (index >= 0 && array[index] == maxValue) index--;
        if (index < 0) return false;
        array[index]++;
        if (index == size - 1) return true;
        Arrays.fill(array, index + 1, size, 0);
        return true;
    }
}