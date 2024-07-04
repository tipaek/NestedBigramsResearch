import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader br;
    static StringTokenizer sc;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        sc = new StringTokenizer("");

        TaskD solver = new TaskD();
        solver.solve();

        br.close();
        out.close();
    }

    static class TaskD {
        public void solve() throws IOException {
            int t = nextInt();

            for (int ii = 0; ii < t; ii++) {
                int n = nextInt();
                int[][] arr = new int[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        arr[i][j] = nextInt();
                    }
                }

                int sum = 0;
                for (int i = 0; i < n; i++) {
                    sum += arr[i][i];
                }

                int repeatRow = 0;
                for (int row = 0; row < n; row++) {
                    if (hasDuplicate(arr[row])) {
                        repeatRow++;
                    }
                }

                int repeatCol = 0;
                for (int col = 0; col < n; col++) {
                    if (hasDuplicate(getColumn(arr, col))) {
                        repeatCol++;
                    }
                }

                out.println("Case #" + (ii + 1) + ": " + sum + " " + repeatRow + " " + repeatCol);
            }
        }

        private boolean hasDuplicate(int[] array) {
            Set<Integer> set = new HashSet<>();
            for (int num : array) {
                if (!set.add(num)) {
                    return true;
                }
            }
            return false;
        }

        private int[] getColumn(int[][] matrix, int col) {
            int[] column = new int[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                column[i] = matrix[i][col];
            }
            return column;
        }
    }

    static String nextToken() throws IOException {
        while (!sc.hasMoreTokens()) {
            String line = br.readLine();
            if (line == null) {
                return null;
            }
            sc = new StringTokenizer(line.trim());
        }
        return sc.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    static long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    static int[] nextIntArray(int n) throws IOException {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = nextInt();
        }
        return array;
    }

    static long[] nextLongArray(int n) throws IOException {
        long[] array = new long[n];
        for (int i = 0; i < n; i++) {
            array[i] = nextLong();
        }
        return array;
    }

    static char[] nextCharArray() throws IOException {
        return nextToken().toCharArray();
    }

    static int getMin(int[] arr) {
        int min = arr[0];
        for (int val : arr) {
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    static int getMax(int[] arr) {
        int max = arr[0];
        for (int val : arr) {
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    static void sortAsc(int[] arr) {
        Arrays.sort(arr);
    }

    static void sortDesc(int[] arr) {
        Integer[] boxedArray = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        Arrays.sort(boxedArray, Collections.reverseOrder());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = boxedArray[i];
        }
    }
}