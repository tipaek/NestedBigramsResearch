import java.io.*;;
import java.util.*;

public class Solution {
    static StreamTokenizer input;
    static int nextInt() throws Exception {
        input.nextToken();
        return (int) input.nval;
    }

    static String solve(int[][] a) {
        String r = "";
        int lastTime = 0;
        for (int [] ar : a)
            if (ar[1] > lastTime) lastTime = ar[1];
        
        boolean[] ctime = new boolean[lastTime];
        boolean[] jtime = new boolean[lastTime];


        for (int i = 0; i < a.length; i++) {
            int s = a[i][0]; int e = a[i][1];
            boolean checkJ = false;

            // if c ever occupied, need to check j instead
            for (int j = s; j < e; j++) {
                if (ctime[j]) checkJ = true;
            }
            if (checkJ) {
                // if j already occupied, must return impossible
                for (int j = s; j < e; j++) {
                    if (jtime[j]) return "IMPOSSIBLE";
                }
                // if loop runs without prior occupation, add it to j time
                for (int j = s; j < e; j++) {
                    jtime[j] = true;
                }
                r += "J";
            } else { // if c not occupied, give task to c
                for (int j = s; j < e; j++) 
                    ctime[j] = true;
                r += "C";
            }
        }
        
        return r;
    }

    public static void main(String [] args) throws Exception {
        //input = new StreamTokenizer(new BufferedReader( new InputStreamReader(System.in) ));
        input = new StreamTokenizer(new BufferedReader( new FileReader("f.in") ));

        int T = nextInt();
        for (int i = 1; i <= T; i++) {
            int N = nextInt();
            int [][] a = new int[N][2];

            for (int j = 0; j < N; j++) {
                a[j][0] = nextInt();
                a[j][1] = nextInt();
            }

            System.out.println("Case #" + i + ": " + solve(a));
        }        
    }
}