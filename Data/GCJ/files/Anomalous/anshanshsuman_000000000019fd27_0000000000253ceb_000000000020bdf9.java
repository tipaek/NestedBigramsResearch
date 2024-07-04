public class Time {
    public int high;
    public int low;
}

import java.util.Scanner;

public class GlobalMembers {
    public static int fre(Time x, Time[] t, String arr, char c, int j) {
        int ans = 2;
        for (int i = 0; i < j; i++) {
            if (arr.charAt(i) == c) {
                if (((x.low >= t[i].high) && (x.high > t[i].high)) || ((x.low < t[i].low) && (x.high <= t[i].low))) {
                    ans = 1;
                } else {
                    ans = 0;
                    break;
                }
            }
        }
        return ans == 2 || ans == 1 ? 1 : 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int caseNum = 0;

        while (a-- > 0) {
            caseNum++;
            int b = scanner.nextInt();
            StringBuilder arr = new StringBuilder(" ".repeat(b));
            Time[] times = new Time[b];

            for (int i = 0; i < b; i++) {
                times[i] = new Time();
                times[i].low = scanner.nextInt();
                times[i].high = scanner.nextInt();
            }

            for (int i = 0; i < b; i++) {
                if (fre(times[i], times, arr.toString(), 'C', b) != 0) {
                    arr.setCharAt(i, 'C');
                } else if (fre(times[i], times, arr.toString(), 'J', b) != 0) {
                    arr.setCharAt(i, 'J');
                } else {
                    arr.setCharAt(i, 'I');
                }
            }

            String result = arr.toString().replaceAll(" ", "");
            if (result.contains("I")) {
                result = "IMPOSSIBLE";
            }

            System.out.printf("Case #%d: %s%n", caseNum, result);
        }

        scanner.close();
    }
}