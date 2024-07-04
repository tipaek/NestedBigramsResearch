import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Solution {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String CNAME = MethodHandles.lookup().lookupClass().getName();
    private static final Random RAND = new Random();
    private static final TreeSet<Integer> POW2 = new TreeSet<>();
    private static final TreeMap<Integer, Integer> RANKS = new TreeMap<>();
    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    static {
        for (int i = 0; i < 31; i++) {
            int pow = 1 << i;
            RANKS.put(pow, i);
            POW2.add(pow);
        }
    }

    private static String join(Collection<?> objs) {
        return String.join(",", objs.stream().map(Object::toString).toArray(String[]::new));
    }

    private static boolean select(boolean ok1, boolean ok2, StringBuilder sb1, StringBuilder sb2, StringBuilder sb) {
        if (ok1 && ok2) {
            sb.setLength(0);
            sb.append(sb1.length() <= sb2.length() ? sb1 : sb2);
            return true;
        } else if (ok1) {
            sb.setLength(0);
            sb.append(sb1);
            return true;
        } else if (ok2) {
            sb.setLength(0);
            sb.append(sb2);
            return true;
        }
        return false;
    }

    private static boolean solve(int x, int y, StringBuilder sb) {
        if (x == 0 && y == 0) {
            return sb.toString().trim().length() == sb.length();
        }

        int absX = Math.abs(x);
        int absY = Math.abs(y);
        int f, c, rf, rc;

        if (x != 0) {
            f = POW2.floor(absX);
            c = POW2.ceiling(absX);
            rf = RANKS.get(f);
            rc = RANKS.get(c);

            if (c == f) {
                return tryDirection(x > 0 ? 'E' : 'W', rc, x > 0 ? 0 : x, y, sb);
            } else {
                return tryBothDirections(x > 0 ? 'E' : 'W', rc, rf, x > 0 ? x - c : x + c, y, x > 0 ? x - f : x + f, y, sb);
            }
        } else if (y != 0) {
            f = POW2.floor(absY);
            c = POW2.ceiling(absY);
            rf = RANKS.get(f);
            rc = RANKS.get(c);

            if (c == f) {
                return tryDirection(y > 0 ? 'N' : 'S', rc, x, y > 0 ? 0 : y, sb);
            } else {
                return tryBothDirections(y > 0 ? 'N' : 'S', rc, rf, x, y > 0 ? y - c : y + c, x, y > 0 ? y - f : y + f, sb);
            }
        }
        return false;
    }

    private static boolean tryDirection(char direction, int rank, int newX, int newY, StringBuilder sb) {
        if (sb.charAt(rank) != ' ') return false;
        sb.setCharAt(rank, direction);
        boolean ok = solve(newX, newY, sb);
        if (!ok) sb.setCharAt(rank, ' ');
        return ok;
    }

    private static boolean tryBothDirections(char direction, int rank1, int rank2, int newX1, int newY1, int newX2, int newY2, StringBuilder sb) {
        boolean ok1 = false, ok2 = false;
        StringBuilder sb1 = null, sb2 = null;

        if (sb.charAt(rank1) == ' ') {
            sb1 = new StringBuilder(sb);
            sb1.setCharAt(rank1, direction);
            ok1 = solve(newX1, newY1, sb1);
        }
        if (sb.charAt(rank2) == ' ') {
            sb2 = new StringBuilder(sb);
            sb2.setCharAt(rank2, direction);
            ok2 = solve(newX2, newY2, sb2);
        }
        return select(ok1, ok2, sb1, sb2, sb);
    }

    private static String solveMe(int x, int y) {
        StringBuilder sb = new StringBuilder("                                ");
        boolean ok = solve(x, y, sb);
        return ok ? sb.toString().trim() : IMPOSSIBLE;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File fin = new File(USER_DIR + "/io/" + CNAME + ".in");
        Scanner in = fin.exists() ? new Scanner(fin) : new Scanner(System.in);
        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            int X = in.nextInt();
            int Y = in.nextInt();
            System.out.format("Case #%d: %s\n", t, solveMe(X, Y));
        }
        in.close();
    }
}