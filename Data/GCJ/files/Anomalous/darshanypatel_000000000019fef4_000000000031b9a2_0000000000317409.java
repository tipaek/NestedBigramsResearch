import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int testCases = Integer.parseInt(reader.readLine());

        for (int caseNumber = 1; caseNumber <= testCases; caseNumber++) {
            String[] input = reader.readLine().split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            String path = input[2];

            if (x == 0 && y == 0) {
                System.out.println("Case #" + caseNumber + ": 0");
                continue;
            }

            boolean reached = false;
            for (int i = 0; i < path.length(); i++) {
                char direction = path.charAt(i);
                switch (direction) {
                    case 'S' -> y--;
                    case 'N' -> y++;
                    case 'E' -> x++;
                    case 'W' -> x--;
                }

                int stepsRequired = Math.abs(x) + Math.abs(y);
                if (stepsRequired <= i + 1) {
                    System.out.println("Case #" + caseNumber + ": " + (i + 1));
                    reached = true;
                    break;
                }
            }

            if (!reached) {
                System.out.println("Case #" + caseNumber + ": IMPOSSIBLE");
            }
        }
    }
}