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
            TreeMap<Integer, Integer> activityTimes = new TreeMap<Integer, Integer>();
            for (int j = 0; j < activitiesCount; ++j) {
                activityTimes.put(in.nextInt(), in.nextInt());
            }

            String result = solve(activitiesCount, activityTimes);
            System.out.println("Case #" + i + ": " + result);
        }
    }

    private static String solve(int activitiesCount, TreeMap<Integer, Integer> activityTimes) {
        final String IMPOSSIBLE = "IMPOSSIBLE";
        String result = "";
        Integer[] starts = activityTimes.keySet().stream().toArray(Integer[]::new);
        Integer[] ends = activityTimes.values().toArray(new Integer[0]);
        String currentParent = "C";
        int lastParentCIndex = 0;
        int lastParentJIndex = 0;
        int lastOtherParentIndex;

        for (int k = 0; k < activitiesCount; ++k) {
            if (k == 0) {
                result += currentParent;
                lastParentCIndex = k;
            } else {
                if (ends[k - 1] <= starts[k]) {
                    result += currentParent;
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
                        if (ends[lastOtherParentIndex] <= starts[k]) {
                            result += currentParent == "C" ? "J" : "C";
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
                        result += currentParent == "C" ? "J" : "C";
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
        return result;
    }
}
