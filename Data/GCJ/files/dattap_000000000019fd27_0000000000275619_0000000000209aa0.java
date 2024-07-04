import java.util.Scanner;
import java.util.TreeSet;

public class Solution {
    static int[] a ;
    static String s2 = "";
    public static void main (String[] args) {
        Scanner s = new Scanner(System.in);
        int t = Integer.parseInt(s.nextLine());
       forloop: for (int ii = 1; ii <= t; ii++) {
            String[] d = s.nextLine().split(" ");
            int N = Integer.parseInt(d[0]);
            int K = Integer.parseInt(d[1]);
            int[][] m = new int[N][N];
            boolean[][] b = new boolean[N][N];
            int f=0;
            a = new int[N];

            int i1 =0;
           i1=0;
           for (int i2 = 1; i2 <= N; i2++) {

               a[i1++] = i2;

           }
           int c2 =  0;
            while(f++ <= 500) {


                c2++;
                i1 = 0;
                for(int i=0; i < N; i++){
                    s2 = s2 + a[i];
                }
                permute(s2,0, N-1);

                //System.out.println(s2);
                for(int i=0; i < N; i++){
                    a[i] = Integer.parseInt("" + s2.charAt(i));

                }
                s2 = "";

                for (int k = 0; k < N; k++) {
                    i1 = 0;
                    for (int l = 0; l < N; l++) {
                        m[k][l] = a[i1++];
              1         // System.out.print(m[k][l] + " ");
                    }
                   // for(int j=0; j < (c2+1)%N; j++)
                    rotateRight(a);
                   // System.out.println();

                }
                int sum = 0;
                for (int i = 0; i < N; i++) {
                    sum = sum + m[i][i];
                }
             String s2 = "";   if (sum == K) {
                    int r = 0;
                    for (int i = 0; i < N; i++) {
                        TreeSet ts = new TreeSet();
                        for (int j = 0; j < N; j++) {
                            ts.add(m[i][j]);
                        }
                        if (ts.size() == N) {
                            r++;
                        }
                    }
                    int c = 0;
                    for (int i = 0; i < N; i++) {
                        TreeSet ts = new TreeSet();
                        for (int j = 0; j < N; j++) {
                            ts.add(m[j][i]);
                        }
                        if (ts.size() == N) {
                            c++;
                        }
                    }
                    if (r == N && c == N) {
                        System.out.println("Case #" + ii + ": " + "POSSIBLE");
                        for (int i = 0; i < N; i++) {
                            for (int j = 0; j < N; j++) {
                                System.out.print(m[i][j] + " ");
                            }
                            System.out.println();
                        }
                        continue forloop;
                    }
                }
            }

           for(int i=0; i < N; i++){
               s2 = s2 + a[i];
           }
           permute(s2,0, N-1);

           //System.out.println(s2);
           for(int i=0; i < N; i++){
               a[i] = Integer.parseInt("" + s2.charAt(i));

           }
           s2 = "";

           for (int k = 0; k < N; k++) {
               i1 = 0;
               for (int l = 0; l < N; l++) {
                   m[k][l] = a[i1++];
                   // System.out.print(m[k][l] + " ");
               }
               // for(int j=0; j < (c2+1)%N; j++)
               rotateLeft(a);
               // System.out.println();

           }
           int sum = 0;
           for (int i = 0; i < N; i++) {
               sum = sum + m[i][i];
           }
           String s2 = "";   if (sum == K) {
               int r = 0;
               for (int i = 0; i < N; i++) {
                   TreeSet ts = new TreeSet();
                   for (int j = 0; j < N; j++) {
                       ts.add(m[i][j]);
                   }
                   if (ts.size() == N) {
                       r++;
                   }
               }
               int c = 0;
               for (int i = 0; i < N; i++) {
                   TreeSet ts = new TreeSet();
                   for (int j = 0; j < N; j++) {
                       ts.add(m[j][i]);
                   }
                   if (ts.size() == N) {
                       c++;
                   }
               }
               if (r == N && c == N) {
                   System.out.println("Case #" + ii + ": " + "POSSIBLE");
                   for (int i = 0; i < N; i++) {
                       for (int j = 0; j < N; j++) {
                           System.out.print(m[i][j] + " ");
                       }
                       System.out.println();
                   }
                   continue forloop;
               }

           }
           System.out.println("Case #" +  ii + ": " + "IMPOSSIBLE");
       }



        }

    static void rotateLeft(int[] a){
        int t = a[0];
        for(int i=0; i < a.length-1; i++){
            a[i] = a[i+1];
        }
        a[a.length-1] = t;
    }
    static void rotateRight(int[] a){
        int t = a[a.length-1];
        for(int i=a.length - 1; i >= 1; i--){
            a[i] = a[i-1];
        }
        a[0] = t;
    }
    private static void permute(String str, int l, int r)
    {
        if (l == r) {
            s2 = str;
        }
        else
        {
            for (int i = l; i <= r; i++)
            {
                str = swap(str,l,i);
                permute(str, l+1, r);
                str = swap(str,l,i);
            }
            //return str;
        }

    }


    public static String swap(String a, int i, int j)
    {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i] ;
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }
}

