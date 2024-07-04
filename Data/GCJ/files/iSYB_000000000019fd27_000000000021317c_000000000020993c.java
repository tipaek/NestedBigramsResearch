int k = 0;
int r = 0;
int c = 0;
int case = 0;
while(case < T)
{
    for(int i = 0, i < N, i++)
    {
        for(int j = 0, j < N, j++)
        {
            if(i == j)
                k = M[i][j] + k
            for(int x = j, x < N - 1, x++)
            {
                if(M[i][j] == M[i][x+1])
                    r++;
            }
            for(int y = i, y < N - 1, y++)
            {
                if(M[i][j] == M[y+1][j])
                    c++;
            }
        }
    }
    System.out.println("Case #" + T + ": " + k + " " + r + " " + c + " ");
}