import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author lewin
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        SecurityUpdate solver = new SecurityUpdate();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class SecurityUpdate {
        public static int INF = 1 << 29;
        List<SecurityUpdate.Edge>[] graph;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.nextInt(), m = in.nextInt();
            int[] dist = new int[n];
            List<Integer> node1 = new ArrayList<>();
            List<Integer> node2 = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                dist[i] = in.nextInt();
                if (dist[i] < 0) {
                    dist[i] = -dist[i];
                    node1.add(i);
                } else {
                    node2.add(i);
                }
            }
            node1.sort(Comparator.comparingInt(x -> dist[x]));
            node2.sort(Comparator.comparingInt(x -> dist[x]));
            graph = LUtils.genArrayList(n);
            for (int i = 0; i < m; i++) {
                int a = in.nextInt() - 1, b = in.nextInt() - 1;
                graph[a].add(new SecurityUpdate.Edge(b, i));
                graph[b].add(new SecurityUpdate.Edge(a, i));
            }

            boolean[] vis = new boolean[n];
            vis[0] = false;
            int[] adist = new int[n];
            Arrays.fill(adist, INF);
            adist[0] = 0;

            int ap1 = 0, ap2 = 0;

            int[] res = new int[m];
            int seen = 1;
            while (ap1 < node1.size() || ap2 < node2.size()) {
                int nweight = ap2 < node2.size() ? dist[node2.get(ap2)] : INF;
                int first = ap1;
                int minedge = INF;
                int maxedge = 0;
                while (ap1 < node1.size() && dist[node1.get(ap1)] == seen) {
                    for (SecurityUpdate.Edge x : graph[node1.get(ap1)]) {
                        minedge = Math.min(minedge, adist[x.to]);
                        if (adist[x.to] < INF)
                            maxedge = Math.max(maxedge, adist[x.to]);
                    }
                    ap1++;
                }
                nweight = Math.max(minedge + 1000, Math.min(maxedge + 1, nweight));
                for (int i = first; i < ap1; i++) {
                    seen++;
                    int cur = node1.get(i);
                    adist[cur] = nweight;
                    for (SecurityUpdate.Edge x : graph[cur]) {
                        if (res[x.id] == 0 && adist[x.to] < INF) {
                            res[x.id] = Math.max(1, adist[cur] - adist[x.to]);
                        }
                    }
                }
            }
            out.printf("Case #%d: ", testNumber);
            out.println(res);
        }

        static class Edge {
            public int to;
            public int id;

            public Edge(int to, int id) {
                this.to = to;
                this.id = id;
            }

        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(int[] array) {
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(array[i]);
            }
        }

        public void println(int[] array) {
            print(array);
            writer.println();
        }

        public void printf(String format, Object... objects) {
            writer.printf(format, objects);
        }

        public void close() {
            writer.close();
        }

    }

    static class LUtils {
        public static <E> List<E>[] genArrayList(int size) {
            return Stream.generate(ArrayList::new).limit(size).toArray(List[]::new);
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (this.numChars == -1) {
                throw new InputMismatchException();
            } else {
                if (this.curChar >= this.numChars) {
                    this.curChar = 0;

                    try {
                        this.numChars = this.stream.read(this.buf);
                    } catch (IOException var2) {
                        throw new InputMismatchException();
                    }

                    if (this.numChars <= 0) {
                        return -1;
                    }
                }

                return this.buf[this.curChar++];
            }
        }

        public int nextInt() {
            int c;
            for (c = this.read(); isSpaceChar(c); c = this.read()) {
                ;
            }

            byte sgn = 1;
            if (c == 45) {
                sgn = -1;
                c = this.read();
            }

            int res = 0;

            while (c >= 48 && c <= 57) {
                res *= 10;
                res += c - 48;
                c = this.read();
                if (isSpaceChar(c)) {
                    return res * sgn;
                }
            }

            throw new InputMismatchException();
        }

        public String next() {
            int c;
            while (isSpaceChar(c = this.read())) {
                ;
            }

            StringBuilder result = new StringBuilder();
            result.appendCodePoint(c);

            while (!isSpaceChar(c = this.read())) {
                result.appendCodePoint(c);
            }

            return result.toString();
        }

        public static boolean isSpaceChar(int c) {
            return c == 32 || c == 10 || c == 13 || c == 9 || c == -1;
        }

    }
}

