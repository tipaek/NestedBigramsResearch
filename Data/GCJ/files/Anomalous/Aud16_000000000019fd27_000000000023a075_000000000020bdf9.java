import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCases = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= testCases; i++) {
            int n = scanner.nextInt();
            scanner.nextLine();
            List<int[]> tasks = new ArrayList<>();
            String[] assignments = new String[n];
            String result = null;

            for (int j = 0; j < n; j++) {
                String[] input = scanner.nextLine().split(" ");
                int[] task = {Integer.parseInt(input[0]), Integer.parseInt(input[1]), j};
                tasks.add(task);
            }

            tasks.sort(Comparator.comparingInt(a -> a[0]));

            int lastJEnd = 0;
            int lastCEnd = 0;
            boolean isCTurn = false;

            assignments[tasks.get(0)[2]] = "J";
            lastJEnd = tasks.get(0)[1];

            for (int k = 1; k < tasks.size(); k++) {
                int[] currentTask = tasks.get(k);
                if (currentTask[0] >= lastJEnd) {
                    assignments[currentTask[2]] = "J";
                    lastJEnd = currentTask[1];
                } else if (currentTask[0] >= lastCEnd) {
                    assignments[currentTask[2]] = "C";
                    lastCEnd = currentTask[1];
                } else {
                    result = "IMPOSSIBLE";
                    break;
                }
            }

            if (result == null) {
                result = String.join("", assignments);
            }

            System.out.println("Case #"1 + ": " + result);
        }
    }
}