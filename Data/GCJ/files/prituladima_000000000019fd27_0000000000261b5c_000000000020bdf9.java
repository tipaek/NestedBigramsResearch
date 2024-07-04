//package com.prituladima.task3;

import java.io.*;
import java.util.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
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
            int k = 10;
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
            char[] ans = new char[input.size()];
            Arrays.fill(ans, 'C');

            Graph graph = new Graph();
            for (Act from : input) {
                for (Act to : input) {
                    if (!to.equals(from)) {
                        if (intersect(to, from)) {
                            graph.computeIfAbsent(from, (key) -> new HashSet<>());
                            graph.get(from).add(to);
                        }
                    }
                }
            }

            boolean[] used = new boolean[input.size() + 20];
            for (int i = 0; i < input.size(); i++) {
                if (!used[input.get(i).getId()]) {
                    dfs(used, input.get(i), ans, 0, graph);
                }
            }
            printf("Case #%d: %s\n", testNumber, String.valueOf(ans));

        }

    }

    private void dfs(boolean[] used, Act from, char[] ans, int deep, Graph graph) {
        used[from.getId()] = true;
        if (deep % 2 == 0) {
            ans[from.getId()] = 'J';
        }
        for (Act to : graph.get(from)) {
            if (!used[to.getId()]) {
                dfs(used, to, ans, deep + 1, graph);
            }
        }

    }


    private boolean intersect(Act act1, Act act2) {
        return !(act1.getEndTime() <= act2.getStartTime() && act2.getEndTime() <= act1.getStartTime());
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