import java.util.Scanner;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Scanner x =new Scanner(System.in);
        int T = x.nextInt();
        int TN = 0;
        int[][] case = new int[M][M];
        while (TN < T) {
            int M = x.nextInt();
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    case[i][j] = x.nextInt();
                }
            }
            int[] answer = help(case, M, TN);
            System.out.println("Case #%d: %d %d %d", TN + 1, answer[0], answer[1], answer[2]);
        }
    }
    
    public int[] help(int[][] case, int M) {
        int[] res = new int[3];
        int k = 0;
        int r = 0;
        int c = 0;
        int i = 0;
        while (i++ < M) {
            k += case[i][i];
        }
        for (int i = 0; i < M; i++) {
            Set<Integer> rows = new HashSet<>();
            for (int j = 0; j < M; j++) {
                if (rows.containsKey(case[i][j])) {
                    r++;
                    break;
                }
                rows.put(case[i][j]);
            }
        }
        for (int i = 0; i < M; i++) {
            Set<Integer> columns = new HashSet<>();
            for (int j = 0; j < M; j++) {
                if (columns.containsKey(case[j][i])) {
                    c++;
                    break;
                }
                columns.put(case[i][j]);
            }
        }
        res[0] = k;
        res[1] = r;
        res[2] = c;
        return res;
    }
}