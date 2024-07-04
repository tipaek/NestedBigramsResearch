import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = scanner.nextInt(); // Number of test cases

        for (int caseNum = 1; caseNum <= t; caseNum++) {
            int n = scanner.nextInt(); // Number of intervals
            int[] arr = new int[100000];
            int[] startTimes = new int[n];
            int[] endTimes = new int[n];
            boolean isImpossible = false;

            for (int i = 0; i < n; i++) {
                startTimes[i] = scanner.nextInt();
                endTimes[i] = scanner.nextInt();
                for (int time = startTimes[i]; time < endTimes[i]; time++) {
                    arr.time]++;
                    if (arr.time > 2) {
                        isImpossible = true;
                    }
                }
            }

            if (isImpossible) {
                System.out.println("Case #" + caseNum + ": IMPOSSIBLE");
            } else {
                int[] assignments = new int[n];
                assignments[0] = 0; // Assign first task to 'J'
                for (int i = 1; i < n; i++) {
                    for (int j = i - 1; j >= 0; j--) {
                        if ((startTimes[i] >= startTimes[j] && startTimes[i] < endTimes[j]) || 
                            (endTimes[i] > startTimes[j] && endTimes[i] <= endTimes[j])) {
                            assignments[i] = 1 - assignments[j];
                            break;
                        }
                    }
                }

                System.out.print("Case #" + caseNum + ": ");
                for (int i = 0; i < assignments.length; i++) {
                    System.out.print(assignments[i] == 1 ? 'C' : 'J');
                }
                System.out.println();
            }
        }
    }
}