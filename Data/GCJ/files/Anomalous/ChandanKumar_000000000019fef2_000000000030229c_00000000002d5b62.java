import java.util.Random;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int i = 1; i <= T; i++) {
            StringBuilder result = new StringBuilder();
            int jumpCount = 1;
            int X = sc.nextInt();
            int Y = sc.nextInt();
            int minDistance = Math.abs(X) + Math.abs(Y);
            int minJumpCount = calculateMinJumpCount(minDistance);
            int currentX = 0, currentY = 0;
            int failCount = 0;
            Random random = new Random();

            while (true) {
                int length = (int) Math.pow(2, jumpCount - 1);
                int direction = random.nextInt(4) + 1;

                switch (direction) {
                    case 1 -> {
                        result.append("N");
                        currentY += length;
                    }
                    case 2 -> {
                        result.append("E");
                        currentX += length;
                    }
                    case 3 -> {
                        result.append("S");
                        currentY -= length;
                    }
                    case 4 -> {
                        result.append("W");
                        currentX -= length;
                    }
                }

                if (currentX == X && currentY == Y) {
                    break;
                }

                if (Math.abs(currentX) + Math.abs(currentY) > minDistance || 
                    (X < 0 && currentX < X) || (X >= 0 && currentX > X) || 
                    (Y < 0 && currentY < Y) || (Y >= 0 && currentY > Y)) {
                    result.setLength(0);
                    currentX = 0;
                    currentY = 0;
                    jumpCount = 0;
                    failCount++;
                }

                if (failCount == 150040) {
                    result.setLength(0);
                    result.append("IMPOSSIBLE");
                    break;
                }
                jumpCount++;
            }

            System.out.printf("Case #%d: %s%n", i, result);
        }
    }

    private static int calculateMinJumpCount(int minDistance) {
        int i = 1;
        while (true) {
            if (minDistance <= Math.pow(2, i - 1)) {
                return i;
            }
            i++;
        }
    }
}