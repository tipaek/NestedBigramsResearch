import java.util.Scanner;

public class LatinSquares {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter n number: ");
        int n = input.nextInt();
        char[][] m = new char[n][n];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = input.next().charAt(0);
            }
        }

        System.out.println(checkLatinSquare(m));

    }

    public static boolean checkLatinSquare(char[][] m) {

        // first check if grid has valid letters
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
            //More statements
            //Do something
            }
         }
     }
     // check if every row has unique letters
     for (int i = 0; i < m.length; i++) {
            if (!isRowValid(m, i)) return false;
        }

        // check if every column has unique letters
        for (int j = 0; j < m[0].length; j++) {
            if (!isColumnValid(m,j)) return false;
        }

        return true;
    }
    public static boolean isColumnValid(char[][] m, int column) {
         //Statements 
    }
    public static boolean isRowValid(char[][] m, int row) {
         //Statements
    }

    public static void displayMatrix(char[][] m) {
        //Statements
    }

    public static boolean isValidLetter(char ch, int n) {
        // ch starts off from A, so subtract one from n
        n--;
        return (ch >= 'A' && ch <= 'A' + n);
    }


}