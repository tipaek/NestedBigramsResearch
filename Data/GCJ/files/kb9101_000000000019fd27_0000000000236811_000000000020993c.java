import java.util.Scanner;

public class Main 
{
   public static void main(String[] args) 
   {
      Scanner sc = new Scanner(System.in); 
      System.out.println("Please enter number of matrix rows : "); 
      int row = sc.nextInt(); 
      System.out.println("Please enter number of matrix columns : "); 
      int col = sc.nextInt();
      int numbers[][] = new int[row][col];
      fillingMatrix(sc, numbers, row, col);
      printingMatrix(numbers, row, col);
   }
   public static void fillingMatrix(Scanner scan, int num[][], int rows, int columns)
   {
      System.out.println("Please enter elements in matrix : ");
      for(int a = 0; a < rows; a++)
      {
         for(int b = 0; b < columns; b++)
         {
            num[a][b] = scan.nextInt();
         }
      }
   }

   public static void printingMatrix(int num[][], int rows, int columns)
   {
      System.out.println("Printing 2d array in matrix form : ");
      for(int a = 0; a < rows; a++)
      {
         for(int b = 0; b < columns; b++)
         {
            System.out.print(num[a][b] + "\t");
         } 
         System.out.println();
      }
   }
}import java.util.Scanner;

public class Main 
{
   public static void main(String[] args) 
   {
      Scanner sc = new Scanner(System.in); 
      System.out.println("Please enter number of matrix rows : "); 
      int row = sc.nextInt(); 
      System.out.println("Please enter number of matrix columns : "); 
      int col = sc.nextInt();
      int numbers[][] = new int[row][col];
      fillingMatrix(sc, numbers, row, col);
      printingMatrix(numbers, row, col);
   }
   public static void fillingMatrix(Scanner scan, int num[][], int rows, int columns)
   {
      System.out.println("Please enter elements in matrix : ");
      for(int a = 0; a < rows; a++)
      {
         for(int b = 0; b < columns; b++)
         {
            num[a][b] = scan.nextInt();
         }
      }
   }

   public static void printingMatrix(int num[][], int rows, int columns)
   {
      System.out.println("Printing 2d array in matrix form : ");
      for(int a = 0; a < rows; a++)
      {
         for(int b = 0; b < columns; b++)
         {
            System.out.print(num[a][b] + "\t");
         } 
         System.out.println();
      }
   }
}