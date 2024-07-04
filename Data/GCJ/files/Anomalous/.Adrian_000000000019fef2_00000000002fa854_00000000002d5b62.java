import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < amount; i++) {
            String[] info = scanner.nextLine().split(" ", 2);
            solve(i + 1, Integer.parseInt(info[0]), Integer.parseInt(info[1]));
        }
    }

    private static void solve(int test, int x, int y) {
        int ax = Math.abs(x);
        int ay = Math.abs(y);

        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        int maxSteps = Math.max((int) Math.ceil(Math.log(ax) / Math.log(2)), (int) Math.ceil(Math.log(ay) / Math.log(2)));
        List<Integer> path = findPath(new ArrayList<>(), 0, 0, ax, ay, -1, (int) Math.pow(2, maxSteps));

        if (path == null) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        boolean mx = x > 0;
        boolean my = y > 0;
        StringBuilder pathString = new StringBuilder();

        for (int move : path) {
            switch (move) {
                case 0 -> pathString.append(mx ? "E" : "W");
                case 1 -> pathString.append(mx ? "W" : "E");
                case 2 -> pathString.append(my ? "N" : "S");
                case 3 -> pathString.append(my ? "S" : "N");
            }
        }

        System.out.println("Case #" + test + ": " + pathString);
    }

    private static List<Integer> findPath(List<Integer> moves, int x, int y, int dx, int dy, int step, int maxSteps) {
        if (step > maxSteps * 10 || x > dx || y > dy) return null;
        if (x == dx && y == dy) return moves;

        int nextStep = step * 2;
        if (nextStep == -2) nextStep = 1;

        List<List<Integer>> possiblePaths = new ArrayList<>();
        List<Integer> path;

        path = findPath(addMove(moves, 0), x + nextStep, y, dx, dy, nextStep, maxSteps);
        if (path != null) possiblePaths.add(path);
        path = findPath(addMove(moves, 1), x - nextStep, y, dx, dy, nextStep, maxSteps);
        if (path != null) possiblePaths.add(path);
        path = findPath(addMove(moves, 2), x, y + nextStep, dx, dy, nextStep, maxSteps);
        if (path != null) possiblePaths.add(path);
        path = findPath(addMove(moves, 3), x, y - nextStep, dx, dy, nextStep, maxSteps);
        if (path != null) possiblePaths.add(path);

        return possiblePaths.stream().min(Comparator.comparingInt(List::size)).orElse(null);
    }

    private static List<Integer> addMove(List<Integer> moves, int move) {
        List<Integer> newMoves = new ArrayList<>(moves);
        newMoves.add(move);
        return newMoves;
    }
}