//package com.prituladima.task3;

import java.io.*;
import java.util.*;

import static java.lang.Double.min;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public final class Solution {

    private final String impossible = "IMPOSSIBLE";

    private void solve() {
        int T = nextInt();
        outer:
        for (int testNumber = 1; testNumber <= T; testNumber++) {
            int n = nextInt();
            List<Act> input = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                input.add(new Act(j, nextInt(), nextInt()));
            }

            int[] partialSums = new int[24 * 61];
            for (Act act : input) {
                partialSums[act.startTime]++;
                partialSums[act.endTime]--;
            }
//            int k = 10;
            for (int j = 1; j < partialSums.length; j++) {
                partialSums[j] += partialSums[j - 1];
                if (partialSums[j] > 2) {
                    printf("Case #%d: %s\n", j, impossible);
//                    println(impossible);
                    continue outer;
                }
            }

            //solution exist 100%

//            print(Arrays.toString(partialSums));
            char[] ans = new char[n];
//            Arrays.fill(ans, 'C');

            Graph graph = new Graph();
            for (Act from : input) {
                for (Act to : input) {
                    if (!from.equals(to)) {
                        graph.computeIfAbsent(from, (key) -> new HashSet<>());
                        graph.computeIfAbsent(to, (key) -> new HashSet<>());
                        if (intersect(from, to)) {
                            graph.get(from).add(to);
                            graph.get(to).add(from);
                        }
                    }
                }
            }

            boolean[] used = new boolean[n + 20];
            for (Act act : input) {
                if (!used[act.getId()]) {
                    dfs(used, act, ans, 0, graph);
                }
            }
            printf("Case #%d: %s\n", testNumber, String.valueOf(ans));

        }

    }

    private void dfs(boolean[] used, Act from, char[] ans, int deep, Graph graph) {

        used[from.getId()] = true;

        if (deep % 2 == 0) {
            ans[from.getId()] = 'J';
        } else {
            ans[from.getId()] = 'C';
        }

        for (Act to : graph.getOrDefault(from, new HashSet<>())) {
            if (!used[to.getId()]) {
                dfs(used, to, ans, deep + 1, graph);
            }
        }

    }


    private boolean intersect(Act act1, Act act2) {
//        return !(act1.getEndTime() <= act2.getStartTime() || act2.getEndTime() <= act1.getStartTime());
        return Util.doIntersect(
                new SPoint(act1.getId(), act1.getStartTime(), 0),
                new SPoint(act1.getId(), act1.getEndTime() - 1, 0),
                new SPoint(act2.getId(), act2.getStartTime(), 0),
                new SPoint(act2.getId(), act2.getEndTime() - 1, 0)
        );
    }

    public class Segment {

        SPoint p;
        SPoint q;

        public Segment(SPoint p, SPoint q) {
            if (p.x == q.x) {
                this.p = p;
                this.q = q;
            } else if (p.x > q.x) {
                this.p = q;
                this.q = p;
                this.q.isLeft = true;
            } else {
                this.p = p;
                this.q = q;
                this.p.isLeft = true;
            }

            this.p.s = this;
            this.q.s = this;
        }


        public SPoint getP() {
            return p;
        }

        public SPoint getQ() {
            return q;
        }
    }

    public class SPoint {

        int id;
        int x;
        int y;

        Segment s;

        boolean isLeft;

        public SPoint(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public int getId() {
            return id;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Segment getS() {
            return s;
        }

        public boolean isLeft() {
            return isLeft;
        }
    }


    private static class Util {

        public static final double EPS = 1E-9;

        public static boolean doIntersect(Segment a, Segment b) {
            return doIntersect(a.p, a.q, b.p, b.q);
        }

        public static boolean doIntersect(SPoint p1, SPoint q1, SPoint p2, SPoint q2) {
            int o1 = orientation(p1, q1, p2);
            int o2 = orientation(p1, q1, q2);
            int o3 = orientation(p2, q2, p1);
            int o4 = orientation(p2, q2, q1);

            if (o1 != o2 && o3 != o4)
                return true;

            return o1 == 0 && onSegment(p1, q1, p2)
                    ||
                    o2 == 0 && onSegment(p1, q1, q2)
                    ||
                    o3 == 0 && onSegment(p2, q2, p1)
                    ||
                    o4 == 0 && onSegment(p2, q2, q1);

        }


        public static int orientation(SPoint p, SPoint q, SPoint r) {
            double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
            return abs(val) < EPS ? 0 : val > 0 ? 1 : -1;
        }


        public static boolean onSegment(SPoint p, SPoint q, SPoint r) {
            return min(p.x, q.x) <= r.x && r.x <= max(p.x, q.x) && min(p.y, q.y) <= r.y && r.y <= max(p.y, q.y);
        }

    }


    private static class Graph extends HashMap<Act, Collection<Act>> {
//        public Graph(int initialCapacity) {
//            super(initialCapacity);
//            for(int i = 0 ; i < initialCapacity;i++){
//                put()
//            }
//        }
    }

    private static class Act {
        int id;
        int startTime;
        int endTime;

        public Act(int id, int startTime, int endTime) {
            this.id = id;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Act act = (Act) o;
            return id == act.id &&
                    startTime == act.startTime &&
                    endTime == act.endTime;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, startTime, endTime);
        }
    }


    public static void main(String[] args) {
        new Solution().run();
    }

    private BufferedReader reader;
    private StringTokenizer tokenizer;
    private PrintWriter writer;

    private void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out))) {
            this.reader = reader;
            this.writer = writer;
            this.tokenizer = null;
            solve();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    private int nextInt(int radix) {
        return parseInt(nextToken(), radix);
    }

    private int nextInt() {
        return parseInt(nextToken());
    }

    private long nextLong(int radix) {
        return parseLong(nextToken(), radix);
    }

    private long nextLong() {
        return parseLong(nextToken());
    }

    private double nextDouble() {
        return parseDouble(nextToken());
    }

    private int[] nextArr(int size) {
        return stream(new int[size]).map(c -> nextInt()).toArray();
    }

    private long[] nextArrL(int size) {
        return stream(new long[size]).map(c -> nextLong()).toArray();
    }

    private double[] nextArrD(int size) {
        return stream(new double[size]).map(c -> nextDouble()).toArray();
    }

    private char[][] nextCharMatrix(int n) {
        return range(0, n).mapToObj(i -> nextToken().toCharArray()).toArray(char[][]::new);
    }

    private int[][] nextIntMatrix(final int n, final int m) {
        return range(0, n).mapToObj(i -> nextArr(m)).toArray(int[][]::new);
    }

    private double[][] nextDoubleMatrix(final int n, final int m) {
        return range(0, n).mapToObj(i -> nextArrD(m)).toArray(double[][]::new);
    }

    private String nextToken() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    private void printf(String format, Object... args) {
        writer.printf(format, args);
    }

    private void print(Object o) {
        writer.print(o);
    }

    private void println() {
        writer.println();
    }

    private void println(Object o) {
        print(o);
        println();
    }

    private void flush() {
        writer.flush();
    }

}