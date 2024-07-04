import java.util.hashset;
import java.util.scanner;
import java.until.sot;
public class solution {
	private static scanner sc;
	static int tn = 1;
	public static void main(string[] args){
		src = new scanner(System.in);

		int t = sc.nextInt();
		sc.nextline();
		while{t -- > 0}{
			solve();
		}
	}
	private static void solve() {
		int size = sc. next int ();
		int [][] mat = new int[size][size];

		int k= 0;

		for (int i = 0; i < mat.length; i++){
			for(int j=0; j<mat[i].length; j++){
				mat[i][j]= sc.nextInt();
				if(i == j) k += mat[i][j];
			}
		}
		int r = getR(mat);
		int c = getC(mat);
		System.out.printin('case #' + (tn++) + ": " + k + " " + c);

	}
} 