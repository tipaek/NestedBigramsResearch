import java.util.*;
import java.io.*;

public class Solution{
    public static void main(String[] args) throws Exception{
        FS fs=new FS(System.in);
       
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int t = sc.nextInt();
        int rc = sc.nextInt();
        while(t-- > 0){
             int[rc][rc] matrix= new int[rc][rc];
             for(int i=0;i<rc;i++){
                 for(int j=0;j<rc;j++){
                    matrix[i][j] = sc.nextInt();    
                 }
             }
        boolean duplicate = checkTrace(matrix);
        int duplicateRows = duplicateRowCount(matrix);
        int duplicateColumn = duplicateColumnCount(matrix);
        int diagonal =0;
        //Does not contains a duplcaite element in row or column
        if(!duplicate){
                for(int = 0;i < rc; i++){
                    for(int j=0;j< rc ;j++){
                    diagonal+=matrix[i][i];    
                   }
                }
                System.out.println("Case #"+t+":"+diagonal+" 0 "+" 0 ");
            }
        // Contains a duplicate element in row and column
        else{
                System.out.println("Case #"+t+":"+diagonal+" "+duplicateRows+" "+duplicateColumn);        
            }
        }
    }
    public int duplicateRowCount(int[row][col] matrix){
        int duplicateRows = 0;
        for(int i = 0;i < row;i++){
          for(int j = 0;j < col;j++){
             int num = matrix[i][j];
             for(int otherCol=col+1;otherCol < i;otherCol++){
                 if(num == matrix[row][otherCol]){
                    duplicateRows++; 
                 }
                }   
           }
        }
        return duplicateRows;
    }
    public int duplicateColumnCount(int[row][col] matrix){
        int duplicateColumn=0;
        for(int i = 0;i < col;i++){
          for(int j = 0;j < row;j++){
             int num = matrix[i][j];
             for(int otherCol=col+1;otherCol < i;otherCol++){
                 if(num == matrix[otherCol][row]){
                    duplicateColumn++; 
                   }
                }   
             }
        }
        return duplicateColumn;
    }
    public boolean checkTrace(int[i][j] matrix){
        for(int row = 0;row < i;row++){
            for(int col = 0;col < j;col++){
                int num = matrix[row][col];
                for(int otherCol = col+1;otherCol < matrix.length;otherCol++){
                    if(num == matrix[row][otherCol]){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static class FS{
        BufferedReader br;
        StringTokenizer st;
        FS(InputStream in){
            br = new BufferedReader(new InputStreamReader(in));
            st = new StringTokenizer("");
        }
         int nextInt() throws Exception
        {
            return Integer.parseInt(next());
        }
        
        String next() throws Exception
        {
            if (st.hasMoreTokens())
                return st.nextToken();
            st = new StringTokenizer(br.readLine());
            return next();
        }
    }
}