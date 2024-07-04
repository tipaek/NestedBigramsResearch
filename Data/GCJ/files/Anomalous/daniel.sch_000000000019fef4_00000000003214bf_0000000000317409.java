import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int numberOfInstances = Integer.parseInt(br.readLine());
            for (int i = 1; i <= numberOfInstances; i++) {
                String[] inputs = br.readLine().split(" ");
                int x = Integer.parseInt(inputs[0]);
                int y = Integer.parseInt(inputs[1]);
                char[] path = inputs[2].toCharArray();

                boolean reached = false;
                for (int j = 0; j < path.length; j++) {
                    switch (path[j]) {
                        case 'N' -> y++;
                        case 'S' -> y--;
                        case 'E' -> x++;
                        case 'W' -> x--;
                    }

                    int distance = Math.abs(x) + Math.abs(y);
                    if (distance <= j + 1) {
                        System.out.println("Case #" + i + ": " + (j + 1));
                        reached = true;
                        break;
                    }
                }

                if (!reached) {
                    System.out.println("Case #" + i + ": IMPOSSIBLE");
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}