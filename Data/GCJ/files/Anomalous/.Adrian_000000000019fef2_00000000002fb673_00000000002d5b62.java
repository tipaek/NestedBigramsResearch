import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < amount; i++) {
            String[] info = scanner.nextLine().split(" ", 2);
            int x = Integer.parseInt(info[0]);
            int y = Integer.parseInt(info[1]);
            solve(i + 1, x, y);
        }
    }

    private static void solve(int test, int x, int y) {
        int absX = Math.abs(x);
        int absY = Math.abs(y);

        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        int maxPower = Math.max(
            (int) Math.ceil(Math.log(absX) / Math.log(2)),
            (int) Math.ceil(Math.log(absY) / Math.log(2))
        );

        List<Integer> path = findPath(new ArrayList<>(), BigInteger.ZERO, BigInteger.ZERO, 
                                      BigInteger.valueOf(absX), BigInteger.valueOf(absY), 
                                      BigInteger.valueOf(-1), BigInteger.valueOf((int) Math.pow(2, maxPower)));
        
        if (path == null) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        StringBuilder pathString = new StringBuilder();
        boolean isXPositive = x > 0;
        boolean isYPositive = y > 0;

        for (int move : path) {
            switch (move) {
                case 0 -> pathString.append(isXPositive ? "E" : "W");
                case 1 -> pathString.append(isXPositive ? "W" : "E");
                case 2 -> pathString.append(isYPositive ? "N" : "S");
                case 3 -> pathString.append(isYPositive ? "S" : "N");
            }
        }

        System.out.println("Case #" + test + ": " + pathString);
    }

    private static List<Integer> findPath(List<Integer> moves, BigInteger x, BigInteger y, 
                                          BigInteger dx, BigInteger dy, BigInteger n, BigInteger max) {
        if (n.compareTo(max) > 0) return null;
        if (x.equals(dx) && y.equals(dy)) return moves;

        if (x.compareTo(dx) > 0 || x.add(max).compareTo(dx) < 0 || 
            y.compareTo(dy) > 0 || y.add(max).compareTo(dy) < 0) return null;

        BigInteger step = n.multiply(BigInteger.TWO);
        if (step.equals(BigInteger.valueOf(-2))) step = BigInteger.ONE;

        List<List<Integer>> possiblePaths = new ArrayList<>();
        List<Integer> result;

        result = findPath(addMove(moves, 0), x.add(step), y, dx, dy, step, max);
        if (result != null) possiblePaths.add(result);
        result = findPath(addMove(moves, 1), x.subtract(step), y, dx, dy, step, max);
        if (result != null) possiblePaths.add(result);
        result = findPath(addMove(moves, 2), x, y.add(step), dx, dy, step, max);
        if (result != null) possiblePaths.add(result);
        result = findPath(addMove(moves, 3), x, y.subtract(step), dx, dy, step, max);
        if (result != null) possiblePaths.add(result);

        return possiblePaths.stream().min(Comparator.comparingInt(List::size)).orElse(null);
    }

    private static List<Integer> addMove(List<Integer> list, int move) {
        List<Integer> newList = new ArrayList<>(list);
        newList.add(move);
        return newList;
    }
}