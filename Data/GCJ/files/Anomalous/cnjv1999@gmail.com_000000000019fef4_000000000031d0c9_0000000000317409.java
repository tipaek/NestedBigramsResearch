import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        int t = readInt();
        for (int ti = 1; ti <= t; ti++) {
            String[] input = readLine().split(" ");
            long x = Long.parseLong(input[0]);
            long y = Long.parseLong(input[1]);
            String str = input[2].trim();
            long minTime = Long.MAX_VALUE;
            boolean flag = false;

            long dist2 = Math.abs(x) + Math.abs(y);
            long time = 0;
            if (dist2 <= time) {
                flag = true;
                minTime = time;
            }
            for (char c : str.toCharArray()) {
                switch (c) {
                    case 'N' -> y++;
                    case 'S' -> y--;
                    case 'E' -> x++;
                    case 'W' -> x--;
                }

                long curDist = Math.abs(x) + Math.abs(y);
                time++;

                if (curDist <= time) {
                    flag = true;
                    minTime = Math.min(time, minTime);
                }
            }
            if (flag) {
                System.out.printf("Case %d: %d%n", ti, minTime);
            } else {
                System.out.printf("Case %d: IMPOSSIBLE%n", ti);
            }
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    static int readInt() {
        return Integer.parseInt(next());
    }

    static long readLong() {
        return Long.parseLong(next());
    }

    static double readDouble() {
        return Double.parseDouble(next());
    }

    static String readLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}