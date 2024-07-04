import java.io.*;
import java.util.Scanner;
public class LatinCheck{
    public int[] check(int[N][N] m){
        int r,c=0;
        for(int i=0; i<m[0].length; i++){
            for(int j=0; j<m[1].length; j++){
                int pivot = m[i][j];
                for(int k=j+1; k<m[0].length; k++){
                    if(m[i][k] == pivot)
                    break;
                }
                r++;
                for(int l=i+1; l<matrix[1].length; l++){
                    if(m[l][j] == pivot)
                    break;
                }
                c++;
            
            }
        }
        int ar[0]=r;
        ar[1]=c;
        return ar;
    }
    static int trace(int m[N][N]){
        int sum=0;
        for(int i=0; i<n; i++)
         sum=sum+m[i][i];
         return sum;
    }
    public static void main(String[] args){
        Scanner myObj = new Scanner(System.in);
        int T = myObj.nextInt();
        for(int p=1; p<=T; p++){
            int N = myObj.nextInt();
            int m[N][N];
            for(int v = 0; v<N; v++)
            {
                for(int u=0; u<N; u++){
                    int value= myObj.nextInt();
                    m[i][j]=value;
                }
            }
            LatinCheck obj = new LatinCheck();
            int traceValue = obj.trace(m[N][N]);
            int ar1[] = obj.check(m[N][N]);
            System.out.println("Case #"+p+:+" "+tracaValue+" "+ar[1]+" "+ar[1]);
        }
    }
}
