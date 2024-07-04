import java.io.*;
import java.util.*;

class Solution{
    public static void main(String args[])throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t=Integer.parseInt(br.readLine());
        for(int k=0;k<t;k++){
            int n = Integer.parseInt(br.readLine());
            Integer a[][] = new Integer[n][n];
            for(int i=0;i<n;i++){
                a[i] = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
            }
            int rc =0;
            int cc = 0;
            int trace = 0;
            for(int i=0;i<n;i++){
                int row[] = new int[n];
                int col[] = new int[n];
                trace+= a[i][i];
                boolean rd = false;
                boolean cd = false;
                for(int j=0;j<n&&!(rd&&cd);j++){
                    row[a[i][j]-1]++;
                    if(row[a[i][j]-1]>1&&!rd){
                        rc++;
                        rd=true;
                    }
                    col[a[j][i]-1]++;
                    if(col[a[j][i]-1]>1&&!cd){
                        cc++;
                        cd=true;
                    }
                }
            }
			sb.append("Case #").append(k+1).append(": ").append(trace).append(" ").append(rc).append(" ").append(cc).append("\n");
            
        }
		System.out.println(sb);
    }
}