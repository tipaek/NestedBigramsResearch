import java.util.*;
import java.io.*;

public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    int N;
    N = Integer.parseInt(scan.nextLine());
    for(int l = 1; l <= N; l++){
        M = Integer.parseInt(scan.nextLine());
        int[][] array = new int[M][M];
        StringTokenizer line;
        
        for(int i = 0; i < M; i++){
            line = new StringTokenizer(scan.nextLine());
            for(int k = 0; k < M; k++){
                array[i][k] = Integer.parseInt(line.nextToken());
            }
        }
        System.out.println("no error")
    }
}