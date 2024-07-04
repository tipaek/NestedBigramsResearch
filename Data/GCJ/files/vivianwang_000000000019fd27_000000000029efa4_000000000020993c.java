import java.util.*;
    import java.io.*;
    public class Solution {
      public static void main(String[] args) 
      {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int numTests = in.nextInt();
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int size = in.nextInt();
        int[][] mat;
        mat = new int[size][size];
        int total = 0;
      
       for (int r = 0; r < size; r++)
       {
           for (int c = 0; c < size; c++)
           {
               if (r== c)
               {
                   total += mat[r][c];
               }
                
           }
       }
       
       int numCols = 0;
       int numRows = 0;
       for (int r = 0; r < size; r++)
       {
           int first = mat[r][0];
           for (int c = 0; c < size; c++)
           {
              if (first != mat[r][c]) 
                numRows++;
           }
       }
       
       
       for (int c = 0; c < size; r++)
       {
           int first = mat[0][c];
           for (int r = 0; r < size; c++)
           {
              if (first != mat[r][c]) 
                numCols++;
           }
       }
       
       
       for (int i = 1; i < numTests; i++)
       {
         System.out.println("Case #" + i + ": " + total + " " + numRows + " " numCols
       }
      }
    }