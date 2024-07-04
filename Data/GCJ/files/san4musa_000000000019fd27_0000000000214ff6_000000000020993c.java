import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int caseNo = -1;
	int eachCase = 1;
	String num = null;
	StringBuffer output = new StringBuffer();
	Integer N = null;
	while (sc.hasNext()) {
	    if (caseNo == -1) {
		caseNo = Integer.parseInt(sc.nextLine());
		continue;
	    } else if (N == null) {
		num = sc.nextLine();
		N = Integer.parseInt(num);
	    }
	    int k = 0, r = 0, c = 0;
	    long cnt = 0;
	    Integer P[][] = new Integer[N][N];
	    for (int i = 0; i < N; i++) {
		sc.hasNext();
		num = sc.nextLine();
		P[i] = Arrays.stream(num.split(" ")).map(Integer::new).toArray(Integer[]::new);
		cnt = getCount(Arrays.asList(P[i]));
		if (cnt > 0)
		    r++;
	    }
	    List<Integer> col = new ArrayList<>();
	    boolean diagonal = false;
	    boolean match = false;
	    for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
		    if (i == j) {
			k += P[i][j];
			diagonal = true;
		    }
		    if (!match && col.contains(P[j][i])) {
			c++;
			match = true;
		    } else {
			col.add(P[j][i]);
		    }
		    if (diagonal && match)
			break;
		}
		col.clear();
		diagonal = false;
		match = false;
	    }

	    output.append("Case #" + eachCase + ": " + k + " " + r + " " + c);
	    eachCase++;
	    if (eachCase > caseNo)
		break;
	    output.append("\n");
	    N = null;
	}
	System.out.println(output.toString());
	sc.close();
    }
	
    static long getCount(List<Integer> numbers) {
	long cnt = numbers.stream().filter(j -> Collections.frequency(numbers, j) > 1).count();
	return cnt;
    }

}
