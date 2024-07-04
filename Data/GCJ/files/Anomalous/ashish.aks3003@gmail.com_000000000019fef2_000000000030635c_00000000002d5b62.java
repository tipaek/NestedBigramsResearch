import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) {
        InputStream in = System.in;
        InputReader scan = new InputReader(in);
        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            StringBuilder res = new StringBuilder();
            if ((x + y) % 2 == 0) {
                System.out.println("Case #" + (i + 1) + ": " + "IMPOSSIBLE");
            } else if (x != 0 || y != 0) {
                ArrayList<ArrayList<Interval>> dp = new ArrayList<>();
                ArrayList<Interval> irow = new ArrayList<>();
                irow.add(new Interval(0, 0));
                dp.add(irow);
                int row = 0;
                boolean goon = true;
                Interval result = new Interval(-1, -1);
                while (goon) {
                    for (int j = 0; j < dp.get(row).size(); j++) {
                        int move = (int) Math.pow(2, row);
                        dp.add(new ArrayList<>());
                        Interval element = dp.get(row).get(j);
                        if (expogo(element.x + move, element.y, x, y)) {
                            result.set(row + 1, dp.get(row + 1).size());
                            goon = false;
                            break;
                        } else {
                            dp.get(row + 1).add(new Interval(element.x + move, element.y));
                        }
                        if (expogo(element.x - move, element.y, x, y)) {
                            result.set(row + 1, dp.get(row + 1).size());
                            goon = false;
                            break;
                        } else {
                            dp.get(row + 1).add(new Interval(element.x - move, element.y));
                        }
                        if (expogo(element.x, element.y + move, x, y)) {
                            result.set(row + 1, dp.get(row + 1).size());
                            goon = false;
                            break;
                        } else {
                            dp.get(row + 1).add(new Interval(element.x, element.y + move));
                        }
                        if (expogo(element.x, element.y - move, x, y)) {
                            result.set(row + 1, dp.get(row + 1).size());
                            goon = false;
                            break;
                        } else {
                            dp.get(row + 1).add(new Interval(element.x, element.y - move));
                        }
                    }
                    row++;
                }
                while (result.x > 0) {
                    int direction = result.y % 4;
                    result.y /= 4;
                    switch (direction) {
                        case 0 -> res.append('E');
                        case 1 -> res.append('W');
                        case 2 -> res.append('N');
                        case 3 -> res.append('S');
                    }
                    result.x--;
                }
                System.out.println("Case #" + (i + 1) + ": " + res.reverse().toString());
            }
        }
    }

    public static boolean expogo(long xm, long ym, int x, int y) {
        return xm == x && ym == y;
    }

    static class InputReader {
        BufferedReader br;
        StringTokenizer st;

        public InputReader(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static class Interval {
        int x, y;

        Interval(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void set(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}