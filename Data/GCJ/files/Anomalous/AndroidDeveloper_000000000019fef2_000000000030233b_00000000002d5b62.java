import java.util.*;

public class Solution {

    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int test = 0; test < tests; ++test) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();

            int border = 1000;
            int N = 7;

            boolean impossible = true;
            char[] result = new char[N];
            char[] currentRes = new char[N];
            int[] currentSet = new int[N];
            char[] directions = {'N', 'E', 'S', 'W'};

            Arrays.fill(currentSet, 0);
            int bestN = Integer.MAX_VALUE;

            while (generateNextSet(currentSet, directions.length - 1, N)) {
                int sumX = 0;
                int sumY = 0;
                int step = 1;

                for (int i = 0; i < N; i++) {
                    currentRes[i] = directions[currentSet[i]];

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
                        if (i < bestN) {
                            bestN = i + 1;
                            System.arraycopy(currentRes, 0, result, 0, bestN);
                            impossible = false;
                        }
                    }
                    step <<= 1;
                }
            }

            if (impossible) {
                System.out.printf("Case #%d: IMPOSSIBLE%n", test + 1);
            } else {
                System.out.printf("Case #%d: %s%n", test + 1, new String(result, 0, bestN));
            }
        }
    }

    public static boolean generateNextSet(int[] set, int maxVal, int size) {
        int j = size - 1;
        while (j >= 0 && set[j] == maxVal) j--;
        if (j < 0) return false;
        set[j]++;
        Arrays.fill(set, j + 1, size, 0);
        return true;
    }
}