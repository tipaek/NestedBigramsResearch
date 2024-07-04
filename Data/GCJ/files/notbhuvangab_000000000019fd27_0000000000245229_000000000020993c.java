package parenting;

import java.util.Arrays;
import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int t =0; t<T; t++){
            int N = in.nextInt();
            int[][] arr = new int[N][4];
            for(int n=0; n < N; ++n){
                arr[n][0] = in.nextInt();
                arr[n][1] = in.nextInt();
                arr[n][2] = n;
                arr[n][3] = 0;
            }
            solve(arr, t);
        }
    }

    public static void solve(int[][] arr, int t){
        StringBuilder sb = new StringBuilder();
        sb.append("Case #");
        sb.append(t+1);
        sb.append(": ");
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);
        int c = 0, j = 0;
        for(int i=0; i<arr.length; i++){
            int start = arr[i][0];
            int end = arr[i][1];
            if(start >= c){
                c = end;
                arr[i][3] = 1;
            }else if(start >= j){
                j = end;
                arr[i][3] = 2;
            }else{
                System.out.println(String.format("Case #%d: IMPOSSIBLE", t+1));
                return;
            }
        }
        Arrays.sort(arr, (a, b) -> a[2] - b[2]);
        for(int i=0; i<arr.length; i++){
            if(arr[i][3] ==1){
                sb.append("C");
            }else{
                sb.append("J");
            }
        }
        System.out.println(sb.toString());
        return;
    }
}