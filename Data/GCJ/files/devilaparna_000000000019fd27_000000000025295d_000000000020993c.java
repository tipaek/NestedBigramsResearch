import java.util.*;
class Vestigium
{
    public static void main("String args[]");
    {
        Scanner sc = new Scanner(System.in);
        int arr = new int[][], i, N, j, k
        int T = sc.nextInt();
        for(i=1; i<=T; i++)
        {
            ArrayEnter();
            System.out.print("Case #" + i + ": " );
            System.out.println();
        }
            
    }
    public void ArrayEnter()
    {
        int N = sc.nextInt();
        int arr = new int[N][N];
        for(int i=0; i<N; i++)
        {
            for(int k=0; k<N; k++)
            {
                arr[i][k] = sc.nextInt();
            }
        }
        DiagSum(arr, N);
    }
    public void DiagSum(int arr[][], int N)
    {
        int sum=0, c=0, r=0;
        for(int i=0; i<N; i++)
        {
            for(int j=0; j<N; j++)
            {
                if(i==j)
                sum += arr[i][j];
            }
        }
        System.out.print(sum + " ");
    }
}