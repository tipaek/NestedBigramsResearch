import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        JavaSolution.main(args);
    }
}

class JavaSolution {
    public static void main(String[] args) throws IOException {
        boolean isDebugMode = Arrays.asList(args).contains("DEBUG_MODE");
        boolean isInteractiveMode = Arrays.asList(args).contains("INTERACTIVE_MODE");

        Process process = null;
        if (isInteractiveMode) {
            process = Runtime.getRuntime().exec("python3 local_testing_tool.py 2");
        }

        try (Reader reader = getReader(isInteractiveMode, process, isDebugMode);
             OutputStreamWriter writer = getWriter(isInteractiveMode, process)) {

            long startTime = System.nanoTime();
            try (Scanner scanner = new Scanner(new BufferedReader(reader));
                 PrintWriter out = new PrintWriter(new BufferedWriter(writer))) {

                int testCases = scanner.nextInt();
                JavaSolution solution = new JavaSolution(scanner, out);

                for (int i = 0; i < testCases; i++) {
                    out.printf("Case #%d: ", i + 1);
                    solution.run();
                }

            } finally {
                if (isDebugMode) {
                    long endTime = System.nanoTime();
                    System.out.printf(">> Execution time: %.6f\n", (endTime - startTime) / 1_000_000_000.0);
                    System.out.flush();
                }
            }
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static Reader getReader(boolean isInteractiveMode, Process process, boolean isDebugMode) throws IOException {
        if (isInteractiveMode) {
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        } else if (isDebugMode) {
            return new FileReader("input.txt");
        } else {
            return new InputStreamReader(System.in);
        }
    }

    private static OutputStreamWriter getWriter(boolean isInteractiveMode, Process process) {
        if (isInteractiveMode) {
            return new OutputStreamWriter(process.getOutputStream());
        } else {
            return new OutputStreamWriter(System.out);
        }
    }

    private final Scanner in;
    private final PrintWriter out;

    private JavaSolution(Scanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    private String run(long x, long y) {
        int bits = 33;
        boolean[] xBits = new boolean(bits);
        boolean[] yBits = new boolean(bits);
        int maxIndex = 0;

        for (int i = 0; i < bits; i++) {
            xBits[i] = (x & 1) != 0;
            x >>= 1;
            yBits[i] = (y & 1) != 0;
            y >>= 1;
            if (xBits[i] || yBits[i]) maxIndex = i;
        }

        StringBuilder path = new StringBuilder();
        for (int i = 0; i <= maxIndex; ) {
            if (xBits[i] && yBits[i]) {
                return null;
            }
            if (xBits[i]) {
                while (i + 1 <= maxIndex && !xBits[i + 1] && !yBits[i + 1]) {
                    path.append('W');
                    i++;
                }
                path.append('E');
                i++;
            } else {
                if (!yBits[i]) {
                    return null;
                }
                while (i + 1 <= maxIndex && !xBits[i + 1] && !yBits[i + 1]) {
                    path.append('S');
                    i++;
                }
                path.append('N');
                i++;
            }
        }
        return path.toString();
    }

    private String run2(long x, long y) {
        String result = run(x, y);
        if (result != null) return result;

        long t = 1;
        while (t <= x || t <= y) {
            t <<= 1;
        }

        result = run(t - x, y);
        if (result != null) {
            result = result.replace('W', 'T').replace('E', 'W').replace('T', 'E') + 'E';
        } else {
            result = run(x, t - y);
            if (result != null) {
                result = result.replace('N', 'T').replace('S', 'N').replace('T', 'S') + 'N';
            }
        }
        return result;
    }

    private String g(String s1, String s2) {
        if (s1 == null) return s2;
        if (s2 == null) return s1;
        return s1.length() > s2.length() ? s2 : s1;
    }

    private String gg(String s, char ch) {
        return s == null ? null : s + ch;
    }

    private String brun(long x, long y, int step) {
        if (x == 0 && y == 0) {
            return "";
        }
        if (step > 8) {
            return null;
        }

        return g(
                g(gg(brun(x + (1 << step), y, step + 1), 'W'), gg(brun(x - (1 << step), y, step + 1), 'E')),
                g(gg(brun(x, y + (1 << step), step + 1), 'N'), gg(brun(x, y - (1 << step), step + 1), 'S'))
        );
    }

    private void run() {
        int x = in.nextInt();
        int y = in.nextInt();

        boolean reverseX = x < 0;
        x = Math.abs(x);
        boolean reverseY = y < 0;
        y = Math.abs(y);

        String result = run2(x, y);
        if (result == null) {
            result = "IMPOSSIBLE";
        } else {
            if (reverseX) {
                result = result.replace('W', 'T').replace('E', 'W').replace('T', 'E');
            }
            if (reverseY) {
                result = result.replace('N', 'T').replace('S', 'N').replace('T', 'S');
            }
        }
        out.println(result);
    }
}