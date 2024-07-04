import java.util.*;

public class Vestigium{
    public static void main(String[] args){
        
        Scanner in = new Scanner(System.in);
        
        // read test cases:
        int n_case = in.nextInt();
        
        
        for (int i = 0; i < n_cases; i ++ ){
            // read size:
            int size = in.nextInt();
            
            int diagonal = 0;
            
            int repeat_r = 0;
            
            int repeat_c = 0;
            
            int[][] matrix = new int[size][size];
            
            // read line
            for(int r = 0; r < size; r++){
                for(int c = 0; c < size; c++){
                    int[r][c] = in.nextInt();
                }
            }
            
            for(int j = 0; j < size; j++){
                diagonal += matrix[j][j];
                repeat_r += checkDuplicate(matrix, j, true);
                repeat_c += checkDuplicate(matrix, j, false);
            }
            
            
            System.out.println("Case #" + i + ": " + diagonal + " " + r_repeat + " " + c_repeat);
        }
        
        in.close();
        
    }
    
    private static boolean checkDuplicate(int[][] m, int n, boolean r){
        HashSet<Integer> set = new HashSet<>();
        
        // check row
        if(r){
            for(int i = 0; i < m[0].length; i++){
                if (set.contains(m[n][i])){
                    return true;
                }
            }
            
            set.add(m[n][i])
        } 
        
        // check col
        else {
            for(int i = 0; i < m.length; i++){
                if (set.contains(m[i][n])){
                    return true;
                }
            }
            
            set.add(m[i][n])
            
        }
        
        return false;
    }

}