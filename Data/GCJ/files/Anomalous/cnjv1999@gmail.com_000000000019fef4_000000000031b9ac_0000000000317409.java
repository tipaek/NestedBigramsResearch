import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        int t = nextInt();
        for (int ti = 1; ti <= t; ti++) {
            int x = nextInt();
            int y = nextInt();
            String str = next();
            int minTime = Integer.MAX_VALUE;
            boolean possible = false;

            int initialDist = Math.abs(x) + Math.abs(y);
            int time = 0;

            if (initialDist <= time) {
                possible = true;
                minTime = time;
            }

            for (char c : str.toCharArray()) {
                switch (c) {
                    case 'N' -> y++;
                    case 'S' -> y--;
                    case 'E' -> x++;
                    case 'W' -> x--;
                }

                int currentDist = Math.abs(x) + Math.abs(y);
                time++;

                if (currentDist <= time) {
                    possible = true;
                    minTime = Math.min(time, minTime);
                }
            }

            if (possible) {
                printf("Case %d: %d\n", ti, minTime);
            } else {
                printf("Case %d: IMPOSSIBLE\n", ti);
            }
        }
    }

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    private static String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    private static int nextInt() {
        return Integer.parseInt(next());
    }

    private static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}