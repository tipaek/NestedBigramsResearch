import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Solution {

    public static boolean debug = false;
    public static boolean fromFile = false;
    public static String inputFile = "testC.in";

    public static PrintWriter writer;
    public static Scanner scanner;

    public static void debugPrintln(String s) {
        if (debug) {
            writer.println(s);
        }
    }

    public static void debugPrint(String s) {
        if (debug) {
            writer.print(s);
        }
    }

    public static long now() {
        return System.nanoTime();
    }

    public static double round(double d, int sigDigits) {
        double q = Math.pow(10, sigDigits);
        return Math.round(d * q) / q;
    }

    public static void printTime(long start, long stop) {
        long elapsed = stop - start;
        double msPerNs = Math.pow(10, -6);
        debugPrintln("Ms elapsed: " + round(elapsed * msPerNs, 4) + " (" + round(start * msPerNs, 4) + ", " + round(stop * msPerNs, 4) + ")");
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    public static HashSet<BigInteger> arrayToHashSet(BigInteger[] array) {
        return new HashSet<>(Arrays.asList(array));
    }

    public static HashMap<BigInteger, Character> convertCodesToLetters(HashSet<BigInteger> codes) {
        BigInteger[] sortedCodes = codes.toArray(new BigInteger[0]);
        Arrays.sort(sortedCodes);

        HashMap<BigInteger, Character> mapping = new HashMap<>();
        char ch = 'A';
        for (BigInteger code : sortedCodes) {
            mapping.put(code, ch++);
        }

        return mapping;
    }

    public static int getNth(int n) {
        writer.println("" + (n + 1));
        writer.flush();
        return Integer.parseInt(scanner.next());
    }

    public static void solveFor10() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(getNth(i));
        }
        writer.println(result);
        writer.flush();
        assert "Y".equals(scanner.next());
        scanner.next();
    }

    public static int[] extractByIndices(int[] full, int[] indices) {
        return Arrays.stream(indices).map(i -> full[i]).toArray();
    }

    public static void insertByIndices(int[] full, int[] indices, int[] byIndex) {
        for (int i = 0; i < indices.length; i++) {
            full[indices[i]] = byIndex[i];
        }
    }

    public static int[] updateForFlipsAndComplements(int[] original, int[] indices) {
        int n = indices.length;
        int[] updated = new int[n];

        Integer sameIndex = null;
        Integer differentIndex = null;

        for (int i = 0; i < n; i++) {
            if (original[i] == original[n - i - 1]) {
                sameIndex = i;
            } else {
                differentIndex = i;
            }
        }

        boolean isFlipped;
        boolean isComplement;

        if (sameIndex == null) {
            isComplement = false;
        } else {
            int x = getNth(indices[sameIndex]);
            isComplement = x != original[sameIndex];
        }

        if (differentIndex == null) {
            isFlipped = false;
        } else {
            int x = getNth(indices[differentIndex]);
            isFlipped = (!isComplement && x != original[differentIndex]) || (isComplement && x == original[differentIndex]);
        }

        if (isFlipped) {
            for (int i = 0; i < n; i++) {
                updated[i] = original[n - i - 1];
            }
        } else {
            System.arraycopy(original, 0, updated, 0, n);
        }

        if (isComplement) {
            for (int i = 0; i < n; i++) {
                updated[i] = updated[i] == 0 ? 1 : 0;
            }
        }

        return updated;
    }

    public static void solveFor20() {
        int[] values = new int[20];
        int[] finalValues = new int[20];
        int[] middleIndices = new int[10];
        int[] outerIndices = new int[10];

        for (int i = 0; i < 10; i++) {
            middleIndices[i] = i + 5;
            outerIndices[i] = i < 5 ? i : i + 10;
        }

        for (int i : middleIndices) {
            values[i] = getNth(i);
        }

        for (int i : outerIndices) {
            values[i] = getNth(i);
        }

        int[] middle = extractByIndices(values, middleIndices);
        int[] outside = extractByIndices(values, outerIndices);

        int[] finalMiddle = updateForFlipsAndComplements(middle, middleIndices);
        int[] finalOutside = updateForFlipsAndComplements(outside, outerIndices);

        insertByIndices(finalValues, middleIndices, finalMiddle);
        insertByIndices(finalValues, outerIndices, finalOutside);

        StringBuilder result = new StringBuilder();
        for (int value : finalValues) {
            result.append(value);
        }
        writer.println(result);
        writer.flush();
        assert "Y".equals(scanner.next());
        scanner.next();
    }

    public static void solveFor100() {
        int[] values = new int[100];
        int[] intermediateValues = new int[100];
        int[] finalValues = new int[100];

        int[][] smallChunkIndices = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                smallChunkIndices[i][j] = j < 5 ? i * 5 + j : (20 - i - 2) * 5 + j;
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j : smallChunkIndices[i]) {
                values[j] = getNth(j);
            }
        }

        for (int i = 0; i < 10; i++) {
            int[] chunkIndices = smallChunkIndices[i];
            int[] chunk = extractByIndices(values, chunkIndices);
            int[] finalChunk = updateForFlipsAndComplements(chunk, chunkIndices);
            insertByIndices(intermediateValues, chunkIndices, finalChunk);
        }

        int[][] largeChunkIndices = new int[2][50];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 50; j++) {
                largeChunkIndices[i][j] = j < 25 ? i * 25 + j : (4 - i - 2) * 25 + j;
            }
        }

        for (int i = 0; i < 2; i++) {
            int[] chunkIndices = largeChunkIndices[i];
            int[] chunk = extractByIndices(intermediateValues, chunkIndices);
            int[] finalChunk = updateForFlipsAndComplements(chunk, chunkIndices);
            insertByIndices(finalValues, chunkIndices, finalChunk);
        }

        StringBuilder result = new StringBuilder();
        for (int value : finalValues) {
            result.append(value);
        }
        writer.println(result);
        writer.flush();
        assert "Y".equals(scanner.next());
        scanner.next();
    }

    public static void nextCase(int b) {
        switch (b) {
            case 10 -> solveFor10();
            case 20 -> solveFor20();
            case 100 -> solveFor100();
            default -> throw new IllegalArgumentException("Invalid value for b: " + b);
        }
    }

    public static void main(String[] args) throws Exception {
        scanner = fromFile ? new Scanner(new File(inputFile)) : new Scanner(System.in);
        writer = new PrintWriter(System.out);

        int t = scanner.nextInt();
        int b = scanner.nextInt();

        for (int i = 0; i < t; i++) {
            nextCase(b);
        }

        writer.close();
        scanner.close();
    }
}