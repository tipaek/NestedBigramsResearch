import java.io.*;
import java.util.*;

public class Solution {

    public static class Job {
        char worker;
        int startTime;
        int endTime;

        public Job(char worker, int startTime, int endTime) {
            this.worker = worker;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int T = sc.nextInt();
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = sc.nextInt();

            Stack<Character> workers = new Stack<>(); // the two parents: Cameron, Jamie
            workers.push('J');
            workers.push('C');
            List<Job> inProgress = new ArrayList<>();
            boolean isImpossible = false;
            StringBuilder workerOrder = new StringBuilder();

            for (int index = 0; index < N; index++) {
                int startTime = sc.nextInt();
                int endTime = sc.nextInt();
                
                new Job('C', startTime, endTime)
            }

            String s = isImpossible ? "IMPOSSIBLE" : workerOrder.toString();

            String line = "Case #" + test_case + ": " + s + "\n";
            System.out.print(line);
        }
    }
}
