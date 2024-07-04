import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int ttc = Integer.parseInt(br.readLine());
        for(int tc = 0; tc < ttc; tc++){
            int N = Integer.parseInt(br.readLine());
            Integer[][] mat = new Integer[N][N];
            for(int k = 0; k < N; k++){
                mat[k] = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
            }

            int rr = 0, rc = 0;

            // read rm
            for(int i = 0; i < N; i++){
                Set<Integer> thisRow = new HashSet<>();
                for(int j = 0; j < N; j++){
                    if(thisRow.contains(mat[i][j]))
                    {
                        rr++;
                        break;
                    }
                    thisRow.add(mat[i][j]);
                }
            }

            // read cm
            for(int i = 0; i < N; i++){
                Set<Integer> thisRow = new HashSet<>();
                for(int j = 0; j < N; j++){
                    if(thisRow.contains(mat[j][i]))
                    {
                        rc++;
                        break;
                    }
                    thisRow.add(mat[j][i]);
                }
            }

            int trace = 0;
            for(int i = 0; i < N; i++){
                    trace += mat[i][i];
            }

            System.out.println("Case #" + (tc+1) + ": " + trace + " " + rr + " " + rc);
        }
    }
}
