public class fb {

	public static void main(String[] args) {
		int X=3;
		while(X>0) {
			if(X>2)System.out.print("a");
			if(X==2)System.out.print("b c");
			if(X==1) {
				System.out.print("d");
				break;
			}
			X--;
			System.out.print("-");
		}
	