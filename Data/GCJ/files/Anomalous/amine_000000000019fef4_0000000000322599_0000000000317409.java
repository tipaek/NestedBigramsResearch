import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int i = 1; i <= T; ++i) {
            String response = "IMPOSSIBLE";
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String m = scanner.next();
            scanner.nextLine();

            int[] initialPosition = {x, y};
            int[][] positions = new int[m.length() + 1][2];
            positions[0] = initialPosition.clone();

            for (int j = 0; j < m.length(); j++) {
                char move = m.charAt(j);
                switch (move) {
                    case 'S' -> y--;
                    case 'N' -> y++;
                    case 'E' -> x++;
                    case 'W' -> x--;
                }
                positions[j + 1] = new int[]{x, y};
            }

            int minimumTime = Integer.MAX_VALUE;

            for (int j = 0; j < positions.length; j++) {
                int distance = Math.abs(positions[j][0]) + Math.abs(positions[j][1]);
                if (distance <= j) {
                    minimumTime = Math.min(minimumTime, j);
                }
            }

            if (minimumTime <= m.length()) {
                response = Integer.toString(minimumTime);
            }

            System.out.println("Case #" + i + ": " + response);
        }

        scanner.close();
    }
}