import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    private static final int NORM = 1002;

    public static void main(String[] args) throws IOException {
        new Solution().process();
    }

    private void process() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(reader.readLine());
        for (int t = 1; t <= testCases; t++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());
            String path = tokenizer.nextToken();
            handleTestCase(t, x, y, path);
        }
    }

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private void handleTestCase(int caseNumber, int startX, int startY, String path) {
        Map<Coordinate, List<Integer>> pathMap = new HashMap<>();
        int x = startX, y = startY;

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
        int[][] distances = new int[NORM * 2][NORM * 2];
        for (int[] row : distances) {
            Arrays.fill(row, -1);
        }
        distances[NORM][NORM] = 0;

        int candidate = -1;
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            int currentDistance = distances[current.x + NORM][current.y + NORM];

            if (pathMap.containsKey(current)) {
                List<Integer> times = pathMap.get(current);
                if (currentDistance <= times.get(times.size() - 1)) {
                    for (Integer time : times) {
                        if (currentDistance <= time && (candidate == -1 || time < candidate)) {
                            candidate = time;
                            break;
                        }
                    }
                }
            }

            if (currentDistance == path.length() || (candidate != -1 && currentDistance > candidate)) continue;

            for (int[] direction : DIRECTIONS) {
                Coordinate next = new Coordinate(current.x + direction[0], current.y + direction[1]);
                if (distances[next.x + NORM][next.y + NORM] == -1) {
                    distances[next.x + NORM][next.y + NORM] = currentDistance + 1;
                    queue.add(next);
                }
            }
        }

        int result = -1;
        for (Map.Entry<Coordinate, List<Integer>> entry : pathMap.entrySet()) {
            Coordinate coordinate = entry.getKey();
            int distance = distances[coordinate.x + NORM][coordinate.y + NORM];
            if (distance == -1) continue;

            for (Integer time : entry.getValue()) {
                if (distance <= time && (result == -1 || time < result)) {
                    result = time;
                }
            }
        }

        System.out.printf("Case #%d: %s\n", caseNumber, (result == -1 ? "IMPOSSIBLE" : String.valueOf(result)));
    }

    static class Coordinate {
        final int x, y;
        final String representation;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            this.representation = x + "#" + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Coordinate that = (Coordinate) obj;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return representation.hashCode();
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }
}