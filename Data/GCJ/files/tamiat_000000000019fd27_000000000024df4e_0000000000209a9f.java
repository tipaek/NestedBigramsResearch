import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int ttc = Integer.parseInt(br.readLine());
        for(int tc = 0; tc < ttc; tc++){
            StringBuilder stringBuilder = new StringBuilder();
            Integer[] digits = Arrays.stream(br.readLine().split("")).map(Integer::parseInt).toArray(Integer[]::new);
            int open = 0;
            for(int i = 0; i < digits.length; i++){
                if(open < digits[i]){
                    int diff = digits[i] - open;
                    for(int k = 0; k < diff; k++) stringBuilder.append("(");
                    open = digits[i];
                } else if(open > digits[i]){
                    int diff = open - digits[i];
                    for(int k = 0; k < diff; k++) stringBuilder.append(")");
                    open = digits[i];
                }
                stringBuilder.append(digits[i]);
            }
            for(int k = 0; k < open; k++){
                stringBuilder.append(")");
            }
            System.out.println("Case #" + (tc+1) + ": " + stringBuilder);
        }
    }
}
