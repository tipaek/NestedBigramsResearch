import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < amount; i++) {
            String[] info = scanner.nextLine().split(" ");
            int x = Integer.parseInt(info[0]);
            int y = Integer.parseInt(info[1]);
            solve(i + 1, x, y);
        }
    }

    private static void solve(int test, int x, int y) {
        int absX = Math.abs(x);
        int absY = Math.abs(y);

        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        int maxPowerOf2 = Math.max(
            (int) Math.ceil(Math.log(absX) / Math.log(2)),
            (int) Math.ceil(Math.log(absY) / Math.log(2))
        );

        List<Integer> path = findPath(new ArrayList<>(), 0, 0, absX, absY, -1, (int) Math.pow(2, maxPowerOf2));
        if (path == null) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        StringBuilder pathString = new StringBuilder();
        for (int move : path) {
            switch (move) {
                case 0 -> pathString.append(x > 0 ? "E" : "W");
                case 1 -> pathString.append(x > 0 ? "W" : "E");
                case 2 -> pathString.append(y > 0 ? "N" : "S");
                case 3 -> pathString.append(y > 0 ? "S" : "N");
            }
        }

        System.out.println("Case #" + test + ": " + pathString);
    }

    private static List<Integer> findPath(List<Integer> moves, int x, int y, int dx, int dy, int n, int max) {
        if (n > max * 100) return null;
        if (x == dx && y == dy) return moves;

        List<List<Integer>> possiblePaths = new ArrayList<>();
        int step = n * 2;
        if (n == -1) step = 1;

        addPathIfValid(possiblePaths, findPath(combinedList(moves, 0), x + step, y, dx, dy, step, max));
        addPathIfValid(possiblePaths, findPath(combinedList(moves, 1), x - step, y, dx, dy, step, max));
        addPathIfValid(possiblePaths, findPath(combinedList(moves, 2), x, y + step, dx, dy, step, max));
        addPathIfValid(possiblePaths, findPath(combinedList(moves, 3), x, y - step, dx, dy, step, max));

        return possiblePaths.stream().min(Comparator.comparingInt(List::size)).orElse(null);
    }

    private static void addPathIfValid(List<List<Integer>> possiblePaths, List<Integer> path) {
        if (path != null) possiblePaths.add(path);
    }

    private static List<Integer> combinedList(List<Integer> list, int move) {
        List<Integer> newList = new ArrayList<>(list);
        newList.add(move);
        return newList;
    }
}