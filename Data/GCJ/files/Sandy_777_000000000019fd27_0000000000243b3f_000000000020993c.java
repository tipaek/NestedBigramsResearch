import java.io.*; 
import java.util.*;
  
class Trace { 
  
// Size of given matrix 
static  int MAX = 100; 

static int findTrace(int matrix[][], int n) 
{ 
    int k = 0; 
    for (int i=0; i<n; i++) 
        k += matrix[i][i]; 
    return k; 
} 

 public static int countIdenticalRows(int matrix[][]) 
    { 
  
        int r = 0; 
  
        for (int i = 0; i < matrix.length; i++) { 
  
             HashSet<Integer> hs = new HashSet<>(); 
  
            for (int j = 0; j < matrix[i].length; j++) { 
  
                  hs.add(matrix[i][j]); 
            } 
  
         if (hs.size() == 1) 
                r++; 
        } 
  
        return r; 
    } 


public static int countIdenticalColumns(int matrix[][]) 
    { 
  
        int c = 0; 
  
        for (int i = 0; i < matrix.length; i++) { 
  
             HashSet<Integer> hs = new HashSet<>(); 
  
            for (int j = 0; j < matrix[i].length; j++) { 
  
                  hs.add(matrix[i][j]); 
            } 
  
         if (hs.size() == 1) 
                c++; 
        } 
  
        return c; 
    } 




public static void main (String[] args) { 
Scanner sc=new Scanner(System.in);
int T=sc.nextInt();
int N;
 int[][] matrix = new int[N][N];

for(int x=0;x<T;x++)
{
N=sc.nextInt();
{

for (int i = 0; i < N; i++)
          {
              for (int j = 0; j < N; j++)
              {
                  matrix[i][j] = sc.nextInt();
              }
          }

}
}
int k1=(findTrace(matrix,4);
int k2=(findTrace(matrix,4);
int k3=(findTrace(matrix,3);



int r=countIdenticalRows(matrix);

int c=countIdenticalColumns(matrix);

for(int a=1;a<=T;a++)
{
System.out.println("Case #"+T+" "+k+" "+" "+r+" "+c);
}
}
}