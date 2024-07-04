import java.util.*;

public abstract class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int i = 1; i <= testCases; i++) {
            int n = scanner.nextInt();
            int[] overlaps = new int[1440];
            Map<Integer, Integer> beginTimes = new TreeMap<>();
            Map<Integer, Integer> endTimes = new HashMap<>();

            for (int j = 0; j < n; j++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();
                beginTimes.put(start, j);
                endTimes.put(j, end);

                for (int k = start; k < end; k++) {
                    overlaps[k]++;
                }
            }

            List<Integer> overlapList = Arrays.asList(Arrays.stream(overlaps).boxed().toArray(Integer[]::new));
            String result;

            if (overlapList.contains(3)) {
                result = "IMPOSSIBLE";
            } else if (!overlapList.contains(2)) {
                result = "J".repeat(n);
            } else {
                Map<Integer, String> assignments = new HashMap<>();
                int lastEndTime = -1;
                String lastAssigned = "J";

                for (int startTime : beginTimes.keySet()) {
                    int taskIndex = beginTimes.get(startTime);

                    if (assignments.isEmpty()) {
                        assignments.put(taskIndex, "J");
                        lastEndTime = endTimes.get(taskIndex);
                    } else {
                        if (startTime >= lastEndTime) {
                            assignments.put(taskIndex, lastAssigned);
                        } else {
                            lastAssigned = lastAssigned.equals("J") ? "C" : "J";
                            assignments.put(taskIndex, lastAssigned);
                        }
                        lastEndTime = endTimes.get(taskIndex);
                    }
                }

                StringBuilder solutionBuilder = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    solutionBuilder.append(assignments.get(j));
                }
                result = solutionBuilder.toString();
            }

            System.out.println("Case #" + i + ": " + result);
        }

        scanner.close();
    }
}