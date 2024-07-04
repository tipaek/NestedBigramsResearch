import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class Solution {

    private static final boolean FROM_FILE = false;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = FROM_FILE ? new BufferedReader(new FileReader("C2020R1CPC.in")) : new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = FROM_FILE ? new BufferedWriter(new FileWriter("C2020R1CPC.out")) : new BufferedWriter(new OutputStreamWriter(System.out))) {

            int tests = Integer.parseInt(br.readLine());

            for (int i = 0; i < tests; i++) {
                String[] firstLine = br.readLine().split("\\s+");
                int n = Integer.parseInt(firstLine[0]);
                int d = Integer.parseInt(firstLine[1]);

                String[] secondLine = br.readLine().split("\\s+");
                Double[] sl = Arrays.stream(secondLine).map(Double::valueOf).toArray(Double[]::new);
                Arrays.sort(sl, Collections.reverseOrder());

                String answer = processTestCase(n, d, sl);
                bw.write("Case #" + (i + 1) + ": " + answer + "\n");
                bw.flush();
            }
        }
    }

    private static String processTestCase(int n, int d, Double[] sl) {
        if (d == 2) {
            return processD2(n, sl);
        } else {
            return processD3(n, sl);
        }
    }

    private static String processD2(int n, Double[] sl) {
        if (n == 1) {
            return "1"; // need to cut in half
        } else {
            for (int k = 0; k < sl.length - 1; k++) {
                if (sl[k].equals(sl[k + 1])) {
                    return "0"; // equal pieces
                }
            }
            return "1";
        }
    }

    private static String processD3(int n, Double[] sl) {
        if (n == 1) {
            return "2"; // need to cut in 3
        } else if (n == 2) {
            return processD3N2(sl);
        } else {
            return processD3N3OrMore(sl);
        }
    }

    private static String processD3N2(Double[] sl) {
        if (sl[0] - sl[1] == sl[1]) {
            return "1"; // need to cut first in half
        } else {
            return "2"; // cut second in half, cut first to fit second
        }
    }

    private static String processD3N3OrMore(Double[] sl) {
        for (int k = 0; k < sl.length - 2; k++) {
            if (sl[k].equals(sl[k + 1]) && sl[k + 1].equals(sl[k + 2])) {
                return "0"; // 3 equal pieces
            }
        }

        for (int k = 1; k < sl.length - 1; k++) {
            if (sl[k].equals(sl[k + 1])) {
                return "1"; // find 2 equal and 1 bigger than them
            }
        }

        for (int k = 0; k < sl.length - 1; k++) {
            for (int k2 = k + 1; k2 < sl.length; k2++) {
                if (sl[k] - sl[k2] == sl[k2]) {
                    return "1"; // find 1 double of other
                }
            }
        }

        return "2";
    }
}