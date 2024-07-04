import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
            int numberOfTestCases = sc.nextInt();
            for (int test = 1; test <= numberOfTestCases; test++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                String moves = sc.next();
                GridPoint pepperPosition = new GridPoint(x, y);
                GridPoint origin = new GridPoint(0, 0);

                int meetingPoint = findMeetingPoint(moves, pepperPosition, origin);
                if (meetingPoint == -1) {
                    System.out.printf("Case #%d: IMPOSSIBLE%n", test);
                } else {
                    System.out.printf("Case #%d: %d%n", test, meetingPoint);
                }
            }
        }
    }

    private static int findMeetingPoint(String moves, GridPoint pepperPosition, GridPoint origin) {
        int totalMoves = moves.length();
        for (int i = 1; i <= totalMoves; i++) {
            GridPoint currentPosition = calculatePosition(pepperPosition, moves.substring(0, i));
            int distance = Math.max(i, calculateDistance(origin, currentPosition));
            if (distance <= totalMoves && i >= distance) {
                return distance;
            }
        }
        return -1;
    }

    private static int calculateDistance(GridPoint p1, GridPoint p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    private static GridPoint calculatePosition(GridPoint start, String path) {
        GridPoint position = new GridPoint(start.getX(), start.getY());
        for (char direction : path.toCharArray()) {
            position.move(direction);
        }
        return position;
    }

    private static class GridPoint {
        private int x;
        private int y;

        public GridPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move(char direction) {
            switch (direction) {
                case 'N' -> y++;
                case 'S' -> y--;
                case 'W' -> x--;
                case 'E' -> x++;
                default -> throw new IllegalArgumentException("Invalid direction: " + direction);
            }
        }
    }
}