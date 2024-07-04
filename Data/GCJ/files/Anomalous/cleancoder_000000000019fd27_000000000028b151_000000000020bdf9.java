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
        List<Activity> list = Arrays.asList(activities);
        Activity[] activitiesSorted = list.stream()
                                          .sorted((act1, act2) -> Integer.compare(act1.start, act2.start))
                                          .toArray(Activity[]::new);
        StringBuilder ans = new StringBuilder();
        boolean impossible = false;

        for (int i = 0; i < activitiesSorted.length; i++) {
            Activity currentActivity = activitiesSorted[i];
            if (i > 0) {
                Activity lastActivity = activitiesSorted[i - 1];
                if (i > 1) {
                    Activity lastLastActivity = activitiesSorted[i - 2];
                    if (currentActivity.start < lastActivity.end) {
                        if (currentActivity.start < lastLastActivity.end) {
                            impossible = true;
                            break;
                        } else {
                            String person = lastActivity.person.equals("J") ? "C" : "J";
                            currentActivity.person = person;
                            ans.append(person);
                        }
                    } else {
                        currentActivity.person = lastActivity.person;
                        ans.append(lastActivity.person);
                    }
                } else {
                    if (currentActivity.start < lastActivity.end) {
                        String person = lastActivity.person.equals("J") ? "C" : "J";
                        currentActivity.person = person;
                        ans.append(person);
                    } else {
                        currentActivity.person = lastActivity.person;
                        ans.append(lastActivity.person);
                    }
                }
            } else {
                currentActivity.person = "J";
                ans.append("J");
            }
        }

        if (impossible) {
            ans = new StringBuilder("IMPOSSIBLE");
        }

        System.out.println("Case #" + testCaseNum + ": " + ans);
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
    }
}