import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class fault {
    // inserted, anomolous code(snagged off my leetcode submissions lol)
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0)
            return new int[][] { newInterval };
        List<int[]> list = new ArrayList<>();
        int[] temp = new int[] { -1, -1 };
        if (newInterval[1] < intervals[0][0]) {
            temp = newInterval;
            list.add(temp);
        }

        for (int i = 0; i < intervals.length; i++) {

            if (newInterval[0] >= intervals[i][0] && newInterval[0] <= intervals[i][1]) {
                temp[0] = Math.min(newInterval[0], intervals[i][0]);
            } else if (temp[1] == -1 && newInterval[1] < intervals[i][0]) {
                temp[1] = newInterval[1];
                if (temp[0] == -1) {
                    temp = newInterval;
                    list.add(temp);
                } else
                    list.add(temp);
            }

            if (temp[1] == -1 && newInterval[1] >= intervals[i][0] && newInterval[1] <= intervals[i][1]) {
                temp[1] = Math.max(newInterval[1], intervals[i][1]);
                if (temp[0] == -1) {
                    temp[0] = newInterval[0];
                }
                list.add(temp);
            }
            if (newInterval[0] > intervals[i][1] || newInterval[1] < intervals[i][0]) {
                list.add(intervals[i]);
            }
        }
        int[] last = intervals[intervals.length - 1];

        if (temp[0] == -1)
            list.add(newInterval);
        else if (temp[1] == -1) {
            temp[1] = newInterval[1];
            list.add(temp);
        }

        return list.toArray(new int[list.size()][]);
    }

    public int search(int[] nums, int target) {

        if (nums.length == 1) {
            if (nums[0] == target)
                return 0;
            return -1;
        }
        int l = 0, r = nums.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target)
                return m;

            if (nums[m] > target)
                r = m - 1;
            else
                l = m + 1;
        }
        return -1;
    }

    public int[] twoSum(int[] nums, int target) {
        Hashtable<Integer, Integer> ht = new Hashtable<>();
        int[] output = new int[2];

        for (int i = 0; i < nums.length; i++) {
            ht.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int temp = ht.getOrDefault(target - nums[i], -1);
            if (temp != -1 && temp != i)
                return new int[] { i, temp };
        }
        return output;
    }

    public boolean isPossible(int n, List<List<Integer>> edges) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (List<Integer> edge : edges) {
            graph.computeIfAbsent(edge.get(0), k -> new HashSet<>()).add(edge.get(1));
            graph.computeIfAbsent(edge.get(1), k -> new HashSet<>()).add(edge.get(0));
        }
        List<Integer> nodeWithOddEdges = graph.entrySet()
                .stream()
                .filter(e -> e.getValue().size() % 2 != 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (nodeWithOddEdges.size() == 0) {
            return true;
        }
        if (nodeWithOddEdges.size() == 2) {
            Integer nodeOne = nodeWithOddEdges.get(0);
            Integer nodeTwo = nodeWithOddEdges.get(1);
            if (!hasEdge(graph, nodeOne, nodeTwo)) {
                return true;
            }
            for (int i = 1; i <= n; i++) {
                if (i == nodeOne || i == nodeTwo) {
                    continue;
                }
                if (!hasEdge(graph, i, nodeOne) && !hasEdge(graph, i, nodeTwo)) {
                    return true;
                }
            }
        }
        if (nodeWithOddEdges.size() == 4) {
            Integer nodeOne = nodeWithOddEdges.get(0);
            Integer nodeTwo = nodeWithOddEdges.get(1);
            Integer nodeThree = nodeWithOddEdges.get(2);
            Integer nodeFour = nodeWithOddEdges.get(3);
            if ((!hasEdge(graph, nodeOne, nodeTwo) && !hasEdge(graph, nodeThree, nodeFour)) ||
                    (!hasEdge(graph, nodeOne, nodeThree) && !hasEdge(graph, nodeTwo, nodeFour)) ||
                    (!hasEdge(graph, nodeOne, nodeFour) && !hasEdge(graph, nodeTwo, nodeThree))) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasEdge(Map<Integer, Set<Integer>> graph, int nodeOne, int nodeTwo) {
        return graph.getOrDefault(nodeOne, new HashSet<>()).contains(nodeTwo);
    }

    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.computeIfAbsent(c, k -> new HashSet<>());
            }
        }
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int minLength = Math.min(word1.length(), word2.length());
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            for (int j = 0; j < minLength; j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    graph.get(word1.charAt(j)).add(word2.charAt(j));
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        Map<Character, Boolean> visited = new HashMap<>();
        for (Character c : graph.keySet()) {
            if (dfs(c, graph, sb, visited)) {
                return "";
            }
        }
        return sb.reverse().toString();
    }

    private boolean dfs(Character c, Map<Character, Set<Character>> graph,
            StringBuilder sb, Map<Character, Boolean> visited) {
        if (visited.containsKey(c)) {
            return visited.get(c);
        }
        visited.put(c, true);
        for (Character neighbor : graph.getOrDefault(c, new HashSet<>())) {
            if (dfs(neighbor, graph, sb, visited)) {
                return true;
            }
        }
        visited.put(c, false);
        sb.append(c);
        return false;
    }
}

/**
 * Created by gouthamvidyapradhan on 29/03/2019 We are given an array A of
 * positive integers, and
 * two positive integers L and R (L <= R).
 *
 * <p>
 * Return the number of (contiguous, non-empty) subarrays such that the value of
 * the maximum
 * array element in that subarray is at least L and at most R.
 *
 * <p>
 * Example : Input: A = [2, 1, 4, 3] L = 2 R = 3 Output: 3 Explanation: There
 * are three subarrays
 * that meet the requirements: [2], [2, 1], [3]. Note:
 *
 * <p>
 * L, R and A[i] will be an integer in the range [0, 10^9]. The length of A will
 * be in the range
 * of [1, 50000].
 */
class SubArraysWithBoundedMaximum {
    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] A = { 2, 1, 4, 3 };
        System.out.println(new SubArraysWithBoundedMaximum().numSubarrayBoundedMax(A, 2, 3));
    }

    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int[] DP = new int[A.length];
        int v = -1;
        for (int i = A.length - 1; i >= 0; i--) {
            if (A[i] >= L && A[i] <= R) {
                if (v != -1) {
                    DP[i] = v - i + 1;
                } else {
                    DP[i] = 1;
                    v = i;
                }
            } else if (A[i] < L) {
                if (v == -1) {
                    v = i;
                }
                if (i + 1 < A.length) {
                    if (A[i + 1] < L || (A[i + 1] >= L && A[i + 1] <= R)) {
                        DP[i] = DP[i + 1];
                    } else {
                        DP[i] = 0;
                    }
                }
            } else {
                v = -1;
            }
        }
        int sum = 0;
        for (int i = 0; i < DP.length; i++) {
            sum += DP[i];
        }
        return sum;
    }
}
