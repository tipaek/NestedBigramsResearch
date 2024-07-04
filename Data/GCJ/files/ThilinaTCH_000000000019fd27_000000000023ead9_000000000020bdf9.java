import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TreeMap;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 1; i <= t; ++i) {
            int activitiesCount = in.nextInt();
            TreeMap<Integer, EndTime> activityTimes = new TreeMap<Integer, EndTime>();
            for (int j = 0; j < activitiesCount; ++j) {
                activityTimes.put(in.nextInt(), new EndTime(in.nextInt(), j));
            }

            String result = solve(activitiesCount, activityTimes);
            System.out.println("Case #" + i + ": " + result);
        }
    }

    public static class EndTime {
        public final int endTime;
        public final int order;

        public EndTime(int endTime, int order) {
            super();
            this.endTime = endTime;
            this.order = order;
        }
    }

    private static String solve(int activitiesCount, TreeMap<Integer, EndTime> activityTimes) {
        final String IMPOSSIBLE = "IMPOSSIBLE";
        String[] result = new String[activitiesCount];
        Integer[] starts = activityTimes.keySet().stream().toArray(Integer[]::new);
        EndTime[] ends = activityTimes.values().toArray(new EndTime[0]);
        String currentParent = "C";
        int lastParentCIndex = 0;
        int lastParentJIndex = 0;
        int lastOtherParentIndex;

        for (int k = 0; k < activitiesCount; ++k) {
            if (k == 0) {
                result[ends[k].order] = currentParent;
                lastParentCIndex = k;
            } else {
                if (ends[k - 1].endTime <= starts[k]) {
                    result[ends[k].order] = currentParent;
                    if (currentParent == "C") {
                        lastParentCIndex = k;
                    } else {
                        lastParentJIndex = k;
                    }
                } else {
                    if (k > 1) {
                        if (currentParent == "C") {
                            lastOtherParentIndex = lastParentJIndex;
                        } else {
                            lastOtherParentIndex = lastParentCIndex;
                        }
                        if (ends[lastOtherParentIndex].endTime <= starts[k]) {
                            result[ends[k].order] = currentParent == "C" ? "J" : "C";
                            currentParent = currentParent == "C" ? "J" : "C";
                            if (currentParent == "C") {
                                lastParentCIndex = k;
                            } else {
                                lastParentJIndex = k;
                            }
                        } else {
                            return IMPOSSIBLE;
                        }
                    } else {
                        result[ends[k].order] = currentParent == "C" ? "J" : "C";
                        currentParent = currentParent == "C" ? "J" : "C";
                        if (currentParent == "C") {
                            lastParentCIndex = k;
                        } else {
                            lastParentJIndex = k;
                        }
                    }
                }
            }
        }
        return String.join("", result);
    }
}
