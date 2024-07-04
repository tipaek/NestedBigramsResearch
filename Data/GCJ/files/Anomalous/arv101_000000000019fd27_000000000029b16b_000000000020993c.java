import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        // Read number of test cases
        int t = Integer.parseInt(in.readLine());
        
        for (int i = 1; i <= t; i++) {
            int N = Integer.parseInt(in.readLine());
            int[][] matrix = new int[N][N];
            
            // Read matrix
            for (int x = 0; x < N; x++) {
                StringTokenizer line = new StringTokenizer(in.readLine());
                for (int y = 0; y < N; y++) {
                    matrix[x][y] = Integer.parseInt(line.nextToken());
                }
            }
            
            // Calculate trace
            int trace = 0;
            for (int j = 0; j < N; j++) {
                trace += matrix[j][j];
            }
            
            // Calculate number of rows with duplicate elements
            int r = 0;
            for (int R = 0; R < N; R++) {
                boolean[] seen = new boolean[N + 1];
                for (int C = 0; C < N; C++) {
                    if (seen[matrix[R][C]]) {
                        r++;
                        break;
                    }
                    seen[matrix[R][C]] = true;
                }
            }
            
            // Calculate number of columns with duplicate elements
            int c = 0;
            for (int C = 0; C < N; C++) {
                boolean[] seen = new boolean[N + 1];
                for (int R = 0; R < N; R++) {
                    if (seen[matrix[R][C]]) {
                        c++;
                        break;
                    }
                    seen[matrix[R][C]] = true;
                }
            }
            
            // Print result for the current test case
            System.out.println("Case #"i + ": " + trace + " " + r + " " + c);
        }
    }
}