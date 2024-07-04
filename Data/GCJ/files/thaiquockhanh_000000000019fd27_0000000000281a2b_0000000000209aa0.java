import java.util.*;
import java.lang.*;
import java.io.*;

class Solution
{
    public static void main (String[] args) throws java.lang.Exception
    {
        Scanner scan = new Scanner(System.in);
        int totalTests = scan.nextInt();
        for(int test = 1; test<=totalTests; test++){
            int n = scan.nextInt();
            int k = scan.nextInt();
            List<int[][]> latinSquares = generate(n);
            boolean found = false;
            for(int[][] square : latinSquares) {
                int trace = 0;
                for(int i=0; i<n; i++) trace += square[i][i];
                if(trace == k) {
                    found = true;
                    System.out.printf("Case #%d: POSSIBLE%n", test);
                    for (int[] x : square)
                    {
                        for (int y : x)
                        {
                            System.out.print(y + " ");
                        }
                        System.out.println();
                    }
                    break;
                }
            }
            if(!found) System.out.printf("Case #%d: IMPOSSIBLE%n", test);
        }

    }

    private static List<int[][]> generate(int n) {
        List<int[][]> result = new ArrayList<>();
        int[][] base = new int[n][n];
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) base[i][j] = 1+(n-i+j)%n;
        }
        permute(base, 0, n-1, result);
        List<int[][]> finalResult = new ArrayList<>();
        for(int[][] arr : result) {
            permuteColumn(arr, 0, n-1, finalResult);
        }
        return finalResult;
    }

    private static void permute(int[][] arr, int l, int r, List<int[][]> arrList) {
        if(l==r) {
            int[][] clone = Arrays.stream(arr).map(int[]::clone).toArray(int[][]::new);
            arrList.add(clone);
        } else {
            for (int i = l; i <= r; i++) {
                int[] temp = arr[l].clone();
                arr[l] = arr[i].clone();
                arr[i] = temp.clone();
                permute(arr, l + 1, r, arrList);
                temp = arr[l].clone();
                arr[l] = arr[i].clone();
                arr[i] = temp.clone();
            }
        }
    }

    private static void permuteColumn(int[][] arr, int l, int r, List<int[][]> arrList) {
        if(l==r) {
            int[][] clone = Arrays.stream(arr).map(int[]::clone).toArray(int[][]::new);
            arrList.add(clone);
        } else {
            for (int i = l; i <= r; i++) {
                int sz = arr.length;
                for(int a = 0; a<sz; a++) {
                    int temp = arr[a][i];
                    arr[a][i] = arr[a][l];
                    arr[a][l] = temp;
                }
                permute(arr, l + 1, r, arrList);
                for(int a = 0; a<sz; a++) {
                    int temp = arr[a][i];
                    arr[a][i] = arr[a][l];
                    arr[a][l] = temp;
                }
            }
        }
    }
}