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
