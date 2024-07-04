import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public class Solution {

    static class Schedule implements Comparable<Schedule> {
        int i;
        int begin;
        int end;
        char owner;

        Schedule(int i, int begin, int end) {
            this.i = i;
            this.begin = begin;
            this.end = end;
        }

        int length() {
            return end - begin;
        }

        @Override
        public int compareTo(Schedule o) {
            return this.begin - o.begin;
            //return o.length() - this.length();
        }
        public String toString() {
            return i + ": " + begin + ", " + end + ", " + length();
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader in = new BufferedReader(new FileReader("data/input.txt"));

        int T = Integer.parseInt(in.readLine());

        for (int t = 0; t < T; ++t) {
            int N = Integer.parseInt(in.readLine());

            List<Schedule> scheduleList = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                String[] sin = in.readLine().split(" ");
                scheduleList.add(new Schedule(i, Integer.parseInt(sin[0]), Integer.parseInt(sin[1])));
            }

            String result = solve(scheduleList, N);

            System.out.println("Case #" + (t + 1) + ": " + result);
        }

    }

    static final int C = 0;
    static final int J = 1;

    private static String solve(List<Schedule> scheduleList, int N) {
        Collections.sort(scheduleList);
        //scheduleList.forEach(System.out::println);
        boolean[][] timeLine = new boolean[1440][2];

        boolean result = false;
        if (!solve2(0, scheduleList, timeLine, C)) {
            result = solve2(0, scheduleList, timeLine, J);
        } else {
            result = true;
        }

        if (!result) {
            return "IMPOSSIBLE";
        } else {
            char[] result2 = new char[N];
            for (Schedule s : scheduleList) {
                result2[s.i] = s.owner;
            }
            return new String(result2);
        }
    }


    private static boolean solve2(int i, List<Schedule> scheduleList, boolean[][] timeLine, int assignee) {
        if (i == scheduleList.size()) {
            return true;
        }

        Schedule s = scheduleList.get(i);

        boolean isOk = true;
        for (int t = s.begin; t < s.end; ++t) {
            if (timeLine[t][assignee]) {
                isOk = false;
                break;
            } else {
                timeLine[t][assignee] = true;
            }
        }

        if (isOk) {

            if (assignee == C) {
                s.owner = 'C';
            } else {
                s.owner = 'J';
            }

            boolean[][] newTimeLine =
                    Arrays.stream(timeLine).map(boolean[]::clone).toArray(boolean[][]::new);

            if (!solve2(i +1, scheduleList, newTimeLine, C)) {
                return solve2(i +1, scheduleList, newTimeLine, J);
            } else {
                return true;
            }
        }
        return false;
    }
}
