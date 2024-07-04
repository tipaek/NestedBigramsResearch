import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Solution {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int ntests = sc.nextInt();
        for (int i = 0; i < ntests; i++) {
            int rows = sc.nextInt();
            process(rows, i+1);
        }
    }
    
    public static void process(int rows, int ncase){
        int sum = 0;
        Integer[][] arr = new Integer[rows][rows];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < rows; col++) {
                int value = sc.nextInt();
                arr[row][col] = value;
                if(row == col){
                    sum = sum + value;
                }
            }
        }
        int rowcount = 0;
        for (Integer[] row: arr) {
            if(new HashSet<>(Arrays.asList(row)).size() != rows){
                rowcount++;
            }
        }
        int colcount = 0;
        for (int i = 0; i < rows; i++) {
            if(new HashSet<>(Arrays.asList(getColumn(arr, i))).size() != rows){
                colcount++;
            }
        }
        System.out.println("Case #" + ncase + ":" + " " + sum + " " + rowcount + " " + colcount);
    }

    public static Integer[] getColumn(Integer[][] matrix, int column) {
        return Arrays.stream(Arrays.stream(matrix).mapToInt(ints -> ints[column]).toArray()).boxed().toArray(Integer[]::new);
    }
}
