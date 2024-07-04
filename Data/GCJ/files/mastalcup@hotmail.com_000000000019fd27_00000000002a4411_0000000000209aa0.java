import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
			int t = Integer.parseInt(in.nextLine());
			for (int i = 1; i <= t; ++i) {
				String[] line = in.nextLine().split(" ");
				int n = Integer.parseInt(line[0]);
				int targetDiagonalSum = Integer.parseInt(line[1]);

				System.out.println("Case #" + i + ": " + findAnswer(targetDiagonalSum, n));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String findAnswer(int targetDiagonalSum, int n) {
		int nSquared = (int) Math.pow((double) n, 2);
		if (targetDiagonalSum > nSquared 
				|| targetDiagonalSum < n 
				|| targetDiagonalSum == (n + 1)
				|| targetDiagonalSum == (nSquared - 1)) {
			return "IMPOSSIBLE";
		} else if (n == 2 || n == 3) {
			if (targetDiagonalSum % n != 0) {
				return "IMPOSSIBLE";
			}
		} else if (n > 3) {
			if (targetDiagonalSum == (n + 1)) {
				return "IMPOSSIBLE";
			}
		}
		
		int[] diagonalValues = findValidDiagonalArray(n, targetDiagonalSum);
		int[][] square = generateLatinSquare(diagonalValues);
		
		StringBuilder sb = new StringBuilder();
		sb.append("POSSIBLE");
		
		sb.append(squareToString(square));
		
		return sb.toString();
	}
	
	private static int[] findValidDiagonalArray(int n, int targetDiagonalSum) {
		int[] diagonalValues = new int[n];
		
		int uniqueToUse = -1;
		int smallestDifference = 9999;//greatest possible real value is n^2, n max is 50, so 2500
		for(int i = 1; i <= n; i++){
			int sumByUniques = i * n;
			
			int thisDifference = targetDiagonalSum - sumByUniques;
			
			if(Math.abs(thisDifference) < Math.abs(smallestDifference)) {
				smallestDifference = thisDifference;
				uniqueToUse = i;
			}
		}

		diagonalValues = Collections.nCopies(n, uniqueToUse).stream().mapToInt(x->x).toArray();
		
		int difference = smallestDifference;
		int c = 1;
		while(difference != 0) {
			if(difference > 0) {
				diagonalValues[c-1]++;
				difference--;
				
				if(!isValidDiagonalArray(diagonalValues)) {
					diagonalValues[c]++;
					difference--;
					c = 0;
				}
			}
			else {
				diagonalValues[n-c]--;
				difference++;

				if(!isValidDiagonalArray(diagonalValues)) {
					diagonalValues[n-c-1]--;
					difference++;
					c = 0;
				}
			}
			c++;
		}
		
		return diagonalValues;
	}
	
	private static int[][] generateLatinSquare(int[] diagonalValues){
		int[][] square = new int[diagonalValues.length][diagonalValues.length];

		//fill in diagonals
		for(int d = 0; d < diagonalValues.length; d++) {
			square[d][d] = diagonalValues[d];
		}
		
		solveSudoku(square, diagonalValues.length);
		
		return square;
	}
	
	private static String squareToString(int[][] square) {
		StringBuilder sb = new StringBuilder();
		for(int row = 0; row < square[0].length; row++) {
			sb.append("\n");
			sb.append(
				String.join(" ", Arrays.stream(square[row]).mapToObj(String::valueOf).toArray(String[]::new))
			);
		}
		return sb.toString();
	}
	
	private static boolean isValidDiagonalArray(int[] diagonalValues) {
		int[] numOccurrences = new int[diagonalValues.length];
		//use an int array with index to keep track of each num occurrences
		for(int i = 0; i < diagonalValues.length; i++) {
			int thisNum = diagonalValues[i];
			numOccurrences[thisNum-1]++;
		}
		
		for(int occurrences: numOccurrences) {
			if(occurrences == diagonalValues.length - 1) {
				return false;
			}
		}
		
		return true;
	}

	public static boolean isSafe(int[][] board, int row, int col, int num) {
// row has the unique (row-clash) 
		for (int d = 0; d < board.length; d++) {
// if the number we are trying to  
// place is already present in  
// that row, return false; 
			if (board[row][d] == num) {
				return false;
			}
		}

// column has the unique numbers (column-clash) 
		for (int r = 0; r < board.length; r++) {
// if the number we are trying to 
// place is already present in 
// that column, return false; 

			if (board[r][col] == num) {
				return false;
			}
		}

// if there is no clash, it's safe 
		return true;
	}

	public static boolean solveSudoku(int[][] board, int n)  
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
	
}