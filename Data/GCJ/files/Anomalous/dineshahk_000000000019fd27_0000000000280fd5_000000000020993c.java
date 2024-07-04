private static void calculate(int[][] matrix, int testNo) {
    int n = matrix.length;
    int diagonalSum = 0;
    int repeatedRows = 0, repeatedCols = 0;

    for (int i = 0; i < n; i++) {
        HashSet<Integer> rowSet = new HashSet<>();
        HashSet<Integer> colSet = new HashSet<>();
        boolean rowHasDuplicate = false;
        boolean colHasDuplicate = false;

        for (int j = 0; j < n; j++) {
            if (i == j) {
                diagonalSum += matrix[i][j];
            }

            if (!rowHasDuplicate && !rowSet.add(matrix[i][j])) {
                repeatedRows++;
                rowHasDuplicate = true;
            }

            if (!colHasDuplicate && !colSet.add(matrix[j][i])) {
                repeatedCols++;
                colHasDuplicate = true;
            }
        }
    }

    System.out.println("#" + testNo + ": " + diagonalSum + " " + repeatedRows + " " + repeatedCols);
}