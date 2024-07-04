import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {

    private static final String OUTPUT_TEMPLATE = "Case #%d: %d %d %d";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfCases = Integer.parseInt(scanner.nextLine());

        for (int caseIndex = 0; caseIndex < numberOfCases; caseIndex++) {
            int matrixSize = Integer.parseInt(scanner.nextLine());
            Integer[][] matrix = new Integer[matrixSize][matrixSize];

            int traceSum = 0;
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                matrix[rowIndex] = Arrays.stream(scanner.nextLine().split(" "))
                                         .map(Integer::parseInt)
                                         .toArray(Integer[]::new);
                traceSum += matrix[rowIndex][rowIndex];
            }

            int repeatedRowsCount = countRepeatedRows(matrix);
            int repeatedColsCount = countRepeatedCols(matrix);

            System.out.println(String.format(OUTPUT_TEMPLATE, caseIndex + 1, traceSum, repeatedRowsCount, repeatedColsCount));
        }
    }

    private static int countRepeatedRows(Integer[][] matrix) {
        int size = matrix.length;
        int repeatedRows = 0;

        for (Integer[] row : matrix) {
            boolean[] seen = new boolean[size + 1];
            for (int value : row) {
                if (seen[value]) {
                    repeatedRows++;
                    break;
                }
                seen[value] = true;
            }
        }

        return repeatedRows;
    }

    private static int countRepeatedCols(Integer[][] matrix) {
        int size = matrix.length;
        int repeatedCols = 0;

        for (int colIndex = 0; colIndex < size; colIndex++) {
            boolean[] seen = new boolean[size + 1];
            for (int rowIndex = 0; rowIndex < size; rowIndex++) {
                int value = matrix[rowIndex][colIndex];
                if (seen[value]) {
                    repeatedCols++;
                    break;
                }
                seen[value] = true;
            }
        }

        return repeatedCols;
    }
}