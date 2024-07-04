import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());

        for (int i = 0; i < T; i++) {
            solve(reader, i + 1);
        }
    }

    private static void swapColumns(int[][] matrix, int col1, int col2) {
        for (int[] row : matrix) {
            int temp = row[col1];
            row[col1] = row[col2];
            row[col2] = temp;
        }
    }

    private static void solve(BufferedReader reader, int caseNumber) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int N = Integer.parseInt(tokenizer.nextToken());
        int K = Integer.parseInt(tokenizer.nextToken());

        if (K % N == 0 && K > 0 && K <= N * N) {
            System.out.printf("Case #%d: POSSIBLE%n", caseNumber);
            int[][] matrix = new int[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    matrix[i][j] = (j + i) % N + 1;
                }
            }

            int targetValue = K / N;
            for (int i = 0; i < N; i++) {
                if (matrix[i][i] != targetValue) {
                    for (int j = 0; j < N; j++) {
                        if (matrix[i][j] == targetValue) {
                            swapColumns(matrix, i, j);
                            break;
                        }
                    }
                }
            }

            for (int[] row : matrix) {
                System.out.println(String.join(" ", Arrays.stream(row)
                        .mapToObj(String::valueOf)
                        .toArray(String[]::new)));
            }
        } else {
            System.out.printf("Case #%d: IMPOSSIBLE%n", caseNumber);
        }
    }
}