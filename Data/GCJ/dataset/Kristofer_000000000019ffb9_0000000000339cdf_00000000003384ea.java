/*
 * This submission is using the template from https://github.com/krka/codejamjavatemplate
 * Revision: __REVISION__
 */

// Comment this out before submitting
//package codejam20200516.incremental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Solution {

  private final int caseNumber;
  private long L;
  private long R;

  private Solution(int caseNumber) {
    this.caseNumber = caseNumber;
    // Read inputs here
    L = getLong();
    R = getLong();
  }

  private String solve() {
    long i = 0;
    while (true) {
      if (Math.max(L, R) <= i) {
        return "" + i + " " + L + " " + R;
      }
      i++;
      if (L >= R) {
        L -= i;
      } else {
        R -= i;
      }
    }
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
}
