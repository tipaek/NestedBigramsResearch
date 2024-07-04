

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solution {
    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 0; i < t; ++i) {
            int n = in.nextInt();
            in.nextLine();
            int [][] m = IntStream.range(0, n)
                    .mapToObj(v -> split(in.nextLine(), n))
                    .toArray(int[][]::new);
            String result = Stream.of(trace(m), rowNc(m), columnNc(m)).map(Object::toString).collect(Collectors.joining(" "));
            String answer = "Case #" + (i + 1) + ": " + result;
            out.write(answer);
            out.newLine();
        }
        in.close();
        out.close();

    }
    static int [] split(String s, int limit) {
        return Arrays.stream(s.split(" ", limit)).mapToInt(Integer::parseInt).toArray();
    }
    static boolean complete(int [] a) {
        return a.length == (int)Arrays.stream(a).distinct().count();
    }
    static int [] column(int [][]m , int j) {
        return IntStream.range(0, m[j].length).map(i -> m[i][j]).toArray();
    }
    static int trace(int [][]m) {
        return IntStream.range(0, m.length).map(i -> m[i][i]).sum();
    }
    static int rowNc(int [][] m) {
        return Arrays.stream(m).mapToInt(a -> complete(a) ? 0 : 1).sum();
    }
    static int columnNc(int [][] m) {
        return IntStream.range(0, m[0].length).mapToObj(j-> column(m, j)).mapToInt(a -> complete(a) ? 0 : 1).sum();
    }
}