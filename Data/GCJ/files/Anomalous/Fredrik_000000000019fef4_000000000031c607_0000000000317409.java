import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCases = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= testCases; i++) {
            String line = scanner.nextLine();
            int[] coordinates = extractCoordinates(line);
            String moves = extractMoves(line);

            int x1 = coordinates[0];
            int y1 = coordinates[1];
            int x2 = 0;
            int y2 = 0;

            if (x1 == x2 && y1 == y2) {
                System.out.printf("Case %d: %d%n", i, 0);
                continue;
            }

            int minutes = 1;
            for (char move : moves.toCharArray()) {
                switch (move) {
                    case 'S' -> y1--;
                    case 'N' -> y1++;
                }

                if (x1 > x2) {
                    x2++;
                } else if (x1 < x2) {
                    x2--;
                } else if (y1 > y2) {
                    y2++;
                } else if (y1 < y2) {
                    y2--;
                }

                if (x1 == x2 && y1 == y2) {
                    System.out.printf("Case %d: %d%n", i, minutes);
                    break;
                }
                minutes++;
            }

            if (x1 != x2 || y1 != y2) {
                System.out.printf("Case %d: IMPOSSIBLE%n", i);
            }
        }
    }

    private static int[] extractCoordinates(String line) {
        String[] parts = line.split(" ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    private static String extractMoves(String line) {
        String[] parts = line.split(" ");
        return parts[parts.length - 1];
    }
}