//package com.company;


import java.io.*;
import java.math.*;
import java.util.*;

public class Solution {
    public static class Task {

        public void solve(Scanner sc, PrintWriter pw) throws IOException {
            int T = sc.nextInt();
            for (int tt = 1; tt <= T; tt++) {
                int n = sc.nextInt();
                int[][] mat = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        mat[i][j] = sc.nextInt();
                    }
                }
                long trace = 0;
                int r = 0, c = 0;
                for (int i = 0; i < n; i++) {
                    trace += mat[i][i];
                    Set<Integer> ss = new HashSet<>();
                    for (int j = 0; j < n; j++) {
                        if (ss.contains(mat[i][j])) {
                            r++; break;
                        }
                        ss.add(mat[i][j]);
                    }
                    ss.clear();
                    for (int j = 0; j < n; j++) {
                        if (ss.contains(mat[j][i])) {
                            c++; break;
                        }
                        ss.add(mat[j][i]);
                    }
                }
                pw.println("Case #" + tt + ": " + trace + " " + r + " " + c);
            }
        }
    }

    static long TIME_START, TIME_END;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
//        Scanner sc = new Scanner(new FileInputStream("input"));
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
//        PrintWriter pw = new PrintWriter(new FileOutputStream("output"));
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        TIME_START = System.currentTimeMillis();
        Task t = new Task();
        t.solve(sc, pw);
        TIME_END = System.currentTimeMillis();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        pw.close();
        System.err.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore) / 1000000);
        System.err.println("Time used: " + (TIME_END - TIME_START) + ".");
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(FileReader s) throws FileNotFoundException {
            br = new BufferedReader(s);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}