import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

	private static String calc(int arr[][]) {
		String res = "";
		List<Integer[]> cList = new ArrayList<>();
		List<Integer[]> jList = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			boolean exists = false;
			for (Integer[] cArr : cList) {
				if (arr[i][0] == cArr[0] || arr[i][1] == cArr[1]) {
					exists = true;
					break;
				} else if ((arr[i][0] > cArr[0] && arr[i][0] < cArr[1]) || (cArr[0] > arr[i][0] && cArr[0] < arr[i][1])
						|| (arr[i][1] > cArr[0] && arr[i][1] < cArr[1])
						|| (cArr[1] > arr[i][0] && cArr[1] < arr[i][1])) {
					exists = true;
					break;
				}
			}
			if (exists) {
				exists = false;
				for (Integer[] cArr : jList) {
					if (arr[i][0] == cArr[0] || arr[i][1] == cArr[1]) {
						exists = true;
						break;
					} else if ((arr[i][0] > cArr[0] && arr[i][0] < cArr[1])
							|| (cArr[0] > arr[i][0] && cArr[0] < arr[i][1])
							|| (arr[i][1] > cArr[0] && arr[i][1] < cArr[1])
							|| (cArr[1] > arr[i][0] && cArr[1] < arr[i][1])) {
						exists = true;
						break;
					}
				}
				if (exists) {
					return "IMPOSSIBLE";
				} else {
					Integer[] boxed = Arrays.stream(arr[i]).boxed().toArray(Integer[]::new);
					jList.add(boxed);
					res += "J";
				}
			} else {
				Integer[] boxed = Arrays.stream(arr[i]).boxed().toArray(Integer[]::new);
				cList.add(boxed);
				res += "C";
			}
		}
		return res;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int T = sc.nextInt();
		for (int i = 1; i <= T; i++) {
		    PrintWriter writer = new PrintWriter(System.out);
			int N = sc.nextInt();
			int arr[][] = new int[N][2];
			for (int j = 0; j < N; j++) {
				arr[j][0] = sc.nextInt();
				arr[j][1] = sc.nextInt();
			}
//			System.out.println("Case #" + i + ": " + calc(arr));
			writer.write("Case #" + i + ": " + calc(arr));
			writer.flush(); 
		}
		sc.close();
	}
}