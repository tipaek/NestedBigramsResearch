/*
 * This submission is using the template from https://github.com/krka/codejamjavatemplate
 * Revision: __REVISION__
 */

// Comment this out before submitting
//package vestigium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.StringTokenizer;

public class Solution {

  private final int caseNumber;
  private final int N;
  private int trace;
  private final int[][] matrix;

  private Solution(int caseNumber) {
    this.caseNumber = caseNumber;
    // Read inputs here
    N = getInt();
    trace = 0;
    matrix = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int num = getInt();
        if (i == j) {
          trace += num;
        }
        matrix[i][j] = num;
      }
    }
  }

  private String solve() {
    int rows = 0;
    int cols = 0;
    for (int i = 0; i < N; i++) {
      HashSet<Integer> set = new HashSet<Integer>();
      for (int j = 0; j < N; j++) {
        set.add(matrix[i][j]);
      }
      if (set.size() != N) {
        rows++;
      }
    }
    for (int i = 0; i < N; i++) {
      HashSet<Integer> set = new HashSet<Integer>();
      for (int j = 0; j < N; j++) {
        set.add(matrix[j][i]);
      }
      if (set.size() != N) {
        cols++;
      }
    }

    return trace + " " + rows + " " + cols;
  }

  /**
   * ONLY BOILERPLATE BELOW THIS POINT
   */

  private static BufferedReader reader;
  private static StringTokenizer tokenizer = new StringTokenizer("");

  public static void main(String[] args) {
    reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      runCases();
    } finally {
      System.out.flush();
    }
  }

  private static void runCases() {
    int cases = getInt();
    for (int c = 1; c <= cases; c++) {
      String answer = new Solution(c).solve();
      String s = "Case #" + c + ": " + answer;
      System.out.println(s);
    }
  }

  private static String readLine() {
    try {
      return reader.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String getToken() {
    while (true) {
      if (tokenizer.hasMoreTokens()) {
        return tokenizer.nextToken();
      }
      String s = readLine();
      if (s == null) {
        return null;
      }
      tokenizer = new StringTokenizer(s, " \t\n\r");
    }
  }

  private static double getDouble() {
    return Double.parseDouble(getToken());
  }

  private static int getInt() {
    return Integer.parseInt(getToken());
  }

  private static long getLong() {
    return Long.parseLong(getToken());
  }

  private static BigInteger getBigInt() {
    return new BigInteger(getToken());
  }

  private static BigDecimal getBigDec() {
    return new BigDecimal(getToken());
  }

  private static class Pair {
    private final double factor;
    private final int index;

    public Pair(double factor, int index) {
      this.factor = factor;
      this.index = index;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pair pair = (Pair) o;
      return Double.compare(pair.factor, factor) == 0 &&
              index == pair.index;
    }

    @Override
    public int hashCode() {
      return Objects.hash(factor, index);
    }
  }
}
