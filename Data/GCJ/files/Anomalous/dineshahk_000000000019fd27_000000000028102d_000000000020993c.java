import java.util.HashSet;

private static void calculate(int[][] matrix, int testNo) {
    int n = matrix.length;
    int diagonalSum = 0;
    int repeatedRows = 0;
    int repeatedCols = 0;

    for (int i = 0; i < n; i++) {
        HashSet<Integer> rowSet = new HashSet<>();
        HashSet<Integer> colSet = new HashSet<>();
        boolean rowHasDuplicates = false;
        boolean colHasDuplicates = false;

        for (int j = 0; j < n; j++) {
            if (i == j) {
                diagonalSum += matrix[i][j];
            }

            if (!rowSet.add(matrix[i][j])) {
                rowHasDuplicates = true;
            }

            if (!colSet.add(matrix[j][i])) {
                colHasDuplicates = true;
            }
        }

        if (rowHasDuplicates) {
            repeatedRows++;
        }

        if (colHasDuplicates) {
            repeatedCols++;
        }
    }

    System.out.println("#" + testNo + ": " + diagonalSum + " " + repeatedRows + " " + repeatedCols);
}