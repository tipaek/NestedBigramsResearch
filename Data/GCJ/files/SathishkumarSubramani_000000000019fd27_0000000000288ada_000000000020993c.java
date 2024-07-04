public class Solution {
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        int testCount = scanner.nextInt();
        for (int i = 1; i <= testCount; i++) {
            int N = scanner.nextInt();
            
            int trace = 0;
            int rows = 0;
            int columns = 0;
            
            long maxSum = (N * (N +1)) / 2;
            
            for (int r = 1; r <= N; r++) {
                Set<Integer> row = 
            for (int c = 1; c <= N; c++) {
                int value = scanner.nextInt();
                if(r == c) {
                    trace += (value * value);
                }
                 
            }
            }
            System.out.println("Case #%d: %d %d %d", i, trace, rows, columns);
        }
        
    }
}