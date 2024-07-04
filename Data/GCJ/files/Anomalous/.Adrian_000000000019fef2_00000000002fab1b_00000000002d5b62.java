import java.util.*;
import java.util.stream.Collectors;

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

        if ((x % 2 == y % 2)) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        int maxSteps = Math.max((int) Math.ceil(Math.log(absX) / Math.log(2)), (int) Math.ceil(Math.log(absY) / Math.log(2)));

        List<Integer> path = findPath(new ArrayList<>(), 0, 0, absX, absY, -1, (int) Math.pow(2, maxSteps));
        if (path == null) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        StringBuilder pathString = new StringBuilder();
        boolean positiveX = x > 0;
        boolean positiveY = y > 0;

        for (int move : path) {
            switch (move) {
                case 0 -> pathString.append(positiveX ? "E" : "W");
                case 1 -> pathString.append(positiveX ? "W" : "E");
                case 2 -> pathString.append(positiveY ? "N" : "S");
                case 3 -> pathString.append(positiveY ? "S" : "N");
            }
        }

        System.out.println("Case #" + test + ": " + pathString);
    }

    private static List<Integer> findPath(List<Integer> moves, int x, int y, int targetX, int targetY, int step, int maxSteps) {
        if (step > maxSteps * 10) return null;
        if (x == targetX && y == targetY) return moves;

        int nextStep = step * 2 == -2 ? 1 : step * 2;
        List<List<Integer>> possiblePaths = new ArrayList<>();

        addPathIfNotNull(possiblePaths, findPath(new ArrayList<>(moves) {{
            add(0);
        }}, x + nextStep, y, targetX, targetY, nextStep, maxSteps));

        addPathIfNotNull(possiblePaths, findPath(new ArrayList<>(moves) {{
            add(1);
        }}, x - nextStep, y, targetX, targetY, nextStep, maxSteps));

        addPathIfNotNull(possiblePaths, findPath(new ArrayList<>(moves) {{
            add(2);
        }}, x, y + nextStep, targetX, targetY, nextStep, maxSteps));

        addPathIfNotNull(possiblePaths, findPath(new ArrayList<>(moves) {{
            add(3);
        }}, x, y - nextStep, targetX, targetY, nextStep, maxSteps));

        return possiblePaths.stream().min(Comparator.comparingInt(List::size)).orElse(null);
    }

    private static void addPathIfNotNull(List<List<Integer>> paths, List<Integer> path) {
        if (path != null) {
            paths.add(path);
        }
    }
}