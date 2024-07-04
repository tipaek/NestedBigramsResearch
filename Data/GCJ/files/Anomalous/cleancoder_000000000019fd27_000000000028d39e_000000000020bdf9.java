import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Activity {
    int start;
    int end;
    String person;

    Activity(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class Solution {

    public static void solve(int testCaseNum, Activity[] activities) {
        List<Activity> activityList = Arrays.asList(activities);
        Activity[] sortedActivities = activityList.stream()
                .sorted((a1, a2) -> Integer.compare(a1.start, a2.start))
                .toArray(Activity[]::new);

        boolean impossible = false;
        for (int i = 0; i < sortedActivities.length; i++) {
            Activity currentActivity = sortedActivities[i];
            if (i > 0) {
                Activity lastActivity = sortedActivities[i - 1];
                if (i > 1) {
                    Activity lastLastActivity = sortedActivities[i - 2];
                    if (currentActivity.start < lastActivity.end) {
                        if (currentActivity.start < lastLastActivity.end) {
                            impossible = true;
                            break;
                        } else {
                            currentActivity.person = lastActivity.person.equals("J") ? "C" : "J";
                        }
                    } else {
                        currentActivity.person = lastActivity.person;
                    }
                } else {
                    if (currentActivity.start < lastActivity.end) {
                        currentActivity.person = lastActivity.person.equals("J") ? "C" : "J";
                    } else {
                        currentActivity.person = lastActivity.person;
                    }
                }
            } else {
                currentActivity.person = "C";
            }
        }

        String result = impossible ? "IMPOSSIBLE" : Arrays.stream(activities).map(a -> a.person).reduce("", String::concat);
        System.out.printf("Case #%d: %s%n", testCaseNum, result);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numTestCases = scanner.nextInt();
        for (int i = 1; i <= numTestCases; i++) {
            int numActivities = scanner.nextInt();
            Activity[] activities = new Activity[numActivities];
            for (int j = 0; j < numActivities; j++) {
                activities[j] = new Activity(scanner.nextInt(), scanner.nextInt());
            }
            solve(i, activities);
        }
        scanner.close();
    }
}