import java.util.*;

public  class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int t=0; t<T; t++) {
            int U = sc.nextInt();
            int[] Q = new int[10000];
            String[] R = new String[10000];
            for (int i=0; i<10000; i++) {
                Q[i] = sc.nextInt();
                R[i] = sc.next();
            }

            System.out.println("Case #" + (t+1) + ": " + solve(U, Q, R));
        }
    }

    private static String solve(int U, int[] Q, String[] R) {
        HashSet<Character>[] set = new HashSet[10];
        for (int i=0; i<10; i++) {
            set[i] = new HashSet();
        }

        for (int i=0; i<10000; i++) {
            if (Q[i] < 10) {
                // Q[i]�1��らR[i]�必�1��, Mi �Qi以下
                set[Q[i]].add(R[i].charAt(0));
            } else {
                if (R[i].length() == 1) {
                    // Q[i]�2��R[i]�1��ら, Mi�9以下
                    set[9].add(R[i].charAt(0));
                } else {
                    // Q[i]�2��R[i]�2��ら，10���1����れ�れ考�られる
                    // 少���もMi�10���Qi�10��以下
                    set[Q[i]/10].add(R[i].charAt(0));

                    if (Q[i]/10 == 1) {
                        // Qi�10-19�R[i]�2��ら, Mi�10���1�固定� Mi�1���以下Q[i]�1��以下
                        // set[Q[i]%10].add(R[i].charAt(1));
                    } else {
                        // Mi�20以上�ら, Mi�10���ら繰り下���り�る��Qi�1���0以下 (任�)
                        set[0].add(R[i].charAt(1));
                    }
                }
            }
        }

        String D = "";

        /*
        for (int i=0; i<10; i++) {
            System.err.println("size of set[" + i + "]=" + set[i].size());
        }
        */

        set[0].removeAll(set[9]);
        // System.err.println("size of set[0]=" + set[0].size());
        D += String.valueOf((char)set[0].toArray()[0]);

        HashSet<Character> hit = new HashSet();
        for (int i=1; i<=9; i++) {
            set[i].removeAll(hit);
            D += String.valueOf((char)set[i].toArray()[0]);

            hit.add(D.charAt(i));
        }

        return D;
    }
}
