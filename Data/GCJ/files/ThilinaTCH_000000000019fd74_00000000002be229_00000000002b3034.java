import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();
        for (int i = 1; i <= t; ++i) {
            int noOFPatterns = in.nextInt();
            String[] patterns = new String[noOFPatterns];
            String longestPattern = "";

            for (int j = 0; j < noOFPatterns; ++j) {
                patterns[j] = in.next();
                if (patterns[j].length() > longestPattern.length()) {
                    longestPattern = patterns[j];
                }
            }

            String result = solve(patterns, longestPattern);
            System.out.println("Case #" + i + ": " + result);
        }
    }

    private static String solve(String[] patterns, String longestPattern) {
        String result = "*";
        String[] nonMatching = Arrays.stream(patterns)
                .filter(str -> str.length() == longestPattern.length() && !str.equals(longestPattern)).toArray(String[]::new);
        if (nonMatching.length > 0) {
            char[] firstItemChars = nonMatching[0].toCharArray();
            for (int j = 0; j < nonMatching.length; j++) {

                if (longestPattern.replace("*", "") != nonMatching[j].replace("*", "")) {
                    for (int i = 0; i < firstItemChars.length; i++) {

                        if (firstItemChars[i] != '*') {
                            char[] nonMatchChar = nonMatching[j].toCharArray();
                            if (nonMatchChar[i] != '*' && nonMatchChar[i] != firstItemChars[i]) {
                                return result;
                            }
                        }
                    }
                }
            }
        }

        String shortestMatch = longestPattern.replace("*", "");
        for (int i = 0; i < patterns.length; i++) {
            if (!Pattern.matches(patterns[i].replace("*", ".*"), shortestMatch)) {
                return result;
            }
        }

        return shortestMatch;
    }
}
