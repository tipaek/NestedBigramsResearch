import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    private static boolean debug = false;

    private static int X, Y;
    private static char[] A;

    private static void solveProblem(InputStream inputStream) {
        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(inputStream)))) {
            int testCount = scanner.nextInt();
            for (int t = 1; t <= testCount; t++) {
                X = scanner.nextInt();
                Y = scanner.nextInt();
                A = scanner.next().toCharArray();
                Object result = solveTestCase();
                System.out.println("Case #" + t + ": " + result);
            }
        }
    }

    private static Object solveTestCase() {
        int minute = 0;
        int distance = Math.abs(X) + Math.abs(Y);
        boolean done = false;

        if (X == 0 && Y == 0) {
            done = true;
        } else {
            for (char move : A) {
                switch (move) {
                    case 'E' -> X++;
                    case 'W' -> X--;
                    case 'N' -> Y++;
                    case 'S' -> Y--;
                }
                minute++;
                distance = Math.abs(X) + Math.abs(Y);
                if (minute >= distance) {
                    done = true;
                    break;
                }
            }
        }

        return done ? minute : "IMPOSSIBLE";
    }

    private static String joinValues(List<?> list, String delimiter) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }

    private static String joinValues(int[] array, String delimiter) {
        return Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(delimiter));
    }

    private static int[] readInts(Scanner scanner, int count) {
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }

    private static void printDebug(Object message) {
        if (debug) {
            System.out.println("DEBUG: " + message);
        }
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        if (debug) {
            solveProblem(new FileInputStream("input.in"));
            System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        } else {
            solveProblem(System.in);
        }
    }
}