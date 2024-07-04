import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {
    private static final int MAX_JUMP = 7;
    private static final HashMap<Integer, ArrayList<ArrayList<Integer>>> sub = new HashMap<>();
    private static final InputStreamReader r = new InputStreamReader(System.in);
    private static final BufferedReader br = new BufferedReader(r);
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        initializeSubsets(MAX_JUMP);
        int t = sc.nextInt();
        for (int i = 1; i <= t; i++) {
            System.out.print("Case #" + i + ": ");
            solve();
        }
    }

    private static void solve() throws Exception {
        int X = sc.nextInt();
        int Y = sc.nextInt();
        for (int jump = 1; jump <= MAX_JUMP; jump++) {
            for (ArrayList<Integer> al : sub.get(jump)) {
                long x = 0, y = 0;
                for (int i = 0; i < al.size(); i++) {
                    int d = al.get(i);
                    long v = (long) Math.pow(2, i);
                    switch (d) {
                        case 0 -> y += v;
                        case 1 -> y -= v;
                        case 2 -> x += v;
                        case 3 -> x -= v;
                    }
                }
                if (x == X && y == Y) {
                    StringBuilder sb = new StringBuilder();
                    for (int d : al) {
                        switch (d) {
                            case 0 -> sb.append("N");
                            case 1 -> sb.append("S");
                            case 2 -> sb.append("E");
                            case 3 -> sb.append("W");
                        }
                    }
                    System.out.println(sb);
                    return;
                }
            }
        }
        System.out.println("IMPOSSIBLE");
    }

    private static void initializeSubsets(int n) {
        if (n == 1) {
            ArrayList<ArrayList<Integer>> al = new ArrayList<>();
            al.add(new ArrayList<>(Arrays.asList(0)));
            al.add(new ArrayList<>(Arrays.asList(1)));
            al.add(new ArrayList<>(Arrays.asList(2)));
            al.add(new ArrayList<>(Arrays.asList(3)));
            sub.put(n, al);
            return;
        }
        initializeSubsets(n - 1);
        ArrayList<ArrayList<Integer>> al = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rec = sub.get(n - 1);
        for (ArrayList<Integer> x : rec) {
            ArrayList<Integer> a = new ArrayList<>(x);
            ArrayList<Integer> b = new ArrayList<>(x);
            ArrayList<Integer> c = new ArrayList<>(x);
            ArrayList<Integer> d = new ArrayList<>(x);
            a.add(0);
            b.add(1);
            c.add(2);
            d.add(3);
            al.add(a);
            al.add(b);
            al.add(c);
            al.add(d);
        }
        sub.put(n, al);
    }
}