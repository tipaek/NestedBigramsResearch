import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

    private static final boolean DEBUG = false;
    private static int count = Integer.MAX_VALUE;

    public static String solve(int[] nums, char[] moves) {
        count = Integer.MAX_VALUE;
        if (nums[0] == 0 && nums[1] == 0) return "0";

        int startX = nums[0], startY = nums[1];
        int minX = 0, maxX = 0, minY = 0, maxY = 0;

        for (char move : moves) {
            switch (move) {
                case 'S' -> nums[1]--;
                case 'N' -> nums[1]++;
                case 'W' -> nums[0]--;
                case 'E' -> nums[0]++;
            }
            minX = Math.min(minX, nums[0]);
            maxX = Math.max(maxX, nums[0]);
            minY = Math.min(minY, nums[1]);
            maxY = Math.max(maxY, nums[1]);
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        int[][] graph = new int[height][width];
        int step = 0;
        int currentX = startX - minX, currentY = startY - minY;
        graph[currentY][currentX] = step;

        for (char move : moves) {
            switch (move) {
                case 'S' -> currentY--;
                case 'N' -> currentY++;
                case 'W' -> currentX--;
                case 'E' -> currentX++;
            }
            step++;
            graph[currentY][currentX] = step;
        }

        int targetX = -minX, targetY = -minY;

        if (graph[targetY][targetX] > 0) {
            count = Math.min(count, graph[targetY][targetX]);
        }

        bfs(targetY, targetX, graph);
        return count == Integer.MAX_VALUE ? "IMPOSSIBLE" : String.valueOf(count);
    }

    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private static void bfs(int startY, int startX, int[][] graph) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});
        graph[startY][startX] = -1;
        int step = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] current = queue.poll();
                for (int[] direction : DIRECTIONS) {
                    int newY = current[0] + direction[0], newX = current[1] + direction[1];
                    if (newY < 0 || newY >= graph.length || newX < 0 || newX >= graph[0].length || graph[newY][newX] < 0)
                        continue;
                    if (graph[newY][newX] >= step) {
                        count = Math.min(count, graph[newY][newX]);
                    }
                    graph[newY][newX] = -step;
                    queue.add(new int[]{newY, newX});
                }
            }
            step++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        long beginTime = System.nanoTime();
        InputStream is = DEBUG ? new FileInputStream("src/main/resources/codejam/y2020/round1c/t1/1.in") : System.in;
        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(is)))) {
            int testCount = scanner.nextInt();
            for (int testNumber = 1; testNumber <= testCount; testNumber++) {
                int[] nums = {scanner.nextInt(), scanner.nextInt()};
                String moves = scanner.next();
                System.out.println("Case #" + testNumber + ": " + solve(nums, moves.toCharArray()));
            }
        }
        System.err.println("Done in " + ((System.nanoTime() - beginTime) / 1e9) + " seconds.");
    }
}