import java.util.*;
public class Vestigium{
    int findTrace(int M[][N], int N){
        int k = 0; //trace
        for(int i=0;i<N;i++)
          k +=M[i][i];
        return k;
    }
    int countIdenticalRows(int M[][N], int N){
        int r=0; //row count
        for(int t=0;i<N;i++){
            HashSet<Integer> hs = new HashSet<>();
            for(int j=0;j<N;j++){
                hs.add(M[i][j]);
            }
            if(hs.size()==1)
              r++;
        }
        return r;
    }
    int countIdenticalColumns(int M[][N], int N){
        int c = 0; //column count
        for(int i=0;i<N;i++){
            HashSet<Integer> hs=new HashSet<>();
            for(int j=0;j<N;j++){
                hs.add(M[j][i]));
            }
            if(hs.size()==1)
              c++;
        }
        return c;
    }
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        int T = in.nextInt(); //testcases
        int N = in.nextInt(); //size of matrix
        int M[][] = new int[N][N];
        for(int i=0;i<N;i++){
            for(int k=0;k<N;k++){
                M[i][k]=in.nextInt();
            }
        }
        for(int j=1;j<=T;j++){
            System.out.print("Case : #"+j+": "+trace(M,N));
            System.out.print(" "+countIdenticalRows(M,N));
            System.out.print(" "+countIdenticalColumns(M,N));
            System.out.println();
        }
    }
}