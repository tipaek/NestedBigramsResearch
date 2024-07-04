import java.util.*;
import java.io.*;

public class Solution {
  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt();
    for (int i = 1; i <= t; ++i) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();

        List<ArrayList<Integer>> JIntervals = new ArrayList<>();
        List<ArrayList<Integer>> CIntervals = new ArrayList<>();

        StringBuilder ans = new StringBuilder();
        boolean breakNow = false;

        for (int i = 1; i <= t; ++i) {
            int intervals = in.nextInt();

            for(int interval = 0; interval < intervals; interval++) {
                int start = in.nextInt();
                int end = in.nextInt();

                boolean overlap = checkIfOverlap(JIntervals, start, end);

                if(overlap) {
                    overlap = checkIfOverlap(CIntervals, start, end);
                    if(overlap) {
                        ans = new StringBuilder();
                        ans.append("Impossible");
                        break;
                    }
                    else {
                        ans.append("C");
                        CIntervals.add(new ArrayList<>(Arrays.asList(start, end)));
                    }
                }
                else {
                    ans.append("J");
                    JIntervals.add(new ArrayList<>(Arrays.asList(start, end)));
                }
            }
            System.out.println("Case #" + i + ": " +  ans);
        }
    }
}