import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int tests = in.nextInt();

        int n, trace, current;
        List<Set<Integer>> rowSets, columnSets;
        List<Boolean> rowHasDuplicates, columnHasDuplicates;
        for (int t = 1; t <= tests; ++t) {
            n = in.nextInt();
            trace = 0;
            rowSets = Stream.generate(HashSet<Integer>::new).limit(n).collect(Collectors.toList());
            columnSets = Stream.generate(HashSet<Integer>::new).limit(n).collect(Collectors.toList());
            rowHasDuplicates = Stream.generate(() -> false).limit(n).collect(Collectors.toList());
            columnHasDuplicates = Stream.generate(() -> false).limit(n).collect(Collectors.toList());

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    current = in.nextInt();
                    if (!rowHasDuplicates.get(i) && !rowSets.get(i).add(current)) {
                        rowHasDuplicates.set(i, true);
                    }
                    if (!columnHasDuplicates.get(j) && !columnSets.get(j).add(current)) {
                        columnHasDuplicates.set(j, true);
                    }
                    if (i == j) {
                        trace += current;
                    }
                }
            }

            System.out.printf("Case #%d: %d %d %d\n", t, trace,
                    rowHasDuplicates.stream().filter(Boolean::booleanValue).count(),
                    columnHasDuplicates.stream().filter(Boolean::booleanValue).count());
        }
    }
}