import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
             PrintWriter out = new PrintWriter(System.out)) {
            int testCount = Integer.parseInt(in.nextLine());
            Solver solver = new Solver();
            for (int i = 1; i <= testCount; i++) {
                solver.solve(i, in, out);
            }
        }
    }

    private static class Solver {
        public void solve(int testNumber, Scanner in, PrintWriter out) {
            String[] line = in.nextLine().split(" ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);
            String moves = line[2];
            int time = 1;

            for (char move : moves.toCharArray()) {
                switch (move) {
                    case 'S' -> y--;
                    case 'N' -> y++;
                    case 'E' -> x++;
                    case 'W' -> x--;
                }

                if (isReachable(x, y, time)) {
                    out.printf("Case #%d: %d\n", testNumber, time);
                    return;
                }
                time++;
            }
            out.printf("Case #%d: IMPOSSIBLE\n", testNumber);
        }

        private boolean isReachable(int x, int y, int time) {
            return Math.abs(x) + Math.abs(y) <= time;
        }
    }
}