import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

    private static final boolean DEBUG = false;
    private static int minSteps = Integer.MAX_VALUE;

    public static String solve(int[] coordinates, char[] moves) {
        minSteps = Integer.MAX_VALUE;
        if (coordinates[0] == 0 && coordinates[1] == 0) return "";

        int startX = coordinates[0], startY = coordinates[1];
        int minX = startX, maxX = startX, minY = startY, maxY = startY;

        for (char move : moves) {
            switch (move) {
                case 'S' -> startY--;
                case 'N' -> startY++;
                case 'W' -> startX--;
                case 'E' -> startX++;
            }
            minX = Math.min(minX, startX);
            maxX = Math.max(maxX, startX);
            minY = Math.min(minY, startY);
            maxY = Math.max(maxY, startY);
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        int adjustedStartX = coordinates[0] - minX;
        int adjustedStartY = coordinates[1] - minY;
        int adjustedCurrentX = adjustedStartX;
        int adjustedCurrentY = adjustedStartY;

        int[][] grid = new int[height][width];
        grid[adjustedCurrentY][adjustedCurrentX] = 0;
        int step = 0;

        for (char move : moves) {
            switch (move) {
                case 'S' -> adjustedCurrentY--;
                case 'N' -> adjustedCurrentY++;
                case 'W' -> adjustedCurrentX--;
                case 'E' -> adjustedCurrentX++;
            }
            step++;
            grid[adjustedCurrentY][adjustedCurrentX] = step;
        }

        if (grid[adjustedStartY][adjustedStartX] > 0) {
            minSteps = Math.min(minSteps, grid[adjustedStartY][adjustedStartX]);
        }

        bfs(adjustedStartY, adjustedStartX, grid);
        return minSteps == Integer.MAX_VALUE ? "IMPOSSIBLE" : String.valueOf(minSteps);
    }

    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private static void bfs(int startY, int startX, int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});
        grid[startY][startX] = -1;
        int step = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] current = queue.poll();
                for (int[] dir : DIRECTIONS) {
                    int newY = current[0] + dir[0];
                    int newX = current[1] + dir[1];
                    if (newY < 0 || newY >= grid.length || newX < 0 || newX >= grid[0].length || grid[newY][newX] < 0)
                        continue;
                    if (grid[newY][newX] >= step) {
                        minSteps = Math.min(minSteps, grid[newY][newX]);
                    }
                    grid[newY][newX] = -step;
                    queue.add(new int[]{newY, newX});
                }
            }
            step++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.nanoTime();
        InputStream inputStream = DEBUG ? new FileInputStream("src/main/resources/codejam/y2020/round1c/t1/1.in") : System.in;
        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(inputStream)))) {
            int testCases = scanner.nextInt();
            for (int testCase = 1; testCase <= testCases; testCase++) {
                int[] coordinates = {scanner.nextInt(), scanner.nextInt()};
                String moves = scanner.next();
                System.out.println("Case #" + testCase + ": " + solve(coordinates, moves.toCharArray()));
            }
        }
        System.err.println("Done in " + ((System.nanoTime() - startTime) / 1e9) + " seconds.");
    }
}