import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Activity {
    public int start;
    public int end;
    public String person;

    public Activity(int start, int end) {
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
            Activity current = sortedActivities[i];
            if (i > 0) {
                Activity previous = sortedActivities[i - 1];
                if (i > 1) {
                    Activity prePrevious = sortedActivities[i - 2];
                    if (current.start < previous.end) {
                        if (current.start < prePrevious.end) {
                            impossible = true;
                            break;
                        } else {
                            current.person = previous.person.equals("J") ? "C" : "J";
                        }
                    } else {
                        current.person = previous.person;
                    }
                } else {
                    if (current.start < previous.end) {
                        current.person = previous.person.equals("J") ? "C" : "J";
                    } else {
                        current.person = previous.person;
                    }
                }
            } else {
                current.person = "J";
            }
        }

        String result = impossible ? "IMPOSSIBLE" : Arrays.stream(activities)
                .map(act -> act.person)
                .reduce("", String::concat);

        System.out.println("Case #" + testCaseNum + ": " + result);
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