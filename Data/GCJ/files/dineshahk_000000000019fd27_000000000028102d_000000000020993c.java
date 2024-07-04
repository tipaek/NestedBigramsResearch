 private static void calculate(int[][] matrix, int testNo) {
        
        int n = matrix.length;

        int k  = 0;
        int r = 0, c = 0;


        for (int i = 0; i < n; i++) {
            HashSet<Integer> colset = new HashSet<Integer>();
            HashSet<Integer> rowset = new HashSet<Integer>();
            for (int j = 0; j < n; j++) {
                if (i == j)
                    k += matrix[i][j];

                if (colset.contains(matrix[i][j]))
                    c +=1;
                colset.add(matrix[i][j]);

                if (rowset.contains(matrix[j][i]))
                    r +=1;
                rowset.add(matrix[j][i]);



            }
        }

        System.out.println("#"+testNo+ ": "+ k + " "+ r + " " + c  );
    }