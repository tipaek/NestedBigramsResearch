static int findTrace(int mat[][], int n)
    {
        int sum = 0;
        for (int i=0; i<n; i++)
            sum += mat[i][i];
        return sum;
    }