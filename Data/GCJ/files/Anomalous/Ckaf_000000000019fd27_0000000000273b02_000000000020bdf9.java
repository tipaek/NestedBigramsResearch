import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        for (int caseNum = 1; caseNum <= testCases; caseNum++) {
            int activityCount = scanner.nextInt();
            int[] startTimes = new int(activityCount);
            int[] endTimes = new int(activityCount);
            boolean[] assignedToCameron = new boolean(activityCount);

            for (int i = 0; i < activityCount; i++) {
                startTimes[i] = scanner.nextInt();
                endTimes[i] = scanner.nextInt();
            }

            StringBuilder schedule = new StringBuilder("J");
            boolean currentParentAssignment = false;
            assignedToCameron[0] = currentParentAssignment;

            outerLoop:
            for (int i = 1; i < activityCount; i++) {
                int overlapCount = 0;
                for (int j = 0; j < i; j++) {
                    if (startTimes[i] >= endTimes[j] || endTimes[i] <= startTimes[j]) {
                        assignedToCameron[i] = currentParentAssignment;
                    } else {
                        overlapCount++;
                        if (overlapCount > 1 && (assignedToCameron[j] != assignedToCameron[i])) {
                            overlapCount--;
                        } else {
                            currentParentAssignment = !assignedToCameron[j];
                            assignedToCameron[i] = currentParentAssignment;
                        }
                        if (overlapCount > 1) {
                            schedule.setLength(0);
                            schedule.append("IMPOSSIBLE");
                            break outerLoop;
                        }
                    }
                }
                if (schedule.length() > 0) {
                    schedule.append(assignedToCameron[i] ? "C" : "J");
                }
            }

            System.out.println("Case #" + caseNum + ": " + schedule);
        }
        scanner.close();
    }
}