import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

	private static String calc(int arr[][]) {
		String ans = "";

		List<Integer[]> cList = new ArrayList<>();
		List<Integer[]> jList = new ArrayList<>();

		boolean overlaps = false;

		for (int i = 0; i < arr.length; i++) {
			int curr[] = arr[i];

			overlaps = false;

			for (int j = 0; j < cList.size(); j++) {
				Integer cArr[] = cList.get(j);
				if ((curr[0] >= cArr[0] && curr[0] < cArr[1]) || (curr[1] > cArr[0] && curr[1] <= cArr[1])
						|| (cArr[0] >= curr[0] && cArr[0] < curr[1]) || (cArr[1] > curr[0] && cArr[1] <= curr[1])) {
					overlaps = true;
					break;
				}
			}
			if (overlaps) {
				overlaps = false;
				for (int k = 0; k < jList.size(); k++) {
					Integer jArr[] = jList.get(k);
					if((curr[0] >= jArr[0] && curr[0] < jArr[1]) || (curr[1] > jArr[0] && curr[1] <= jArr[1])
							|| (jArr[0] >= curr[0] && jArr[0] < curr[0]) || (jArr[1] > curr[0] && jArr[1] <= curr[1])) {
						overlaps = true;
						break;
					}
				}
				if(overlaps) {
					return "IMPOSSIBLE";
				} else {
					ans += "J";
					Integer[] boxed = Arrays.stream(curr).boxed().toArray(Integer[]::new);
					jList.add(boxed);
				}
			} else {
				ans += "C";
				Integer[] boxed = Arrays.stream(curr).boxed().toArray(Integer[]::new);
				cList.add(boxed);
			}
		}

		return ans;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int T = sc.nextInt();
		for (int i = 1; i <= T; i++) {
			int N = sc.nextInt();
			int arr[][] = new int[N][2];
			for (int j = 0; j < N; j++) {
				arr[j][0] = sc.nextInt();
				arr[j][1] = sc.nextInt();
			}
			System.out.println("Case #" + i + ": " + calc(arr));
		}
		sc.close();
	}
}