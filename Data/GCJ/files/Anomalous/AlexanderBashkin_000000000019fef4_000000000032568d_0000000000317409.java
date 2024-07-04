import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    private static final int OFFSET = 1002;
    private static final int GRID_SIZE = OFFSET * 3;
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        new Solution().execute();
    }

    private void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(reader.readLine());

        for (int t = 1; t <= testCases; t++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            String path = tokenizer.nextToken();
            processTestCase(t, x, y, path);
        }
    }

    private void processTestCase(int testCaseNumber, int startX, int startY, String path) {
        Map<Coordinate, List<Integer>> pathMap = new HashMap<>();
        int x = startX;
        int y = startY;

        for (int i = 0; i < path.length(); i++) {
            char direction = path.charAt(i);
            switch (direction) {
                case 'S' -> y--;
                case 'N' -> y++;
                case 'W' -> x--;
                case 'E' -> x++;
            }
            pathMap.computeIfAbsent(new Coordinate(x, y), k -> new ArrayList<>()).add(i + 1);
        }

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(new Coordinate(0, 0));
        int[][] distances = new int[GRID_SIZE][GRID_SIZE];
        for (int[] row : distances) {
            Arrays.fill(row, -1);
        }
        distances[OFFSET][OFFSET] = 0;

        int minTime = -1;
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            int currentDistance = distances[current.x + OFFSET][current.y + OFFSET];

            if (pathMap.containsKey(current)) {
                List<Integer> times = pathMap.get(current);
                if (currentDistance <= times.get(times.size() - 1)) {
                    for (Integer time : times) {
                        if (currentDistance <= time && (minTime == -1 || time < minTime)) {
                            minTime = time;
                            break;
                        }
                    }
                }
            }

            if (currentDistance == path.length() || (minTime != -1 && currentDistance > minTime)) continue;

            for (int[] direction : DIRECTIONS) {
                Coordinate next = new Coordinate(current.x + direction[0], current.y + direction[1]);
                if (distances[next.x + OFFSET][next.y + OFFSET] == -1) {
                    distances[next.x + OFFSET][next.y + OFFSET] = currentDistance + 1;
                    queue.add(next);
                }
            }
        }

        int result = -1;
        for (Map.Entry<Coordinate, List<Integer>> entry : pathMap.entrySet()) {
            Coordinate coordinate = entry.getKey();
            int distance = distances[coordinate.x + OFFSET][coordinate.y + OFFSET];
            if (distance == -1) continue;
            for (Integer time : entry.getValue()) {
                if (distance <= time && (result == -1 || time < result)) {
                    result = time;
                }
            }
        }

        System.out.printf("Case #%d: %s%n", testCaseNumber, (result == -1 ? "IMPOSSIBLE" : result));
    }

    static class Coordinate {
        final int x, y;
        final String key;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            this.key = x + "#" + y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }
}