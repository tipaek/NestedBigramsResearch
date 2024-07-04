import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int testCases = Integer.parseInt(br.readLine());

            for (int t = 0; t < testCases; t++) {
                int N = Integer.parseInt(br.readLine());
                int[][] matrix = new int[N][N];

                for (int i = 0; i < N; i++) {
                    String[] lineArray = br.readLine().split(" ");
                    for (int j = 0; j < N; j++) {
                        matrix[i][j] = Integer.parseInt(lineArray[j]);
                    }
                }

                int trace = 0;
                for (int i = 0; i < N; i++) {
                    trace += matrix[i][i];
                }

                int rowCounter = 0;
                int columnCounter = 0;

                for (int i = 0; i < N; i++) {
                    if (hasDuplicate(matrix[i])) {
                        rowCounter++;
                    }

                    int[] columnArray = new int[N];
                    for (int j = 0; j < N; j++) {
                        columnArray[j] = matrix[j][i];
                    }
                    if (hasDuplicate(columnArray)) {
                        columnCounter++;
                    }
                }

                System.out.println("Case #" + (t + 1) + ": " + trace + " " + rowCounter + " " + columnCounter);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean hasDuplicate(int[] array) {
        Integer[] arr = Arrays.stream(array).boxed().toArray(Integer[]::new);
        List<Integer> list = Arrays.asList(arr);
        Collections.sort(list);

        for (int k = 0; k < list.size() - 1; k++) {
            if (list.get(k).equals(list.get(k + 1))) {
                return true;
            }
        }
        return false;
    }
}