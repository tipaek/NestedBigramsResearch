import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void printPoint() {
            System.out.println(this.x + "," + this.y);
        }
    }

    public static ArrayList<Point> getPathArray(String str) {
        ArrayList<Point> paths = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            switch (ch) {
                case 'N' -> paths.add(new Point(0, 1));
                case 'S' -> paths.add(new Point(0, -1));
                case 'E' -> paths.add(new Point(1, 0));
                case 'W' -> paths.add(new Point(-1, 0));
            }
        }
        return paths;
    }

    public static ArrayList<Point> getCumulativePath(ArrayList<Point> pathPoints, int Px, int Py) {
        ArrayList<Point> cumulativePath = new ArrayList<>();
        Point currentPoint = new Point(Px, Py);
        for (Point p : pathPoints) {
            int x = currentPoint.x + p.x;
            int y = currentPoint.y + p.y;
            cumulativePath.add(new Point(x, y));
            currentPoint = new Point(x, y);
        }
        return cumulativePath;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        for (int tt = 1; tt <= t; tt++) {
            int Px = sc.nextInt();
            int Py = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            String str = sc.nextLine();

            ArrayList<Point> pathPoints = getPathArray(str);
            ArrayList<Point> finalPath = getCumulativePath(pathPoints, Px, Py);

            int timeLimit = pathPoints.size();
            boolean isPossible = false;
            int stepsRequired = timeLimit + 1;

            for (int i = 1; i <= timeLimit; i++) {
                stepsRequired = Math.abs(finalPath.get(i - 1).x) + Math.abs(finalPath.get(i - 1).y);
                if (stepsRequired <= i) {
                    stepsRequired = i;
                    isPossible = true;
                    break;
                }
            }

            if (isPossible) {
                System.out.println("Case #" + tt + ": " + stepsRequired);
            } else {
                System.out.println("Case #" + tt + ": IMPOSSIBLE");
            }
        }

        sc.close();
    }
}