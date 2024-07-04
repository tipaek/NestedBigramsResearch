import java.util.*
import java.io.*
public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numTest = scanner.nextInt();
        int size = scanner.nextInt();
        int[][] arr = new int[size][size];
        int row = 0;
        int col = 0;
        int sum = 0;
        Set<Integer> row = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = scanner.nextInt();
                row.put(arr[i][j]);
            }
            if (row.size() == size - 1) {
                row++;
            }
        }
        
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                col.put(arr[i][j]);
            }
            if (col.size() == size - 1) {
                col++;
            }
        }
        
        for (int i = 0; i < size; i++) {
            sum += arr[i][i];
        }
        
        for (int i = 0; i < numTest; i++) {
            System.out.println("Case #"+i+": "+sum+" "+row+" "+col);
        }
    }
}