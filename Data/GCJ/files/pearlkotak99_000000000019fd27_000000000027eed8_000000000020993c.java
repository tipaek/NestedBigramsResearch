java.util.Scanner;

class Main{
  public static void main(String[] args){
    int t, n;
    int temp;
    boolean flag = false;
    int rows_repeat = 0, sum = 0;
    int columns_repeat = 0;
    Scanner sc = new Scanner(System.in);
    t = sc.nextInt();
    int[][] result = new int[t][3];
    for(int i = 1; i <= t; i++){
      rows_repeat = 0;
      columns_repeat = 0;
      sum = 0;
      n = sc.nextInt();
      int[][] ar  = new int[n][n];
      for(int j = 0; j < n; j++){
        for(int k = 0; k < n; k++){
          temp = sc.nextInt();
          if(temp >= 1 && temp <= n){
            ar[j][k] = temp;
            if(j == k){
              sum += ar[j][k];
            }
          }else{
            System.out.println("error.");
            flag = true;
          }
        }
      }
      if(flag){
        continue;
      }
      boolean[] check = new boolean[n];
      for(int j = 0; j < n; j++){
        for(int k = 0; k < n; k++){
          temp = ar[j][k];
          if(check[temp]){
            rows_repeat++;
            break;
          }
          check[temp] = true;
        }
        for(int k = 0; k < n; k++){
          check[k] = false;
        }
      }

      for(int k = 0; k < n; k++){
        check[k] = false;
      }

      for(int j = 0; j < n; j++){
        for(int k = 0; k < n; k++){
          temp = ar[k][j];
          if(check[temp]){
            columns_repeat++;
            break;
          }
          check[temp] = true;
        }
        for(int k = 0; k < n; k++){
          check[k] = false;
        }
      }
      result[t][0] = sum;
      result[t][1] = rows_repeat;
      result[t][2] = columns_repeat;
    }
    for(int i = 0; i < t; i++){
      System.out.println("Case #" + t + ": " + sum +" " + rows_repeat + " " + columns_repeat);
    }
  }
}