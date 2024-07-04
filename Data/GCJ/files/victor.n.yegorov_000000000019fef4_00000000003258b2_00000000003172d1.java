import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= t; ++i) {
            int output = solve(sc);
            sb.append("Case #").append(i).append(": ").append(output).append("\n");
        }
        System.out.print(sb);
    }

    private static int solve(Scanner sc) {
        int n = sc.nextInt(), d = sc.nextInt();
        BigInteger[] a = IntStream.range(0, n).mapToObj(i -> sc.nextBigInteger()).sorted().toArray(BigInteger[]::new);
        if (n == 1) {
            return d - 1;
        }

        if (d == 2) {
            for (int i = 1; i < n; ++i) {
                if (a[i].equals(a[i - 1])) {
                    return 0;
                }
            }
            return 1;
        }
        if (d == 3) {
            if (n == 2) {
                return a[0].multiply(BigInteger.valueOf(2)).equals(a[1]) ? 1 : 2;
            }

            Map<BigInteger, Long> map = Arrays.stream(a).collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            if (map.values().stream().anyMatch(e -> e >= 3)) {
                return 0;
            }

            Optional<BigInteger> minPair = map.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).min(Comparator.naturalOrder());
            if (minPair.isPresent() && map.keySet().stream().max(Comparator.naturalOrder()).filter(e -> e.compareTo(minPair.get()) > 0).isPresent()) {
                return 1;
            }

            if (map.keySet().stream().anyMatch(e -> map.containsKey(e.multiply(BigInteger.valueOf(2))))) {
                return 1;
            }

            return 2;
        }

        //TODO implement
        return d - 1;
    }

}
