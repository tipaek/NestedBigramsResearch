import java.util.Scanner;

public class Solution {
    private static final Scanner scanner = new Scanner(System.in);

    private static class TourStop {
        private final int x;
        private final int y;
        private final int step;

        private TourStop(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }

        private TourStop(Point position, int step) {
            this(position.x, position.y, step);
        }

        private int getDistance() {
            return Math.abs(x) + Math.abs(y);
        }
    }

    private static class Point {
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        int numberOfCases = scanner.nextInt();
        for (int i = 0; i < numberOfCases; i++) {
            int startX = scanner.nextInt();
            int startY = scanner.nextInt();
            String path = scanner.next();
            processCase(i + 1, startX, startY, path);
        }
    }

    private static void processCase(int caseNumber, int startX, int startY, String path) {
        Point currentPosition = new Point(startX, startY);
        TourStop[] tourStops = new TourStop[path.length() + 1];
        tourStops[0] = new TourStop(currentPosition, 0);

        for (int i = 0; i < path.length(); i++) {
            move(currentPosition, path.charAt(i));
            tourStops[i + 1] = new TourStop(currentPosition, i + 1);
        }

        for (int i = 0; i < tourStops.length; i++) {
            if (tourStops[i].getDistance() <= i) {
                printResult(caseNumber, Integer.toString(i));
                return;
            }
        }
        printResult(caseNumber, "IMPOSSIBLE");
    }

    private static void move(Point position, char direction) {
        switch (direction) {
            case 'N' -> position.y++;
            case 'S' -> position.y--;
            case 'E' -> position.x++;
            case 'W' -> position.x--;
        }
    }

    private static void printResult(int caseNumber, String result) {
        System.out.printf("Case #%d: %s%n", caseNumber, result);
    }
}