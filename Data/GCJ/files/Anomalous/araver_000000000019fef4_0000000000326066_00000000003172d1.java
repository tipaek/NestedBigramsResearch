import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class Solution {

    private static final boolean FROM_FILE = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br;
        BufferedWriter bw;

        if (FROM_FILE) {
            br = new BufferedReader(new FileReader("C2020R1CPC.in"));
        } else {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        try {
            if (FROM_FILE) {
                File file = new File("C2020R1CPC.out");
                if (!file.exists()) {
                    file.createNewFile();
                }
                bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            } else {
                bw = new BufferedWriter(new OutputStreamWriter(System.out));
            }

            int tests = Integer.parseInt(br.readLine());

            for (int i = 0; i < tests; i++) {
                String[] input = br.readLine().split("\\s+");
                int n = Integer.parseInt(input[0]);
                int d = Integer.parseInt(input[1]);

                Long[] sl = Arrays.stream(br.readLine().split("\\s+"))
                                  .map(Long::valueOf)
                                  .toArray(Long[]::new);

                Arrays.sort(sl, Collections.reverseOrder());

                String answer = calculateCuts(n, d, sl);

                bw.write("Case #" + (i + 1) + ": " + answer + "\n");
                bw.flush();
            }

            if (FROM_FILE) {
                bw.close();
            }
        } finally {
            if (FROM_FILE) {
                br.close();
            }
        }
    }

    private static String calculateCuts(int n, int d, Long[] sl) {
        if (d == 2) {
            return calculateCutsForD2(n, sl);
        } else {
            return calculateCutsForD3(n, sl);
        }
    }

    private static String calculateCutsForD2(int n, Long[] sl) {
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

    private static String calculateCutsForD3(int n, Long[] sl) {
        if (n == 1) {
            return "2"; // need to cut in 3
        } else if (n == 2) {
            return (sl[0] - sl[1] == sl[1]) ? "1" : "2"; // need at least 1 cut
        } else {
            if (hasThreeEqualPieces(sl)) {
                return "0";
            }
            if (hasTwoEqualPieces(sl)) {
                return "1";
            }
            if (hasOneDoubleOfOther(sl)) {
                return "1";
            }
            if (hasOneSumOfTwoOthers(sl)) {
                return "1";
            }
            return "2";
        }
    }

    private static boolean hasThreeEqualPieces(Long[] sl) {
        for (int k = 0; k < sl.length - 2; k++) {
            if (sl[k].equals(sl[k + 1]) && sl[k + 1].equals(sl[k + 2])) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTwoEqualPieces(Long[] sl) {
        for (int k = 1; k < sl.length - 1; k++) {
            if (sl[k].equals(sl[k + 1])) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasOneDoubleOfOther(Long[] sl) {
        for (int k = 0; k < sl.length - 1; k++) {
            for (int k2 = k + 1; k2 < sl.length; k2++) {
                if (sl[k] - sl[k2] == sl[k2]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasOneSumOfTwoOthers(Long[] sl) {
        for (int k = 0; k < sl.length - 2; k++) {
            for (int k2 = k + 1; k2 < sl.length - 1; k2++) {
                for (int k3 = k2 + 1; k3 < sl.length; k3++) {
                    if (sl[k].equals(sl[k3] + sl[k2])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}