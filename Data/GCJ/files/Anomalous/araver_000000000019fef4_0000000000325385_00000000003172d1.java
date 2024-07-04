import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class Solution {

    private static final boolean FROM_FILE = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = createBufferedReader();
        BufferedWriter bw = createBufferedWriter();

        try {
            int tests = Integer.parseInt(br.readLine());

            for (int i = 0; i < tests; i++) {
                String[] firstLine = br.readLine().split("\\s+");
                int n = Integer.parseInt(firstLine[0]);
                int d = Integer.parseInt(firstLine[1]);

                Long[] sl = Arrays.stream(br.readLine().split("\\s+"))
                                  .map(Long::valueOf)
                                  .toArray(Long[]::new);

                Arrays.sort(sl, Collections.reverseOrder());

                String answer = calculateAnswer(n, d, sl);

                bw.write("Case #" + (i + 1) + ": " + answer + "\n");
                bw.flush();
            }
        } finally {
            closeResources(br, bw);
        }
    }

    private static BufferedReader createBufferedReader() throws FileNotFoundException {
        if (FROM_FILE) {
            return new BufferedReader(new FileReader("C2020R1CPC.in"));
        } else {
            return new BufferedReader(new InputStreamReader(System.in));
        }
    }

    private static BufferedWriter createBufferedWriter() throws IOException {
        if (FROM_FILE) {
            File file = new File("C2020R1CPC.out");
            if (!file.exists()) {
                file.createNewFile();
            }
            return new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        } else {
            return new BufferedWriter(new OutputStreamWriter(System.out));
        }
    }

    private static String calculateAnswer(int n, int d, Long[] sl) {
        if (d == 2) {
            return calculateForD2(n, sl);
        } else {
            return calculateForD3(n, sl);
        }
    }

    private static String calculateForD2(int n, Long[] sl) {
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

    private static String calculateForD3(int n, Long[] sl) {
        if (n == 1) {
            return "2";
        } else if (n == 2) {
            return sl[0] - sl[1] == sl[1] ? "1" : "2";
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

            for (int k = 0; k < sl.length - 2; k++) {
                for (int k2 = k + 1; k2 < sl.length - 1; k2++) {
                    for (int k3 = k2 + 1; k3 < sl.length; k3++) {
                        if (sl[k].equals(sl[k3] + sl[k2])) {
                            return "1";
                        }
                    }
                }
            }

            return "2";
        }
    }

    private static void closeResources(BufferedReader br, BufferedWriter bw) throws IOException {
        if (FROM_FILE) {
            br.close();
            bw.close();
        }
    }
}