import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        for (int i = 0; i < testCases; i++) {
            processCase(i + 1, scanner);
        }
    }

    private static void processCase(int caseId, Scanner scanner) {
        final int OFFSET = 1000;
        final int SIZE = 2001;
        final int MAX_TIME = 5000;
        final int MIN_TIME = -5000;

        int[][] playerArrival = new int[SIZE][SIZE];
        int[][] meArrival = new int[SIZE][SIZE];

        int startX = scanner.nextInt();
        int startY = scanner.nextInt();
        String path = scanner.next();

        for (int x = -OFFSET; x <= OFFSET; x++) {
            for (int y = -OFFSET; y <= OFFSET; y++) {
                meArrival[OFFSET + x][OFFSET + y] = MAX_TIME;
                playerArrival[OFFSET + x][OFFSET + y] = MIN_TIME;
            }
        }

        meArrival[OFFSET - startX][OFFSET - startY] = 0;

        int playerX = 0;
        int playerY = 0;
        playerArrival[OFFSET][OFFSET] = 0;
        for (int i = 0; i < path.length(); i++) {
            switch (path.charAt(i)) {
                case 'N' -> playerY++;
                case 'S' -> playerY--;
                case 'E' -> playerX++;
                case 'W' -> playerX--;
                default -> throw new RuntimeException("Invalid direction");
            }
            playerArrival[OFFSET + playerX][OFFSET + playerY] = i + 1;
        }

        boolean[][] enqueued = new boolean[SIZE][SIZE];
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(OFFSET - startX, OFFSET - startY));
        enqueued[OFFSET - startX][OFFSET - startY] = true;

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] dir : directions) {
                int newX = point.x + dir[0];
                int newY = point.y + dir[1];
                if (isValid(newX, newY, SIZE) && !enqueued[newX][newY]) {
                    queue.add(new Point(newX, newY));
                    enqueued[newX][newY] = true;
                    meArrival[newX][newY] = Math.min(meArrival[newX][newY], meArrival[point.x][point.y] + 1);
                }
            }
        }

        int minTime = findMinTime(meArrival, playerArrival, SIZE);
        if (minTime == Integer.MAX_VALUE) {
            System.out.println("Case #" + caseId + ": IMPOSSIBLE");
        } else {
            System.out.println("Case #" + caseId + ": " + minTime);
        }
    }

    private static boolean isValid(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private static int findMinTime(int[][] meArrival, int[][] playerArrival, int size) {
        int minTime = Integer.MAX_VALUE;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (meArrival[x][y] <= playerArrival[x][y]) {
                    minTime = Math.min(minTime, playerArrival[x][y]);
                }
            }
        }
        return minTime;
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}