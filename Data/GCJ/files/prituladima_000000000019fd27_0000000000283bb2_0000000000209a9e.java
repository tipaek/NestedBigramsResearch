//package com.prituladima.task4;

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

    private void solve() {
        int T = nextInt(), B = nextInt();
        for (int testNumber = 1; testNumber <= T; testNumber++) {
            solve(testNumber, B);
        }
    }

    private void solve(int testNumber, int B){
        char[] ans = new char[B];
        int nextPos = 1;
        for(int i = 1; i <= 150; i++){
            println(nextPos);
            flush();
            int value = nextInt();
            if(i % 10 == 1){

            }else {
                ans[nextPos - 1] = Character.forDigit(value,10);
                if(nextPos == ans.length) {
                    break;
                }
                nextPos++;
            }
        }
        for (int i = 0; i < ans.length; i++) {
            if(ans[i] != '0' && ans[i] != '1'){
                throw new RuntimeException();
            }
        }
        println(String.valueOf(ans));
        flush();
        final String verdict = nextToken();
         if("Y".equals(verdict)) {
             return;
         }else if ("N".equals(verdict)){
        //     throw new RuntimeException();
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