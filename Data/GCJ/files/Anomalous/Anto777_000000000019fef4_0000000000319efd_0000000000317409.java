import java.io.*;
import java.util.*;
import java.awt.Point;

public final class Solution {
    private BufferedReader br;
    private StringTokenizer stk;
    private static final long MOD = 998244353;

    public static void main(String[] args) {
        new Thread(null, () -> {
            try {
                new Solution().run();
            } catch (Exception | Error ex) {
                ex.printStackTrace();
            }
        }, "solution", 1 << 26).start();
    }

    public Solution() {
        br = new BufferedReader(new InputStreamReader(System.in));
        stk = null;
    }

    private void run() throws Exception {
        int t = ni();
        for (int I = 1; I <= t; I++) {
            int x = ni(), y = ni();
            List<Point> points = new ArrayList<>();
            String s = nt();

            for (char c : s.toCharArray()) {
                switch (c) {
                    case 'S' -> y--;
                    case 'N' -> y++;
                    case 'W' -> x--;
                    case 'E' -> x++;
                }
                points.add(new Point(x, y));
            }

            int minTime = Integer.MAX_VALUE;
            for (int i = 0; i < points.size(); i++) {
                int timeTaken1 = i + 1;
                int timeTaken2 = Math.abs(points.get(i).x) + Math.abs(points.get(i).y);
                if (timeTaken2 <= timeTaken1) {
                    minTime = Math.min(minTime, Math.max(timeTaken1, timeTaken2));
                }
            }
            pl("Case #" + I + ": " + (minTime == Integer.MAX_VALUE ? "IMPOSSIBLE" : minTime));
        }
    }

    private String nextToken() throws Exception {
        if (stk == null || !stk.hasMoreTokens()) {
            stk = new StringTokenizer(br.readLine());
        }
        return stk.nextToken();
    }

    private String nt() throws Exception {
        return nextToken();
    }

    private int ni() throws Exception {
        return Integer.parseInt(nextToken());
    }

    private void pl(Object o) {
        System.out.println(o);
    }
}