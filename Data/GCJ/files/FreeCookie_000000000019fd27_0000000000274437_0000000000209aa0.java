import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int testCaseSetSize = Integer.parseInt(scanner.nextLine().trim());
        for (int testCaseNumber = 1; testCaseNumber <= testCaseSetSize; testCaseNumber++) {
            String[] strings = scanner.nextLine().trim().split(" ");
            int matrixSize = Integer.parseInt(strings[0]);
            int trace = Integer.parseInt(strings[1]);


            System.out.print("Case #" + testCaseNumber + ": ");

            if (trace < matrixSize || trace % matrixSize != 0) {
                System.out.println("IMPOSSIBLE");
                continue;
            }


            System.out.println("POSSIBLE");

            int[][] matrix = new int[matrixSize][matrixSize];

            int diagonalNumber = trace / matrixSize;

            for (int rowNumber = 0; rowNumber < matrixSize; rowNumber++) {

                int colNumber = rowNumber;
                int nextNumber = diagonalNumber;

                for (int j = 0; j < matrixSize; j++) {

                    if (++colNumber >= matrixSize) {
                        colNumber = 0;
                    }

                    matrix[rowNumber][colNumber] = nextNumber;

                    if (++nextNumber > matrixSize) {
                        nextNumber = 1;
                    }

                }

            }


            for (int rowNumber = 0; rowNumber < matrixSize; rowNumber++) {

                String strArray[] = Arrays.stream(matrix[rowNumber])
                        .mapToObj(String::valueOf)
                        .toArray(String[]::new);

                System.out.println(String.join(" ", strArray));

            }

        }

    }
}
