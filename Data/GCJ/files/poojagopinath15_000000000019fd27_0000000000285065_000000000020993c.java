private static boolean isLatinSquare(int[][] square) {
    boolean[][] foundInRow = new boolean[square.length][square.length];
    boolean[][] foundInCol = new boolean[square.length][square.length];
    for (int row = 0; row < square.length; row++) {
        if (square[row].length != square.length)
            return false; // Not a square
        for (int col = 0; col < square.length; col++) {
            int idx = square[row][col] - 1;
            if (foundInRow[row][idx] || foundInCol[col][idx])
                return false;
            foundInRow[row][idx] = foundInCol[col][idx] = true;
        }
    }
    return true;
}
System.out.println(isLatinSquare(new int[][] { {1, 2, 3},
                                               {2, 3, 1},
                                               {3, 1, 2} }));
System.out.println(isLatinSquare(new int[][] { {1, 2, 3},
                                               {3, 1, 2},
                                               {2, 3, 1} }));
System.out.println(isLatinSquare(new int[][] { {1, 3, 2},
                                               {2, 1, 3},
                                               {3, 2, 1} }));
System.out.println(isLatinSquare(new int[][] { {1, 3, 2},
                                               {3, 2, 1},
                                               {2, 1, 3} }));
System.out.println(isLatinSquare(new int[][] { {1, 3, 2},
                                               {3, 2, 1},
                                               {1, 3, 2} }));
System.out.println(isLatinSquare(new int[][] { {1, 3, 1},
                                               {3, 2, 3},
                                               {2, 1, 2} }));
System.out.println(isLatinSquare(new int[][] { {1, 2, 3},
                                               {2, 3},
                                               {3, 1, 2} }));

