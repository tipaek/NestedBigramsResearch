import java.io.; import java.util.;

public class Solution {

public static void main(String[] args) {
    int n, i, j;
     Scanner scanner = new Scanner(System.in);
     System.out.println("Enter a first number:");
     n = Integer.parseInt(scanner.nextLine());
     for(i = 0; i <= n; i++) {
     for(j = 0; j <= n-i; j++){
        System.out.print("  ");
     }
     for(j = 0; j <= i; j++){
        if(!(ncr(i, j)>9))
        System.out.print("   "+ncr(i, j));
        else
            System.out.print("  "+ncr(i, j));
     }
     System.out.println();
  }
}

static int factorial(int n) {
  int f;

  for(f = 1; n > 1; n--){
     f *= n;
  }
  return f;
} static int ncr(int n,int r) { return factorial(n) / ( factorial(n-r) * factorial(r) ); } }