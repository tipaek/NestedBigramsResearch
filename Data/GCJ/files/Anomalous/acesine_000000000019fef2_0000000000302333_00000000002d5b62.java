import java.util.Scanner;

public class Solution {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Solution().run();
    }

    private void run() {
        int testCases = scanner.nextInt();
        for (int t = 1; t <= testCases; t++) {
            solve(t, scanner.nextLong(), scanner.nextLong());
        }
    }

    private void solve(int caseNumber, long targetX, long targetY) {
        long x = Math.abs(targetX);
        long y = Math.abs(targetY);
        StringBuilder path = new StringBuilder();

        int[] xBits = new int[35];
        int[] yBits = new int[35];
        initializeBits(x, y, xBits, yBits);

        if (isImpossible(xBits, yBits)) {
            printResult(caseNumber, "IMPOSSIBLE");
            return;
        }

        adjustBits(xBits, yBits);

        buildPath(xBits, yBits, path);

        adjustPathForNegativeCoordinates(targetX, targetY, path);

        verifyPath(path.toString(), targetX, targetY);
        printResult(caseNumber, path.toString());
    }

    private void initializeBits(long x, long y, int[] xBits, int[] yBits) {
        for (int i = 0; i < 35; i++) {
            long mask = 1L << i;
            if ((x & mask) != 0) xBits[i] = 1;
            if ((y & mask) != 0) yBits[i] = 1;
        }
    }

    private boolean isImpossible(int[] xBits, int[] yBits) {
        return (xBits[0] == 1 && yBits[0] == 1) || (xBits[0] == 0 && yBits[0] == 0);
    }

    private void adjustBits(int[] xBits, int[] yBits) {
        for (int i = 0; i < 35; i++) {
            if (xBits[i] > 1) {
                xBits[i + 1] += xBits[i] / 2;
                xBits[i] %= 2;
            }
            if (yBits[i] > 1) {
                yBits[i + 1] += yBits[i] / 2;
                yBits[i] %= 2;
            }
            handleZeroBits(xBits, yBits, i);
            handleBothBitsSet(xBits, yBits, i);
        }
    }

    private void handleZeroBits(int[] xBits, int[] yBits, int i) {
        if (xBits[i] == 0 && yBits[i] == 0) {
            boolean finish = true;
            for (int j = i; j < 35; j++) {
                if (xBits[j] != 0 || yBits[j] != 0) {
                    finish = false;
                    break;
                }
            }
            if (finish) return;
            if (xBits[i - 1] == 1) {
                xBits[i] = 1;
                xBits[i - 1] = -1;
            } else {
                yBits[i] = 1;
                yBits[i - 1] = -1;
            }
        }
    }

    private void handleBothBitsSet(int[] xBits, int[] yBits, int i) {
        if (xBits[i] != 0 && yBits[i] != 0) {
            if (xBits[i - 1] == 1) {
                xBits[i + 1]++;
                xBits[i - 1] = -1;
                xBits[i] = 0;
            } else {
                yBits[i + 1]++;
                yBits[i - 1] = -1;
                yBits[i] = 0;
            }
        }
    }

    private void buildPath(int[] xBits, int[] yBits, StringBuilder path) {
        for (int i = 0; i < xBits.length; i++) {
            if (xBits[i] == 0 && yBits[i] == 0) break;
            if (xBits[i] != 0) path.append(xBits[i] == 1 ? 'E' : 'W');
            else path.append(yBits[i] == 1 ? 'N' : 'S');
        }
    }

    private void adjustPathForNegativeCoordinates(long targetX, long targetY, StringBuilder path) {
        if (targetX < 0) {
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == 'E') path.setCharAt(i, 'W');
                else if (path.charAt(i) == 'W') path.setCharAt(i, 'E');
            }
        }
        if (targetY < 0) {
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == 'N') path.setCharAt(i, 'S');
                else if (path.charAt(i) == 'S') path.setCharAt(i, 'N');
            }
        }
    }

    private void verifyPath(String path, long x, long y) {
        long currentX = 0;
        long currentY = 0;
        long step = 1;

        for (char direction : path.toCharArray()) {
            switch (direction) {
                case 'E' -> currentX += step;
                case 'W' -> currentX -= step;
                case 'N' -> currentY += step;
                case 'S' -> currentY -= step;
            }
            step *= 2;
        }

        if (currentX != x || currentY != y) {
            throw new RuntimeException("Verification failed for coordinates: " + x + ", " + y);
        }
    }

    private void printResult(int caseNumber, String result) {
        System.out.printf("Case #%d: %s%n", caseNumber, result);
    }
}