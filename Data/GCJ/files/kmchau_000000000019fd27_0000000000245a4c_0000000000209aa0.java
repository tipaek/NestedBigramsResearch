import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;


public class Solution {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        //PrintWriter pw = new PrintWriter("output.txt");

        int maxCaseNum = in.nextInt();

        for (int caseIdx = 1; caseIdx <= maxCaseNum; caseIdx++) {
            //String[] array = line.split(" ");
//            long n = in.nextLong();
            int n = in.nextInt();
            int target = in.nextInt();

            String ansStr = "IMPOSSIBLE";
            Set<String> set = new HashSet<>();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    numbers.add(j);
                }
            }

            sum_up(new ArrayList<Integer>(numbers),target, set);

//            System.out.println(set);

            String[] array = set.stream().toArray(String[]::new);
            int[][]matrix = new int[0][];
            for (int i = 0; i < array.length; i++) {
                matrix = new int[n][n];
                String[] diagNums = array[i].replaceAll("\\[","").replaceAll("]","").replaceAll(" ","").split(",");
                for (int j = 0; j < diagNums.length; j++) {
                    matrix[j][j] = Integer.valueOf(diagNums[j]);
                }

                if (solveSudoku(matrix, n)) {
                    ansStr = "POSSIBLE";
//                    printMatrix(matrix);
                    break;
                }

            }

            String ans = "Case #" + caseIdx + ": " + ansStr;
            System.out.println(ans);
            
            if (ansStr.equals("POSSIBLE")) {
                printMatrix(matrix);
            }

        }

        in.close();

    }

    static boolean solveSudoku(int[][] board, int n)
    {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (board[i][j] == 0)
                {
                    row = i;
                    col = j;

                    // we still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty)
            {
                break;
            }
        }

        // no empty space left
        if (isEmpty)
        {
            return true;
        }

        // else for each-row backtrack
        for (int num = 1; num <= n; num++)
        {
            if (isSafe(board, row, col, num))
            {
                board[row][col] = num;
                if (solveSudoku(board, n))
                {
                    // print(board, n);
                    return true;
                }
                else
                {
                    board[row][col] = 0; // replace it
                }
            }
        }
        return false;
    }

    static boolean isSafe(int[][] board,
                          int row, int col,
                          int num)
    {
        // row has the unique (row-clash)
        for (int d = 0; d < board.length; d++)
        {
            // if the number we are trying to
            // place is already present in
            // that row, return false;
            if (board[row][d] == num)
            {
                return false;
            }
        }

        // column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++)
        {
            // if the number we are trying to
            // place is already present in
            // that column, return false;

            if (board[r][col] == num)
            {
                return false;
            }
        }

        // corresponding square has
        // unique number (box-clash)
        int sqrt = (int) Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++)
        {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++)
            {
                if (board[r][d] == num)
                {
                    return false;
                }
            }
        }

        // if there is no clash, it's safe
        return true;
    }

    static void printMatrix (int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
   }

    static long diagonal (long[][] matrix, int n) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += matrix[i][i];
        }

        return sum;
    }

    static void sum_up_recursive(ArrayList<Integer> numbers, int target, ArrayList<Integer> partial, Set<String> set) {
        int s = 0;
        for (int x: partial)
            s += x;
        if (s == target) {
            if (partial.toArray().length == 3) {
                String ans = Arrays.toString(partial.toArray());
                set.add(ans);
            }
        }
        if (s >= target)
            return;
        for(int i=0;i<numbers.size();i++) {
            ArrayList<Integer> remaining = new ArrayList<Integer>();
            int n = numbers.get(i);
            for (int j=i+1; j<numbers.size();j++) remaining.add(numbers.get(j));
            ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
            partial_rec.add(n);
            sum_up_recursive(remaining,target,partial_rec, set);
        }
    }

    static void sum_up(ArrayList<Integer> numbers, int target, Set<String> set) {
        sum_up_recursive(numbers,target,new ArrayList<Integer>(), set);
    }

}
