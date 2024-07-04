import java.util.*;
import java.io.*;

public class Solution{
  
  public static void main(String[] args) throws IOException {
    Solution fd = new Solution();
    Scanner input= new Scanner(System.in);
    int testN = input.nextInt();
    for(int i = 0; i < testN; i++){
      int mSize = input.nextInt();
      int[][] matrix = new int[mSize][mSize];
      for(int j = 0; j < mSize; j++){
        for(int k = 0 ; k <mSize; k++){
            matrix[j][k] = input.nextInt();
        }
      }
      int k = 0;
      int r = 0;
      int c = 0;
      for(int j = 0; j < mSize; j++){
          Integer[] a1 = Arrays.stream(matrix[j]).boxed().toArray( Integer[]::new );
          Set<Integer> targetSet = new HashSet<Integer>();
          Collections.addAll(targetSet, a1);
          if(matrix[j].length != targetSet.size()){
              r += 1;
          }
      }
      for(int j = 0; j < mSize; j++){
        Integer[] column = new Integer[mSize];
        for(int l = 0; l < mSize; l++){
          column[l] = Integer.valueOf(matrix[l][j]);
        }
        Set<Integer> targetSet = new HashSet<Integer>();
          Collections.addAll(targetSet, column);
        if(matrix[j].length != targetSet.size()){
              c += 1;
          }
      }
      for(int j = 0; j< mSize; j++){
          k += matrix[j][j];
      }
      int test = i + 1;
      System.out.println("Case #" + test + ":"  + k + " " + r + " " + c);
    }
  }
}
