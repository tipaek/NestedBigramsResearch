import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class Solution {

    private static final boolean FROM_FILE = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = FROM_FILE ? new BufferedReader(new FileReader("C2020R1CPC.in")) : new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = FROM_FILE ? new BufferedWriter(new FileWriter("C2020R1CPC.out")) : new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int tests = Integer.parseInt(br.readLine());

            for (int i = 0; i < tests; i++) {
                String[] firstLine = br.readLine().split("\\s+");
                int n = Integer.parseInt(firstLine[0]);
                int d = Integer.parseInt(firstLine[1]);

                String[] secondLine = br.readLine().split("\\s+");
                Long[] sl = Arrays.stream(secondLine).map(Long::valueOf).toArray(Long[]::new);
                Arrays.sort(sl, Collections.reverseOrder());

                String answer = determineAnswer(n, d, sl);
                
                bw.write("Case #" + (i + 1) + ": " + answer + "\n");
                bw.flush();
            }
        } finally {
            if (FROM_FILE) {
                br.close();
                bw.close();
            }
        }
    }

    private static String determineAnswer(int n, int d, Long[] sl) {
        if (d == 2) {
            return handleCaseD2(n, sl);
        } else {
            return handleCaseD3(n, sl);
        }
    }

    private static String handleCaseD2(int n, Long[] sl) {
        if (n == 1) {
            return "1";
        } else {
            for (int k = 0; k < sl.length - 1; k++) {
                if (sl[k].equals(sl[k + 1])) {
                    return "0";
                }
            }
            return "1";
        }
    }

    private static String handleCaseD3(int n, Long[] sl) {
        if (n == 1) {
            return "2";
        } else if (n == 2) {
            return (sl[0] - sl[1] == sl[1]) ? "1" : "2";
        } else {
            for (int k = 0; k < sl.length - 2; k++) {
                if (sl[k].equals(sl[k + 1]) && sl[k + 1].equals(sl[k + 2])) {
                    return "0";
                }
            }

            for (int k = 1; k < sl.length - 1; k++) {
                if (sl[k].equals(sl[k + 1])) {
                    return "1";
                }
            }

            for (int k = 0; k < sl.length - 1; k++) {
                for (int k2 = k + 1; k2 < sl.length; k2++) {
                    if (sl[k] - sl[k2] == sl[k2]) {
                        return "1";
                    }
                }
            }

            return "2";
        }
    }
}