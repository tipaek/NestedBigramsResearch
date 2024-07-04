import java.util.*;

public class Solution {

    public static void main(String[] args) {
        solve(new Scanner(System.in));
    }
    

    public static void solve(Scanner scanner) {
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int count = scanner.nextInt();
            List<String> patternList = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                patternList.add(scanner.next());
            }
            String result = getResult(patternList);
            System.out.print("Case #" + (i + 1) + ": ");
            System.out.print(result);
            if (i != n - 1)
                System.out.println();
        }
    }

    private static String getResult(List<String> patternList) {
        final int MAX_LENGTH = 10000;

        String start = "";
        String end = "";

        Set<String> middleSet = new HashSet<>();

        for (String pattern : patternList) {
            for (int i = 0; i < start.length(); i++) {
                if (i >= pattern.length()) {
                    return "*";
                }
                if (pattern.charAt(i) == '*') {
                    break;
                }
                if (pattern.charAt(i) != start.charAt(i)) {
                    return "*";
                }
            }
            for (int j = end.length() - 1; j >= 0; j--) {
                int m = j + pattern.length() - end.length();
                if (m < 0) {
                    return "*";
                }
                if (pattern.charAt(m) == '*') {
                    break;
                }
                if (pattern.charAt(m) != end.charAt(j)) {
                    return "*";
                }
            }

            String[] splits = Arrays.stream(pattern.split("\\*")).filter(s -> !s.isEmpty()).toArray(String[]::new);
            if (splits.length == 0) {
                continue;
            }

            if (pattern.charAt(0) != '*' && splits[0].length() > start.length()) {
                start = splits[0];
            }
            if (pattern.charAt(pattern.length() - 1) != '*' && splits[splits.length - 1].length() > end.length()) {
                end = splits[splits.length - 1];
            }
            int minK = (pattern.charAt(0) == '*') ? 0 : 1;
            int maxK = (pattern.charAt(pattern.length() - 1) == '*') ? splits.length : splits.length - 1;
            for (int k = minK; k < maxK; k++) {
                middleSet.add(splits[k]);
            }
        }

        List<String> middleList = new ArrayList(middleSet);
        for (int i = middleList.size() - 1; i >= 0; i--) {
            if (start.indexOf(middleList.get(i)) > 0 || end.indexOf(middleList.get(i)) > 0) {
                middleList.remove(i);
            }
        }

        if (!start.isEmpty() && !end.isEmpty() && middleList.isEmpty()) {
            if (start.equals(end)) {
                return start;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start);
        for (String middle : middleList) {
            stringBuilder.append(middle);
            if (stringBuilder.length() > MAX_LENGTH) {
                return "*";
            }
        }
        stringBuilder.append(end);
        String result = stringBuilder.toString();
        if (result.length() > MAX_LENGTH) {
            return "*";
        }
        return result;
    }
}
