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
        Emacs solver = new Emacs();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class Emacs {
        int n;
        int q;
        int[] l;
        int[] r;
        int[] p;
        char[] s;
        int nidx;
        int curpos;
        List<Integer>[] graph;
        int[] pos;
        int[] type;
        int[] cyclen;
        int[] prelen;
        int[] depth;
        int nt;
        int[][][][] distpar;
        int[][] anc;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            n = in.nextInt();
            q = in.nextInt();
            nt = n / 2;
            s = in.next().toCharArray();
            pos = new int[n];
            type = new int[n];
            nidx = 0;
            curpos = 0;
            l = in.readIntArray(n);
            r = in.readIntArray(n);
            p = in.readIntArray(n);
            graph = LUtils.genArrayList(n);
            cyclen = new int[nt];
            depth = new int[n];
            dfs(0);
            distpar = new int[20][nt][2][2];
            AUtils.deepFill(distpar, 1 << 29);
            anc = new int[20][nt];
            prelen = new int[nt];
            dfs2(0);
            for (int level = 1; level < 20; level++) {
                for (int node = 0; node < nt; node++) {
                    anc[level][node] = anc[level - 1][anc[level - 1][node]];
                    for (int i = 0; i <= 1; i++) {
                        for (int j = 0; j <= 1; j++) {
                            for (int k = 0; k <= 1; k++) {
                                distpar[level][node][i][k] = Math.min(distpar[level][node][i][k],
                                        distpar[level - 1][node][j][k] + distpar[level - 1][anc[level - 1][node]][i][j]);
                            }
                        }
                    }
                }
            }
            int[] ss = in.readIntArrayAndDecrementOne(q), es = in.readIntArrayAndDecrementOne(q);
            long ans = 0;
            for (int i = 0; i < q; i++) {
                int s = ss[i], e = es[i];
                if (depth[pos[s]] > depth[pos[e]]) {
                    int t = s;
                    s = e;
                    e = t;
                }
                int spos = pos[s], epos = pos[e], sside = type[s], eside = type[e];
                int[] dps = new int[2];
                dps[sside] = 0;
                dps[1 - sside] = 1;
                int[] dpe = new int[2];
                dpe[eside] = 0;
                dpe[1 - eside] = 1;
                while (depth[spos] != depth[epos] || anc[0][spos] != anc[0][epos]) {
                    int diff = depth[epos] - depth[spos];
                }
            }
            out.printf("Case #%d: %d\n", testNumber, ans);
        }

        void dfs2(int node) {
            int clen = 0;
            for (int next : graph[node]) {
                clen += 2;
                dfs2(next);
                anc[0][next] = node;
                distpar[0][next][0][1] = Math.min(clen, cyclen[node] - clen);
                distpar[0][next][0][0] = Math.min(clen - 1, cyclen[node] - clen + 1);
                distpar[0][next][1][0] = Math.min(clen, cyclen[node] - clen);
                distpar[0][next][1][1] = Math.min(clen + 2, cyclen[node] - clen - 2);
                prelen[next] = clen;
            }
        }

        void dfs(int node) {
            cyclen[node] = 2;
            while (curpos < s.length && s[curpos] == '(') {
                cyclen[node] += 2;
                int q = nidx;
                pos[curpos] = q;
                type[curpos] = 0;
                ++curpos;

                graph[node].add(q);
                depth[q] = depth[node] + 1;
                dfs(nidx++);

                assert s[curpos] == ')';
                pos[curpos] = q;
                type[curpos] = 1;
                ++curpos;
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

    static class AUtils {
        public static void deepFill(int[][][][] x, int val) {
            for (int[][][] y : x) deepFill(y, val);
        }

        public static void deepFill(int[][][] x, int val) {
            for (int[][] y : x) deepFill(y, val);
        }

        public static void deepFill(int[][] x, int val) {
            for (int[] y : x) deepFill(y, val);
        }

        public static void deepFill(int[] x, int val) {
            Arrays.fill(x, val);
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

        public int[] readIntArray(int tokens) {
            int[] ret = new int[tokens];
            for (int i = 0; i < tokens; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        public int[] readIntArrayAndDecrementOne(int tokens) {
            int[] ret = new int[tokens];
            for (int i = 0; i < tokens; i++) {
                ret[i] = nextInt() - 1;
            }
            return ret;
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

