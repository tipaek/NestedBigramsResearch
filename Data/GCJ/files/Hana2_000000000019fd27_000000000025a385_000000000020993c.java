import java.util.Scanner;

public static void main(String[] args){
    int t = input.nextInt(); 
    for(int i=0 ; i<t ; i++){
        int n = input.nextInt();
        int[][] arr = new int[n][n];
        int k = 0;
        for (int j=0 ; j<n ; j++)
            for (int c=0 ; c<n ; c++)
                if(c == k)
                    k++;
        int r = 0;
        for (int j=0 ; j<n ; j++)
            for (int c=0 ; c<arr[j].length ; c++)
                if (j != c && arr[c]==arr[j]{
                    r++;
                    continue;
                }
        int co = 0;
        for (int j=0 ; j<arr[n].length ; j++)
            for (int c=0 ; c<n ; c++)
                if (j != c && arr[c]==arr[j]{
                    co++;
                    continue;
                }
    }/*end for*/
}/*end main*/
