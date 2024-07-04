import java.util.*;

class Test{
    static int countIdenticalrows(int mat[][],int N){
        int count=0;
        for(int i=0;i<N,i++){
            HashSet<Integer> hs = new HashSet<>();
            for(int j=0;j<N;j++){
                if(hs.contains(mat[i][j])){
                    count++;
                    break;
                }
                hs.add(mat[i][j]);
            }
        }
        return count;
    }
    static int findTrace(int mat[][], int n) { 
        int sum = 0; 
        for (int i=0; i<n; i++) 
            sum += mat[i][i]; 
        return sum; 
    } 
    static void transpose(int A[][],int N) 
    { 
        for (int i = 0; i < N; i++) 
            for (int j = i+1; j < N; j++) 
            { 
                 int temp = A[i][j]; 
                 A[i][j] = A[j][i]; 
                 A[j][i] = temp; 
            } 
    } 
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int T=sc.nextInt();
        for(int m=0;m<t;m++){
            int N=sc.nextInt();
            int[][] mat=new int[N][N];
            for(int i=0;i<N;i++){
                for(int j=0;j<N;j++){
                    mat[i][j]=sc.nextInt();
                }
            }
            int trace=findTrace(mat,N);
            int rows=countIdenticalrows(mat,N);
            transpose(mat,N);
            int colms=countIdenticalrows(mat,N);
            System.out.println("Case #"+(m+1)+": "+
            trace+" "+rows+" "+colms);
        }
    }
}