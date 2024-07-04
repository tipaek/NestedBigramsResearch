import java.util.Arrays;
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
	    int C_min_S=-1, C_max_E=-1, J_min_S=-1, J_max_E=-1;
	    Integer P[] = new Integer[2];
	    String result = "";
	    for (int i = 0; i < N; i++) {
		sc.hasNext();
		num = sc.nextLine();
		P = Arrays.stream(num.split(" ")).map(Integer::new).toArray(Integer[]::new);
		if (i == 0) {
		    C_min_S = P[0];
		    C_max_E = P[1];
		    result += "C";
		    continue;
		}
		if(P[1] <= C_min_S){
		    C_min_S = P[0];
		    result += "C";
		} else if(P[0] >= C_max_E){
		    C_max_E = P[1];
		    result += "C";
		} else if(P[1] <= J_min_S){
		    J_min_S = P[0];
		    result += "J";
		} else if(P[0] >= J_max_E){
		    J_max_E = P[1];
		    result += "J";
		    if (J_min_S == -1) {
			J_min_S = P[0];
		    }
		} else {
		    result = "IMPOSSIBLE";
		    break;
		}

	    }


	    output.append("Case #" + eachCase + ": " + result);
	    eachCase++;
	    if (eachCase > caseNo)
		break;
	    output.append("\n");
	    N = null;
	}
	System.out.println(output.toString());
	sc.close();
    }

}
