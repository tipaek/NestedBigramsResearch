import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        List<String> results = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCases = Integer.parseInt(br.readLine().trim());
        for (int i = 1; i <= testCases; i++) {
            Integer[][] matrix = readTestCase(br);
            Integer k = computeTrace(matrix);
            Integer r = computeRowDuplicates(matrix);
            Integer c = computeColumnsDuplicates(matrix);
            results.add("Case #" + i + ": " + k + " " + r + " " + c);
        }
        br.close();
        for (String result : results) {
            System.out.println(result);
        }
    }

    private static Integer[][] readTestCase(BufferedReader br) throws IOException {
        int matrixSize = Integer.parseInt(br.readLine().trim());
        Integer[][] fullMatrix = new Integer[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            Integer[] integers = readMatrixLine(br);
            fullMatrix[i] = integers;
        }
        return fullMatrix;
    }

    private static Integer[] readMatrixLine(BufferedReader br) throws IOException {
        String matrixLine = br.readLine();
        String[] split = matrixLine.split(" ");
        return Arrays.stream(split).map(Integer::valueOf).toArray(Integer[]::new);
    }

    private static Integer computeTrace(Integer[][] matrix) {
        int trace = 0;
        for (int i = 0; i < matrix.length; i++) {
            trace += matrix[i][i];
        }
        return trace;
    }

    private static Integer computeRowDuplicates(Integer[][] matrix) {
        Set<Integer> cache = new HashSet<>();
        Integer duplicates = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (cache.contains(matrix[i][j])) {
                    duplicates++;
                    break;
                } else {
                    cache.add(matrix[i][j]);
                }
            }
            cache = new HashSet<>();
        }
        return duplicates;
    }

    private static Integer computeColumnsDuplicates(Integer[][] matrix) {
        Set<Integer> cache = new HashSet<>();
        Integer duplicates = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (cache.contains(matrix[j][i])) {
                    duplicates++;
                    break;
                } else {
                    cache.add(matrix[j][i]);
                }
            }
            cache = new HashSet<>();
        }
        return duplicates;
    }
}
