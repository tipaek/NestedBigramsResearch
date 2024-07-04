//package com.prituladima;

import java.io.*;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

import static java.lang.Double.min;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

public class Main {

//    int[] pow2;
    ArrayDeque<Character> deque = new ArrayDeque<>();
    private void solve() {
//        pow2 = new int[31];
//        for (int i = 0; i < 31; i++) {
//            pow2[i] = (1 << i);
//        }


        int T = nextInt();
        for (int tt = 1; tt <= T; tt++) {
            solve(tt);
        }
    }


//    boolean ansFound;
    String minAns = null;

    private void solve(int testCase) {
//        ansFound = false;
        minAns = null;
        deque.clear();
        int x = nextInt();
        int y = nextInt();

//        StringBuilder ans = new StringBuilder();

        go(x, y, 0, 0, 0, '\0');

        if(minAns == null){
//            println("IMPOSSIBLE");
            printf("Case #%d: %s\n", testCase, "IMPOSSIBLE");
            return;
        }

//        String ans = null;
//        for (Character character : deque) {
//            ans.append(character);
//        }
        printf("Case #%d: %s\n", testCase, minAns);
    }

    private void go(final int X, final int Y, long curX, long curY, int pow2, char dir) {

        if (X == curX && Y == curY) {
//            ansFound = true;
            final StringBuilder stringBuilder = new StringBuilder();
            for(char ch : deque){
                stringBuilder.append(ch);
            }

            if(minAns == null || minAns.length() > stringBuilder.length()){
                minAns = stringBuilder.toString();
            }
            return;
        }else if (pow2 == 10){
            return;
        }

        deque.addLast('E');
        go(X, Y, curX + (1L << pow2), curY, pow2 + 1, 'E');

//        if(ansFound) return;

        deque.removeLast();



        deque.addLast('W');
        go(X, Y, curX - (1L << pow2), curY, pow2 + 1, 'W');
//        if(ansFound) return;

        deque.removeLast();



        deque.addLast('N');
        go(X, Y, curX, curY + (1L << pow2), pow2 + 1, 'N');
//        if(ansFound) return;

        deque.removeLast();



        deque.addLast('S');
        go(X, Y, curX, curY - (1L << pow2), pow2 + 1, 'S');
//        if(ansFound) return;

        deque.removeLast();



    }

    private boolean answerExist(int x, int y) {
        return false;
    }


    public static void main(String[] args) {
        new Main().run();
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