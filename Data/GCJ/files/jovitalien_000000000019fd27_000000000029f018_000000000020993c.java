import java.util.*;
import java.io.*;
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int tests = in.nextInt();
        for (int i = 0; i < test; ++i) {
            int size = in.nextInt();
            int[][] matrix = new int[size][size];
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    matrix[row, col] = in.nextInt();
                }
            }
            int trace = 0;
            int repRows = 0;
            int repCols = 0;
            for (int diag = 0; diag < size; diag++) {
                tradce += matrix[diag][diag];
                Set<Integer> rows = new HashSet<>();
                Set<Integer> cols = new HashSet<>();
                for (int row = 0; row < size; row++) {
                    rows.add(matrix[row][diag]);
                }
                for (int col = 0; col < size; col++) {
                    cols.add(matrix[diag][col]);
                }
                if(rows.size < size) {
                    repCols++;
                }
                if(cols.size < size) {
                    repRows++;
                }
                System.out.println("Case #%d: %d %d %d\n", i, trace, repRows, repCols);
            }
        }
    }
}