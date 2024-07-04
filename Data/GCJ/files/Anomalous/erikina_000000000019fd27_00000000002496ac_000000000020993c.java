package codejam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int cases = scanner.nextInt();
        
        for (int i = 1; i <= cases; ++i) {
            int dim = scanner.nextInt();
            int[][] matrix = new int[dim][dim];
            int trace = 0, duplicateRows = 0, duplicateColumns = 0;
            
            for (int row = 0; row < dim; row++) {
                Set<Integer> rowSet = new HashSet<>();
                for (int col = 0; col < dim; col++) {
                    int value = scanner.nextInt();
                    matrix[row][col] = value;
                    if (row == col) {
                        trace += value;
                    }
                    rowSet.add(value);
                }
                if (rowSet.size() < dim) {
                    duplicateRows++;
                }
            }
            
            for (int col = 0; col < dim; col++) {
                Set<Integer> colSet = new HashSet<>();
                for (int row = 0; row < dim; row++) {
                    colSet.add(matrix[row][col]);
                }
                if (colSet.size() < dim) {
                    duplicateColumns++;
                }
            }
            
            System.out.println("Case #"1 + i + ": " + trace + " " + duplicateRows + " " + duplicateColumns);
        }
        
        scanner.close();
    }
}