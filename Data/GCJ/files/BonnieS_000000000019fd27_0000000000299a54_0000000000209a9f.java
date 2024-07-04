import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {

	    Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int dataSize = Integer.parseInt(s.next());
		int count = 1;

		while (dataSize >= 0) {
			int n = Integer.parseInt(s.next());
			int k = Integer.parseInt(s.next());
			int [][] components = new int[n][n]; 

			String possible = "POSSIBLE";
			if (n > k || n*n < k) {
				possible = "IMPOSSIBLE";
			} else {
				components = findComponents(n);
			}
			System.out.println("Case #"+count+": "+possible);
			if (possible.equals("POSSIBLE")){
				possible = getArrayWithTrace(components,);
				printArray(components);	
				System.out.println("Trace: "+getTrace(components));
			}

			count++;
			dataSize--;
		}
	}
	
	private static String getArrayWithTrace() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void printArray(int [][]components){
		for (int[]x:components) {
				for (int i:x) {
					System.out.print(i+" ");
				}
				System.out.println("");
			}
	}	
	private static int getTrace(int[][]a) {
		int sum = 0;
		for (int i = 0; i < a[0].length;i++) {
			sum+=a[i][i];
		}
		return sum;
	}
	                                     
	private static int[][] findComponents(int n){
	
		int [][] matrix = new int[n][n]; 
		for (int x = 0; x<n;x++) {
			int count = x;
			for (int y = 0;y<n;y++) {
				count++;
				matrix[x][y] = count;
				if (count == n) {
					count = 0;
				}
			}
		}
		return matrix;
	}
}
