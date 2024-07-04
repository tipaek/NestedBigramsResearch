package ca.mobilelive.investor.api.v1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Verdict {

    public static void main(String[] args) {
        List<Integer> row1 = new ArrayList<Integer>() {{ add(1); add(2); add(3); }};
        List<Integer> row2 = new ArrayList<Integer>() {{ add(3); add(2); add(3); }};
        List<Integer> row3 = new ArrayList<Integer>() {{ add(2); add(3); add(1); }};
        List<List<Integer>> lines = new ArrayList<List<Integer>>() {{ add(row1); add(row2); add(row3); }};
        Verdict.findDuplicate(lines);
    }

    private static void findDuplicate(List<List<Integer>> lines) {
        int i = 1;
        for (List<Integer> row : lines) {
            final Set<Integer> finalSet = new HashSet<>();
            final Set<Integer> comparingSet = new HashSet<>();
            final StringBuffer sb = new StringBuffer();
            for (Integer integer : row) {
                if (!comparingSet.add(integer)) {
                    finalSet.add(integer);
                }
                if (!finalSet.isEmpty()) {
                    sb.append("Case #").append(i).append(":");
                    row.forEach(x -> sb.append(" ").append(x));
                    i++;
                }
                System.out.println(sb.toString().trim());

            }
        }
    

}
