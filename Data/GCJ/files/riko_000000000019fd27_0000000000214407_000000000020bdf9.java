import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Eric
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastReader in = new FastReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task2020QC solver = new Task2020QC();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class Task2020QC {
        static PrintStream errorStream;

        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] s = new int[n];
            int[] e = new int[n];
            for (int i = 0; i < n; i++) {
                s[i] = in.nextInt();
                e[i] = in.nextInt();
            }
            String res = solveMe(n, s, e);
            out.print("Case #" + testNumber + ": ");
            out.println(res);
        }

        private String solveMe(int n, int[] s, int[] e) {
            int[] order = getRange0toNMinus1(n);
            Task2020QC.Tuple2<Integer>[] tuples = toArrTuple2(toArray(order), toArray(s), toArray(e));
            Arrays.sort(tuples, (t1, t2) -> Integer.compare(t1.second(), t2.second()));
            d("tuples = " + stringMe(tuples));
            char[] res = new char[n];
            int end1 = 0;
            int end2 = 0;
            for (int i = 0; i < n; i++) {
                if (tuples[i].second() >= end1) {
                    res[i] = 'J';
                    end1 = tuples[i].third();
                } else if (tuples[i].second() >= end2) {
                    res[i] = 'C';
                    end2 = tuples[i].third();
                } else {
                    return "IMPOSSIBLE";
                }
            }

            char[] ans = new char[n];
            for (int i = 0; i < n; i++) {
                ans[tuples[i].first()] = res[i];
            }

            return new String(ans);
        }

        public static int[] getRange(int start, int end) {
            if (end < start)
                return new int[0];
            int[] a = new int[end - start + 1];
            for (int i = 0; i < a.length; i++)
                a[i] = start + i;
            return a;
        }

        public static int[] getRange0toNMinus1(int n) {
            return getRange(0, n - 1);
        }

        public <T> String stringMe(T[] a, String separator) {
            int n = a.length;
            StringBuilder res = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (i > 0)
                    res.append(separator);
                res.append(a[i]);
            }
            return res.toString();
        }

        public <T> String stringMe(T[] a) {
            return stringMe(a, " ");
        }

        public static Integer[] toArray(int[] a) {
            return Arrays.stream(a).boxed().toArray(Integer[]::new);
        }

        public <T> Task2020QC.Tuple2<T>[] toArrTuple2(T[]... list) {
            for (int i = 1; i < list.length; i++)
                assertEquals(list[0].length, list[i].length, "Arrays must be of equal size (problem with i = " + i + ")");
            Task2020QC.Tuple2<T>[] res = new Task2020QC.Tuple2[list[0].length];
            for (int i = 0; i < list[0].length; i++) {
                Class<?> componentType = list[0].getClass().getComponentType();
                T[] row = (T[]) Array.newInstance(componentType, list.length);
                for (int j = 0; j < list.length; j++) {
                    row[j] = list[j][i];
                }
                res[i] = new Task2020QC.Tuple2<T>(row);
            }

            return res;
        }

        public static void d(String message) {
            if (errorStream == null)
                errorStream = System.err;
            errorStream.println(message);
            errorStream.flush();
        }

        public static void assertTrue(boolean shouldBeTrue, String message) {
            if (!shouldBeTrue)
                throw new RuntimeException(message);
        }

        public static String getExpected(Object expected, Object actual) {
            return "expected = " + expected + "; actual = " + actual + " ; ";
        }

        public static void assertEquals(int val1, int val2, String message) {
            assertTrue(val1 == val2, getExpected(val1, val2) + message);
        }

        public static class Tuple2<T> implements Comparable<Task2020QC.Tuple2<T>> {
            T[] elements;

            public Tuple2(T... elements) {
                this.elements = elements;
            }

            public String toString() {
                return Arrays.deepToString(elements);
            }

            public T first() {
                return elements[0];
            }

            public T second() {
                return elements[1];
            }

            public T third() {
                return elements[2];
            }

            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Task2020QC.Tuple2 tuple = (Task2020QC.Tuple2) o;
                return Arrays.equals(elements, tuple.elements);
            }

            public int hashCode() {
                return Objects.hash(elements);
            }

            public int compareTo(Task2020QC.Tuple2<T> t2) {
                if (t2.elements.length != elements.length)
                    throw new RuntimeException("Elements has 2 different sizes!");
                if (getClass() != t2.getClass())
                    throw new RuntimeException("Elements are not of the same class!");
                for (int i = 0; i < elements.length; i++) {
                    if (!(elements[i] instanceof Comparable)) {
                        throw new RuntimeException("Element is not comparable!");
                    }
                    int val = ((Comparable<T>) elements[i]).compareTo(t2.elements[i]);
                    if (val != 0)
                        return val;
                }
                return 0;
            }

        }

    }

    static class FastReader {
        public BufferedReader br;
        public StringTokenizer st;
        String context = null;

        public FastReader(InputStream in) {
            this(new InputStreamReader(in));
        }

        public FastReader(InputStreamReader input) {
            br = new BufferedReader(input);
        }

        public String next() {
            if (context != null) {
                System.err.print("[" + context + "] Wait for input ...");
            }
            while (st == null || !st.hasMoreElements()) {
                try {
                    String s = br.readLine();
                    if (s == null)
                        return null;
                    st = new StringTokenizer(s);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Could not read");
                }
            }
            String res = st.nextToken();
            if (context != null) {
                System.err.println("[" + context + "] ... received : " + res);
            }
            return res;
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

