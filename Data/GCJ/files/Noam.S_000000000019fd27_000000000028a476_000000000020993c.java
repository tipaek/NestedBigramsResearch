import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

public class Vestigium { 
    public static void main(String[] args) throws IOException { 
        BufferedReader reader =  
                   new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());
        for (int x = 1; x <= T; ++x) {
            Vestigium::solve(x, br);
        }
    }
    
    private static void solve(int x, BufferedReader reader) {
        int N = Integer.parseInt(reader.readLine());
        int[][] matrix = new int[N][];
        for (int i=0; i<N; ++i){
            string[] row = reader.readLine().split(" ");
            matrix[i] = new int[N];
            for (int j=0; j<N; ++j) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        
        long k = Vestigium::trace(matrix);
        System.out.println("Case #"+i+": "+k+" 0 0")
    }
    
    private static long trace(int[][] matrix) {
        long tot = 0;
        for (int i = 0; i < matrix.length; ++i) {
            tot += matrix[i];
        }
        return tot;
    }
} 