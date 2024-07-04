import java.util.Scanner;

public class Solution {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfCases = in.nextInt();
        for (int i = 1; i <= numberOfCases; i++) {
            processCase(i);
        }
    }

    private static void processCase(int caseNumber) {
        int[] position = {in.nextInt(), in.nextInt()};
        String path = in.next();
        System.out.printf("Case #%d: ", caseNumber);

        for (int i = 0; i < path.length(); i++) {
            if (isReachable(position, i)) {
                System.out.println(i);
                return;
            }
            updatePosition(position, path.charAt(i));
        }

        if (isReachable(position, path.length())) {
            System.out.println(path.length());
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }

    private static boolean isReachable(int[] position, int distance) {
        return distance >= (Math.abs(position[0]) + Math.abs(position[1]));
    }

    private static void updatePosition(int[] position, char direction) {
        switch (direction) {
            case 'N' -> position[1]++;
            case 'S' -> position[1]--;
            case 'E' -> position[0]++;
            case 'W' -> position[0]--;
        }
    }
}