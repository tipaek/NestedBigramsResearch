import java.util.*;

public class Solution {

    public static void main(String[] args) {
        class Data {
            int k;
            int r;
            int c;

            Data(int k, int r, int c)
            {
                this.k = k;
                this.r = r;
                this.c = c;
            }

            String out(int tc)
            {
                return "Case #" + tc + ": " + k + " " + r + " " + c;
            }
        }

        Scanner scanner = new Scanner(System.in);
        int testCases = Integer.parseInt(scanner.nextLine());
        Data[] results = new Data[testCases];


        for(int i = 1; i <= testCases; i++)
        {
            // init vars
            int k = 0, r = 0, c = 0;

            // get size of matrix
            int N = Integer.parseInt(scanner.nextLine());

            // first row, second col
            HashSet<Integer>[] colStore = new HashSet[N];

            // read in matrix data  row by row
            for (int j = 0; j < N; j++) {
                Integer[] row = Arrays.stream(scanner.nextLine().split("\\s+")).map(Integer::parseInt)
                        .toArray(Integer[]::new);

                for(int z = 0; z < N; z++)
                {
                    if (null == colStore[z]) colStore[z] = new HashSet<>();
                    colStore[z].add(row[z]);
                }

                // also sum up trace by this.
                k += row[j];

                // find if distinct
                r += Arrays.stream(row).distinct().count() < row.length ? 1 : 0;
            }

            // read in matrix
            for(int j = 0; j < N; j++)
            {
                if(colStore[j].size() < N) c++;
            }

            results[i-1] = new Data(k, r, c);
        }

        for(int i = 1; i <=testCases; i++)
        {
            System.out.println(results[i-1].out(i));
        }
    }
}
