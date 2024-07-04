import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {

    private static final String OUTPUT_TEMPLATE = "Case #%d: %d %d %d";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCases = Integer.parseInt(scanner.nextLine());

        for (int caseIndex = 0; caseIndex < numCases; caseIndex++) {
            int matrixSize = Integer.parseInt(scanner.nextLine());
            Integer[][] matrix = new Integer[matrixSize][matrixSize];

            int trace = 0;
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                matrix[rowIndex] = Arrays.stream(scanner.nextLine().split(" "))
                                         .map(Integer::parseInt)
                                         .toArray(Integer[]::new);
                trace += matrix[rowIndex][rowIndex];
            }

            int repeatedRowsCount = countRepeatedRows(matrix);
            int repeatedColsCount = countRepeatedCols(matrix);

            System.out.println(String.format(OUTPUT_TEMPLATE, caseIndex + 1, trace, repeatedRowsCount, repeatedColsCount));
        }
    }

    private static int countRepeatedRows(Integer[][] matrix) {
        int size = matrix.length;
        int repeatedRowsCount = 0;

        for (Integer[] row : matrix) {
            boolean[] seen = new boolean[size + 1];
            for (int value : row) {
                if (seen[value]) {
                    repeatedRowsCount++;
                    break;
                }
                seen[value] = true;
            }
        }

        return repeatedRowsCount;
    }

    private static int countRepeatedCols(Integer[][] matrix) {
        int size = matrix.length;
        int repeatedColsCount = 0;

        for (int colIndex = 0; colIndex < size; colIndex++) {
            boolean[] seen = new boolean[size + 1];
            for (Integer[] row : matrix) {
                if (seen[row[colIndex]]) {
                    repeatedColsCount++;
                    break;
                }
                seen[row[colIndex]] = true;
            }
        }

        return repeatedColsCount;
    }
}