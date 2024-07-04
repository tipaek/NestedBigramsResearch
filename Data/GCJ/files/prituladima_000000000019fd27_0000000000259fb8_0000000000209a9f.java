//package com.prituladima.task2;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Solution {

    private void solve() {
        int T = nextInt();
        for (int i = 1; i <= T; i++) {
            String input = nextToken();

            int level = 0;
            //Level
            //Iteration
            //if(level > value) ->
            // diff = level - value
            //for(diff)
            // level ++
            // ans.append('(')
            // ans.append(value)


            //if(level < value) ->
            //for(diff)
            // level --
            // ans.append(')')
            // ans.append(value)

            // end for(level) ans.append(')')
            StringBuilder ans = new StringBuilder();
            for (int j = 0; j < input.length(); j++) {
                final int value = Character.getNumericValue(input.charAt(j));
                if (level == value) {
                    ans.append(value);
                } else if (level < value) {
                    int diff = value - level;
                    for (int k = 0; k < diff; k++) {
                        ans.append('(');
                    }
                    ans.append(value);
                    level = value;
                }else if (level > value) {
                    int diff = level - value;
                    for (int k = 0; k < diff; k++) {
                        ans.append(')');
                    }
                    ans.append(value);
                    level = value;
                }
            }

            for (int k = 0; k < level; k++) {
                ans.append(')');
            }
            level = 0;

            printf("Case #%d: %s\n", i,  ans.toString());

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