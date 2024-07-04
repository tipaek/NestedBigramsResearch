//package com.prituladima.b;

import java.io.*;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Solution {

    //    int[] pow2;
    // ArrayDeque<Character> deque = new ArrayDeque<>();

    private void solve() {
//        pow2 = new int[31];
//        for (int i = 0; i < 31; i++) {
//            pow2[i] = (1 << i);
//        }


        int T = nextInt();
        int A = nextInt();
        int B = nextInt();

        for (int tt = 1; tt <= T; tt++) {
            solve(tt, A, B);
        }
    }


    //    boolean ansFound;
//    String minAns = null;

    private void solve(int testCase, int A, int B) {
        //1. Test case
        int leftBound = -(int) 1e9;
        int rightBound = (int) 1e9;

        for (int i = leftBound + A; i <= rightBound - B; i++)
            for (int j = leftBound + A; j <= rightBound - B; j++) {
                String ans = query(i, j);
                if ("CENTER".equals(ans)) {
                    return;
                }
//                }else if("HIT"){
//
//                }
            }

    }

    private String query(int i, int j) {
        printf("%d %d\n", i, j);
        flush();
        return nextToken();
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