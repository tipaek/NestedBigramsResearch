import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        List<String> results = new ArrayList<>();
        int testCases = scanner.nextInt();
        scanner.nextLine();

        for (int caseNumber = 1; caseNumber <= testCases; ++caseNumber) {
            String input = scanner.nextLine();
            String[] inputs = input.split(" ");
            int n = Integer.parseInt(inputs[0]);
            int k = Integer.parseInt(inputs[1]);
            int remainder = k % n;
            int sumN = n * (n + 1) / 2;

            if (remainder == 0 || (k == sumN && n > 2)) {
                List<List<Integer>> latinSquare = generateLatinSquare(n);
                List<List<Integer>> finalMatrix = new ArrayList<>();

                if (remainder == 0) {
                    int quotient = k / n;

                    for (int j = 0; j < n; j++) {
                        for (int row = 0; row < n; row++) {
                            if (latinSquare.get(row).get(j) == quotient) {
                                finalMatrix.add(latinSquare.get(row));
                                break;
                            }
                        }
                    }
                } else {
                    for (int j = n; j > 0; j--) {
                        for (int row = 0; row < n; row++) {
                            if (latinSquare.get(row).get(j - 1) == j) {
                                finalMatrix.add(latinSquare.get(row));
                                break;
                            }
                        }
                    }
                }

                results.add("Case #" + caseNumber + ": POSSIBLE");
                for (List<Integer> row : finalMatrix) {
                    results.add(String.join(" ", row.stream().map(String::valueOf).toArray(String[]::new)));
                }
            } else {
                results.add("Case #" + caseNumber + ": IMPOSSIBLE");
            }
        }

        results.forEach(System.out::println);
    }

    static List<List<Integer>> generateLatinSquare(int n) {
        int start = n + 1;
        List<List<Integer>> matrix = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            List<Integer> row = new ArrayList<>();
            int temp = start;

            while (temp <= n) {
                row.add(temp);
                temp++;
            }

            for (int j = 1; j < start; j++) {
                row.add(j);
            }

            start--;
            matrix.add(row);
        }

        return matrix;
    }
}