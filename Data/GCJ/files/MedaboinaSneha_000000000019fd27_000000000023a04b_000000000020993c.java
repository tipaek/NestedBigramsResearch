import java.util.*;
import java.io.*;
import java.lang.*;
public class Solution{
    public static int rows(int arr[][]){
    int cnt=0;
    for(int i=0;i<arr.length;i++){
        HashSet<Integer> hs=new HashSet<>();
        for(int j=0;j<arr[i].length;j++){
            hs.add(arr[i][j]);
        }
        if(hs.size()==1)
            cnt++;
    }
    return cnt;
}
public static int columns(int arr[][]){
    int cnt=0;
    for(int i=0;i<arr.length;i++){
        HashSet<Integer> hs=new HashSet<>();
        for(int j=1;j<arr[i].length;j++){
            hs.add(arr[j][i]);
        }
        if(hs.size()==1)
            cnt++;
    }
    return cnt;
}
public static void main(String[] args){
    Scanner snr=new Scanner(System.in);
    int t=snr.nextInt();
    while(t-->0){
        int n=snr.nextInt();
        int[][] arr=new int[n][n];
        int sum=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                arr[i][j]=snr.nextInt();
                if(i==j)
                    sum=sum+arr[i][i];
            }
        }
        for(int i=1;i<=t;i++){
            System.out.print("Case #:"+i+);
            System.out.print(" "+sum);
            System.out.print(" "+rows(arr));
            System.out.print(" "+columns(arr));
            System.out.println();}
    }
}
}