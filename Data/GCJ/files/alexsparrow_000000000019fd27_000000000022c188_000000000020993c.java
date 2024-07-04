import java.util.Scanner;

public class Soulation {
    private static final Scanner scan = new Scanner(System.in);
    static int row = 0;
    static int col = 0;
    
    public static voiid main(string[] arg) {
        int t = scan.nextInt();
        for(int i = 0; i < t; ++t){
            int dia = 0;
            int n = scan.nextInt();
            int[][] arr = new int[n][n];
            for (int i = 0; i < n; ++i){
                for (int j = 0; j < n; ++j) {
                    arr[i][j] = scan.nextInt();
                }
                dia += arr[i][i];
            }
            System.out.print(dia + " ")
            for (int i = 0; i < n; ++i) {
                add(arr[i]);
            }
            System.out.print(row + " ")
            row = 0;
            for (int i = 0; i < n / 2; i++) { 
        for (int j = i; j < n - i - 1; j++) { 
  
            int temp = a[i][j]; 
            a[i][j] = a[n - 1 - j][i]; 
            a[n - 1 - j][i] = a[n - 1 - i][n - 1 - j]; 
            a[n - 1 - i][n - 1 - j] = a[j][n - 1 - i]; 
            a[j][n - 1 - i] = temp; 
        } 
    } 
    
    for(int i = 0; i < n; ++i) {
        add(arr[i]);
    }
    System.out.print(row + " ")
        }
    }
    
    private static void find(int arr[]){
        int[] cnt = new int[100];
       for (int i = 0; i < arr.length; ++i){
           ++cnt[arr[i];
           if(arr[i] > 1) {
               ++row;
               return ;
           } 
       }
    }
}