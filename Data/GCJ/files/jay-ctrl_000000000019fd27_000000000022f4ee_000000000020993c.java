import java.util.*;
class Solution {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        int t=s.;nextInt();
        while(t>0) {
            t--;
            int case=1;
            int row=0;
            int col=0;
            int n=s.nextInt();
            int a=new int[n][n];
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    a[i][j]=s.nextInt();
                }
            }
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    if(a[Math.abs(a[i][j])-1] < 0) {
                        row++;
                        break;
                    }
                    else {
                        a[Math.abs(a[i][j])-1]= -a[Math.abs(a[i][j])-1];
                    }
                }
            }
            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    if(a[Math.abs(a[j][i])-1] < 0) {
                        col++;
                        break;
                    }
                    else {
                        a[Math.abs(a[j][i])-1]= -a[Math.abs(a[j][i])-1];
                    }
                }
            }
            int k=0;
            for(int i=0;i<n;i++) {
                k+=a[i][i];
            }
            System.out.println("case #"+case+": "+k+" "+row+" "+col);
        }
    }
}