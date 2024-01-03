package Data.four.Anomalous;

import java.util.*;

public class Anom1 {

}

class SubstringConcatenation {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int wordLength = words[0].length();
        int totalWords = words.length;
        int concatLength = wordLength * totalWords;
        int sLength = s.length();

        // Create a hashmap to store word frequencies from the words array
        HashMap<String, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i <= sLength - concatLength; i++) {
            // Create a hashmap to store word frequencies from the current substring
            HashMap<String, Integer> currentWordFrequencyMap = new HashMap<>();
            int j = 0;

            while (j < totalWords) {
                String currentWord = s.substring(i + j * wordLength, i + (j + 1) * wordLength);
                if (wordFrequencyMap.containsKey(currentWord)) {
                    currentWordFrequencyMap.put(currentWord, currentWordFrequencyMap.getOrDefault(currentWord, 0) + 1);

                    if (currentWordFrequencyMap.get(currentWord) > wordFrequencyMap.get(currentWord)) {
                        break; // Current word appears more times than allowed in the substring
                    }
                } else {
                    break; // Current word is not part of the given words array
                }
                j++;
            }

            if (j == totalWords) {
                result.add(i); // All words found in the current substring
            }
        }

        return result;
    }
}

class ReverseKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prevGroupTail = dummy;

        while (head != null) {
            ListNode groupStart = head;
            ListNode groupEnd = getGroupEnd(head, k);

            if (groupEnd == null) {
                break; // There are fewer than k nodes remaining in the list
            }

            head = groupEnd.next; // Move head to the start of the next group
            groupEnd.next = null; // Cut off the group from the remaining list

            prevGroupTail.next = reverseList(groupStart); // Reverse the current group
            groupStart.next = head; // Connect the reversed group to the next group
            prevGroupTail = groupStart; // Update prevGroupTail for the next iteration
        }

        return dummy.next;
    }

    // Helper function to reverse a linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    // Helper function to find the end of the current group
    private ListNode getGroupEnd(ListNode head, int k) {
        while (head != null && k > 1) {
            head = head.next;
            k--;
        }
        return head;
    }

    public static void main(String[] args) {
        ReverseKGroup reverseKGroup = new ReverseKGroup();

        // Example: Reverse nodes in k-groups (k=3) in the linked list: 1 -> 2 -> 3 -> 4
        // -> 5
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        int k = 3;

        ListNode reversed = reverseKGroup.reverseKGroup(head, k);

        // Print the reversed list: 3 -> 2 -> 1 -> 4 -> 5
        while (reversed != null) {
            System.out.print(reversed.val + " ");
            reversed = reversed.next;
        }
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        // Create a min-heap (priority queue) to store the ListNode objects
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Add the heads of all the lists to the min-heap
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }

        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        // Process the min-heap and merge the lists
        while (!minHeap.isEmpty()) {
            ListNode smallestNode = minHeap.poll();
            current.next = smallestNode;
            current = current.next;

            if (smallestNode.next != null) {
                minHeap.offer(smallestNode.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();

        // Example: Merge lists [1->4->5], [1->3->4], and [2->6] into a single sorted
        // list
        ListNode l1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode l3 = new ListNode(2, new ListNode(6));

        ListNode[] lists = { l1, l2, l3 };

        ListNode mergedList = mergeKSortedLists.mergeKLists(lists);

        // Print the merged list
        while (mergedList != null) {
            System.out.print(mergedList.val + " ");
            mergedList = mergedList.next;
        }
    }
}

class MedianOfTwoSortedArrays {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums2.length;
        int low = 0;
        int high = m;

        while (low <= high) {
            int partitionSmall = (low + high) / 2;
            int partitionLarge = (m + n + 1) / 2 - partitionSmall;

            int leftSmall = (partitionSmall == 0) ? Integer.MIN_VALUE : nums1[partitionSmall - 1];
            int rightSmall = (partitionSmall == m) ? Integer.MAX_VALUE : nums1[partitionSmall];

            int leftLarge = (partitionLarge == 0) ? Integer.MIN_VALUE : nums2[partitionLarge - 1];
            int rightLarge = (partitionLarge == n) ? Integer.MAX_VALUE : nums2[partitionLarge];

            if (leftSmall <= rightLarge && leftLarge <= rightSmall) {
                if ((m + n) % 2 == 1) {
                    return Math.max(leftSmall, leftLarge);
                } else {
                    return (Math.max(leftSmall, leftLarge) + Math.min(rightSmall, rightLarge)) / 2.0;
                }
            } else if (leftSmall > rightLarge) {
                high = partitionSmall - 1;
            } else {
                low = partitionSmall + 1;
            }
        }

        // If we reach this point, it means the smaller array is empty.
        // In this case, directly find the median of the second array.
        int totalLen = m + n;
        if (totalLen % 2 == 1) {
            return nums2[totalLen / 2];
        } else {
            return (nums2[totalLen / 2 - 1] + nums2[totalLen / 2]) / 2.0;
        }
    }

}

class RegularExpressionMatching {
    public static boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // Create a DP table to store intermediate results
        boolean[][] dp = new boolean[m + 1][n + 1];

        // Empty string and empty pattern always match
        dp[0][0] = true;

        // Fill the first row (dealing with empty string)
        // For patterns like a*, a*b*, a*b*c*, etc., they can match with an empty
        // string.
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char currentS = s.charAt(i - 1);
                char currentP = p.charAt(j - 1);

                if (currentS == currentP || currentP == '.') {
                    // Characters match or pattern has a wildcard '.'
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (currentP == '*') {
                    char prevP = p.charAt(j - 2);
                    if (prevP == currentS || prevP == '.') {
                        // Consider the '*' as zero or more occurrences of the previous character in the
                        // pattern
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                    } else {
                        // Ignore the '*' and the character before it in the pattern
                        dp[i][j] = dp[i][j - 2];
                    }
                }
            }
        }

        // The last cell of the DP table holds the final result
        return dp[m][n];
    }

}

class LongestValidParentheses {
    public int longestValidParentheses(String s) {
        int maxLength = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // Initialize the stack with a dummy index

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                stack.push(i);
            } else { // Closing parenthesis ')'
                stack.pop(); // Pop the corresponding opening parenthesis index

                if (stack.isEmpty()) {
                    // If the stack is empty, push the current index as a new starting point
                    stack.push(i);
                } else {
                    // Calculate the length of the current valid parentheses substring
                    maxLength = Math.max(maxLength, i - stack.peek());
                }
            }
        }

        return maxLength;
    }

}
