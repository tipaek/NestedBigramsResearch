import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int t = scanner.nextInt();
            for (int i = 0; i < t; i++) {
                int xEast = scanner.nextInt();
                int yNorth = scanner.nextInt();
                String path = scanner.next();
                System.out.println("Case #" + (i + 1) + ": " + findPictureTime(xEast, yNorth, path));
            }
        }
    }

    private static String findPictureTime(int x, int y, String path) {
        int time = 0;
        boolean found = false;
        int minTime = 0;

        if (x == 0 && y == 0) return "0";

        for (char direction : path.toCharArray()) {
            time++;
            switch (direction) {
                case 'N' -> y++;
                case 'S' -> y--;
                case 'E' -> x++;
                case 'W' -> x--;
            }

            int result = checkAvailability(x, y, time);
            if (result >= 0) {
                if (!found) {
                    minTime = time;
                    found = true;
                } else if (time < minTime) {
                    minTime = time;
                }
            }
        }

        return found ? String.valueOf(minTime) : "IMPOSSIBLE";
    }

    private static int checkAvailability(int x, int y, int time) {
        return time - (Math.abs(x) + Math.abs(y));
    }
}