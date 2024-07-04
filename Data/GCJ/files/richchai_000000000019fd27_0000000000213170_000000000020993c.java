import java.util.*;
import java.io.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = in.nextInt();
		for(int i = 1; i <= t; i++) {
			int N = in.nextInt();
			int[][] arr = readMatrix(N, in);
			Integer[][] arr2 = convert(arr);
			
			System.out.println("Case #" + i + ": " + diag(arr) + " " + rowRepeats(arr2) + " " + colRepeats(arr2) + "\n");
			
		}
		
		
		
	}
	
	public static int[][] readMatrix(int N, Scanner in) {
		int[][] arr = new int[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				arr[i][j] = in.nextInt();
			}
		}
		return arr;
	}
	
	public static int diag(int[][] arr) {
		int sum = 0;
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i][i];
		}
		return sum;
	}
	
	public static Integer[][] convert(int[][] arr) {
		Integer[][] newarr = new Integer[arr.length][arr.length];
		for(int i = 0; i < arr.length; i++) {
			newarr[i] = Arrays.stream(arr[i]).boxed().toArray(Integer[]::new);
		}
		return newarr;
	}
	
	public static int rowRepeats(Integer[][] arr) {
		int count = 0;
		for(int i = 0; i < arr.length; i++) {
			if(rowRepeat(arr[i])) {
				count++;
			}
		}
		return count;
	}
	
	public static boolean rowRepeat(Integer[] arr) {
		List<Integer> l = Arrays.asList(arr);
		Set<Integer> s = new HashSet<>(l);
		return s.size() < l.size();
	}
	
	public static boolean colRepeat(Integer[][] arr, int col) {
		Set<Integer> s = new HashSet<>();
		for(int i = 0; i < arr.length; i++) {
			s.add(arr[i][col]);
		}
		
		return s.size() < arr.length;
	}
	
	public static int colRepeats(Integer[][] arr) {
		int count = 0;
		for(int i = 0 ; i < arr.length; i++) {
			if(colRepeat(arr, i)) {
				count++;
			}
		}
		return count;
	}
	
	
	
}
