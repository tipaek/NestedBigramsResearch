import java.util.*;
import java.io.*;

public class Solution {

    static Set<int[]> seen = new HashSet<>();

    public static int[] getArr(int[][] nums, int[][] sorted, int k) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i][0] == sorted[k][0] && nums[i][1] == sorted[k][1] && !seen.contains(nums[i])) {
                seen.add(nums[i]);
                return nums[i];
            }
        }
        return nums[0];
    }

    public static void schedule(int[][] nums, int caseNum) {
        int[][] sorted = Arrays.stream(nums).map(int[]::clone).toArray(int[][]::new);
        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));

        Map<int[], String> map = new HashMap<>();
        StringBuilder result = new StringBuilder();

        List<int[]> jList = new ArrayList<>();
        List<int[]> cList = new ArrayList<>();

        jList.add(sorted[0]);
        int[] temp = getArr(nums, sorted, 0);
        map.put(temp, "J");

        int prevStart = temp[0];
        int prevEnd = temp[1];
        int startCount = 1;
        int endCount = 1;

        for (int i = 1; i < sorted.length; i++) {
            if (prevStart == sorted[i][0] && prevEnd == sorted[i][1]) {
                startCount++;
                endCount++;
                if (startCount > 2 && endCount > 2) {
                    System.out.println("Case #" + caseNum + ": IMPOSSIBLE");
                    return;
                }
            } else {
                prevStart = sorted[i][0];
                prevEnd = sorted[i][1];
                startCount = 1;
                endCount = 1;
            }

            if (cList.isEmpty()) {
                cList.add(sorted[i]);
                temp = getArr(nums, sorted, i);
                map.put(temp, "C");
            } else if ((sorted[i][0] < jList.get(jList.size() - 1)[1]) && 
                       (sorted[i][0] < cList.get(cList.size() - 1)[1])) {
                System.out.println("Case #" + caseNum + ": IMPOSSIBLE");
                return;
            } else if (sorted[i][0] < jList.get(jList.size() - 1)[1]) {
                cList.add(sorted[i]);
                temp = getArr(nums, sorted, i);
                map.put(temp, "C");
            } else {
                jList.add(sorted[i]);
                temp = getArr(nums, sorted, i);
                map.put(temp, "J");
            }
        }

        for (int[] num : nums) {
            result.append(map.get(num));
        }
        System.out.println("Case #" + caseNum + ": " + result.toString());
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = Integer.parseInt(in.nextLine());
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            int[][] nums = new int[n][2];
            for (int k = 0; k < n; k++) {
                nums[k][0] = in.nextInt();
                nums[k][1] = in.nextInt();
            }
            schedule(nums, i);
        }
        in.close();
    }
}