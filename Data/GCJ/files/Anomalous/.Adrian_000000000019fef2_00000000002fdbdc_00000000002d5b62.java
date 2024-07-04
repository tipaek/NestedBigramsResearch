import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        int absX = Math.abs(x);
        int absY = Math.abs(y);

        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        int maxPowerX = (int) Math.ceil(Math.log(absX) / Math.log(2));
        int maxPowerY = (int) Math.ceil(Math.log(absY) / Math.log(2));
        int maxPower = Math.max(maxPowerX, maxPowerY);

        List<Integer> path = findPath(new ArrayList<>(), BigInteger.ZERO, BigInteger.ZERO, BigInteger.valueOf(absX), BigInteger.valueOf(absY), BigInteger.valueOf(-1), BigInteger.valueOf((int) Math.pow(2, maxPower)));
        if (path == null) {
            System.out.println("Case #" + test + ": IMPOSSIBLE");
            return;
        }

        StringBuilder pathString = new StringBuilder();
        for (int direction : path) {
            switch (direction) {
                case 0 -> pathString.append(x > 0 ? "E" : "W");
                case 1 -> pathString.append(x > 0 ? "W" : "E");
                case 2 -> pathString.append(y > 0 ? "N" : "S");
                case 3 -> pathString.append(y > 0 ? "S" : "N");
            }
        }

        System.out.println("Case #" + test + ": " + pathString);
    }

    private static List<Integer> findPath(List<Integer> moves, BigInteger x, BigInteger y, BigInteger targetX, BigInteger targetY, BigInteger step, BigInteger maxStep) {
        if (step.compareTo(maxStep) > 0) return null;
        if (x.equals(targetX) && y.equals(targetY)) return moves;

        if (x.compareTo(targetX) > 0 || y.compareTo(targetY) > 0 ||
                x.add(maxStep).compareTo(targetX) < 0 || y.add(maxStep).compareTo(targetY) < 0) {
            return null;
        }

        BigInteger nextStep = step.multiply(BigInteger.TWO);
        if (nextStep.equals(BigInteger.valueOf(-2))) nextStep = BigInteger.ONE;

        List<List<Integer>> possiblePaths = new ArrayList<>();
        possiblePaths.add(findPath(addToList(moves, 0), x.add(nextStep), y, targetX, targetY, nextStep, maxStep));
        possiblePaths.add(findPath(addToList(moves, 1), x.subtract(nextStep), y, targetX, targetY, nextStep, maxStep));
        possiblePaths.add(findPath(addToList(moves, 2), x, y.add(nextStep), targetX, targetY, nextStep, maxStep));
        possiblePaths.add(findPath(addToList(moves, 3), x, y.subtract(nextStep), targetX, targetY, nextStep, maxStep));

        List<Integer> shortestPath = null;
        for (List<Integer> path : possiblePaths) {
            if (path != null && (shortestPath == null || path.size() < shortestPath.size())) {
                shortestPath = path;
            }
        }

        return shortestPath;
    }

    private static List<Integer> addToList(List<Integer> list, int value) {
        List<Integer> newList = new ArrayList<>(list);
        newList.add(value);
        return newList;
    }
}