import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < t; i++) {
            String[] input = br.readLine().split(" ");
            int x = Integer.parseInt(input[0]);
            int y = Integer.parseInt(input[1]);
            StringBuilder ans = new StringBuilder();

            if ((Math.abs(x) % 2 == 0 && Math.abs(y) % 2 == 0) || (Math.abs(x) % 2 == 1 && Math.abs(y) % 2 == 1)) {
                ans.append("IMPOSSIBLE");
            } else {
                ans.append(getDirection(x, y));
            }

            result.append("Case #").append(i + 1).append(": ").append(ans).append("\n");
        }
        System.out.print(result);
    }

    private static String getDirection(int x, int y) {
        StringBuilder direction = new StringBuilder();

        switch (x) {
            case -4:
                switch (y) {
                    case -3 -> direction.append("SSW");
                    case -1 -> direction.append("NSW");
                    case 1 -> direction.append("SNW");
                    case 3 -> direction.append("NNW");
                }
                break;
            case -3:
                switch (y) {
                    case -4 -> direction.append("WWS");
                    case -2 -> direction.append("ESW");
                    case 0 -> direction.append("WW");
                    case 2 -> direction.append("ENW");
                    case 4 -> direction.append("WWN");
                }
                break;
            case -2:
                switch (y) {
                    case -3 -> direction.append("NWS");
                    case -1 -> direction.append("SW");
                    case 1 -> direction.append("NW");
                    case 3 -> direction.append("SWN");
                }
                break;
            case -1:
                switch (y) {
                    case -4 -> direction.append("EWS");
                    case -2 -> direction.append("WS");
                    case 0 -> direction.append("W");
                    case 2 -> direction.append("WN");
                    case 4 -> direction.append("EWN");
                }
                break;
            case 0:
                switch (y) {
                    case -3 -> direction.append("SS");
                    case -1 -> direction.append("S");
                    case 1 -> direction.append("N");
                    case 3 -> direction.append("NN");
                }
                break;
            case 1:
                switch (y) {
                    case -4 -> direction.append("WES");
                    case -2 -> direction.append("ES");
                    case 0 -> direction.append("E");
                    case 2 -> direction.append("EN");
                    case 4 -> direction.append("WEN");
                }
                break;
            case 2:
                switch (y) {
                    case -3 -> direction.append("NES");
                    case -1 -> direction.append("SE");
                    case 1 -> direction.append("NE");
                    case 3 -> direction.append("SEN");
                }
                break;
            case 3:
                switch (y) {
                    case -4 -> direction.append("EES");
                    case -2 -> direction.append("WSE");
                    case 0 -> direction.append("EE");
                    case 2 -> direction.append("WNE");
                    case 4 -> direction.append("EEN");
                }
                break;
            case 4:
                switch (y) {
                    case -3 -> direction.append("SSE");
                    case -1 -> direction.append("NSE");
                    case 1 -> direction.append("SNE");
                    case 3 -> direction.append("NNE");
                }
                break;
        }

        return direction.toString();
    }
}