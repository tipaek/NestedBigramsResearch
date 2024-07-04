package com.prituladima.task4.sub10_2;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public final class Solution {

    private void solve() {
        int T = nextInt(), B = nextInt();
        for (int testNumber = 1; testNumber <= T; testNumber++) {
            solve(B);
        }
    }


    private void solve(int B) {
        if (B != 10) {
            throw new RuntimeException();
        }

        char[] arr_1_9 = new char[9];

        //1 Query
        println(1);
        flush();
        nextInt();

        //2 - 10 Queries
        for (int pos = 1, i = 0; pos <= 9; pos++, i++) {
            println(pos);
            flush();
            arr_1_9[i] = Character.forDigit(nextInt(), 10);
        }
        //11 Query
        println(10);
        flush();
        int last = nextInt();

        //12-19 Q
        char[] new_arr_2_9 = new char[8];
        for (int pos = 2, i = 0; pos <= 9; pos++, i++) {
            println(pos);
            flush();
            new_arr_2_9[i] = Character.forDigit(nextInt(), 10);
        }

        //20 Q
        println(1);
        flush();
        int first = nextInt();


        char[] new_arr_2_9_rev = new StringBuilder().append(new_arr_2_9).reverse().toString().toCharArray();
        //1.Positive
        boolean isPositive = true;
        boolean isToggled = true;
        boolean isReversed = true;
        boolean areBoth = true;


        for (int i = 2; i <= 9; i++) {
            isPositive &= (new_arr_2_9[i - 2] == arr_1_9[i - 1]);
            isToggled &= (new_arr_2_9[i - 2] != arr_1_9[i - 1]);
            isReversed &= (new_arr_2_9_rev[i - 2] == arr_1_9[i - 1]);
            areBoth &= (new_arr_2_9_rev[i - 2] != arr_1_9[i - 1]);
        }
        if (isPositive) {
            println(String.valueOf(arr_1_9) + last);
            flush();
            nextToken();
        } else if (isToggled) {
            println(String.valueOf(arr_1_9)
                    .replace('0', '_')
                    .replace('1', '0')
                    .replace('_', '1') + last);
            flush();
            nextToken();
        } else if (isReversed) {
            println(first + new StringBuilder().append(String.valueOf(arr_1_9)).reverse().toString());
            flush();
            nextToken();

        } else if (areBoth) {
            println(first + new StringBuilder().append(String.valueOf(arr_1_9)
                    .replace('0', '_')
                    .replace('1', '0')
                    .replace('_', '1')).reverse().toString());
            flush();
            nextToken();
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
//        flush();
    }

    private void flush() {
        writer.flush();
    }

}