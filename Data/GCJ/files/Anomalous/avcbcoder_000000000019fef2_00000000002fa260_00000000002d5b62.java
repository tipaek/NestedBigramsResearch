import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class Solution {
    public static void main(String[] args) throws Exception {
        initializeSubsequences(MAX_JUMP);
        int testCaseCount = scanner.nextInt();
        for (int i = 1; i <= testCaseCount; i++) {
            System.out.print("Case #" + i + ": ");
            solve();
        }
    }

    private static final int MAX_JUMP = 9;
    private static final HashMap<Integer, ArrayList<ArrayList<Integer>>> subsequences = new HashMap<>();
    private static final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private static final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    private static final Scanner scanner = new Scanner(System.in);

    private static void solve() throws Exception {
        int targetX = scanner.nextInt();
        int targetY = scanner.nextInt();

        for (int jump = 1; jump <= MAX_JUMP; jump++) {
            for (ArrayList<Integer> sequence : subsequences.get(jump)) {
                long x = 0, y = 0;
                for (int i = 0; i < sequence.size(); i++) {
                    int direction = sequence.get(i);
                    long value = (long) Math.pow(2, i);
                    switch (direction) {
                        case 0 -> y += value;
                        case 1 -> y -= value;
                        case 2 -> x += value;
                        case 3 -> x -= value;
                    }
                }
                if (x == targetX && y == targetY) {
                    StringBuilder path = new StringBuilder();
                    for (int direction : sequence) {
                        switch (direction) {
                            case 0 -> path.append("N");
                            case 1 -> path.append("S");
                            case 2 -> path.append("E");
                            case 3 -> path.append("W");
                        }
                    }
                    System.out.println(path.toString());
                    return;
                }
            }
        }
        System.out.println("IMPOSSIBLE");
    }

    private static void initializeSubsequences(int n) {
        if (n == 1) {
            ArrayList<ArrayList<Integer>> baseSequences = new ArrayList<>();
            baseSequences.add(new ArrayList<>(Arrays.asList(0)));
            baseSequences.add(new ArrayList<>(Arrays.asList(1)));
            baseSequences.add(new ArrayList<>(Arrays.asList(2)));
            baseSequences.add(new ArrayList<>(Arrays.asList(3)));
            subsequences.put(n, baseSequences);
            return;
        }
        initializeSubsequences(n - 1);
        ArrayList<ArrayList<Integer>> newSequences = new ArrayList<>();
        ArrayList<ArrayList<Integer>> previousSequences = subsequences.get(n - 1);
        for (ArrayList<Integer> sequence : previousSequences) {
            for (int direction = 0; direction <= 3; direction++) {
                ArrayList<Integer> newSequence = new ArrayList<>(sequence);
                newSequence.add(direction);
                newSequences.add(newSequence);
            }
        }
        subsequences.put(n, newSequences);
    }
}