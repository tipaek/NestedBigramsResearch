import java.util.*;

public class Solution {
    public static void main(String[] args) {
         class Data {
            String S;

            Data(String S) {
                this.S = S;
            }

            String out(int tc) {
                return "Case #" + tc + ": " + S;
            }
        }

        class Activity
        {
            int id;
            Integer start;
            Integer end;

            Activity(int id, int start, int end)
            {
                this.id = id;
                this.start = start;
                this.end = end;
            }
        }


        Scanner scanner = new Scanner(System.in);
        int testCases = Integer.parseInt(scanner.nextLine());
        Data[] results = new Data[testCases];

        for(int i = 1; i <= testCases; i++) {
            int N = Integer.parseInt(scanner.nextLine());
            String[] out = new String[N];
            List<Activity> activities = new ArrayList<>();
            Activity j = null;
            Activity c = null;
            boolean impossible = false;

            for(int x = 0; x < N; x++)
            {
                Integer[] row = Arrays.stream(scanner.nextLine().split("\\s+")).map(Integer::parseInt)
                        .toArray(Integer[]::new);
                activities.add(new Activity(x, row[0], row[1]));
            }
            activities.sort(Comparator.comparingInt(value -> value.start));

            for (Activity a: activities) {
                if(c != null)
                {
                    if(c.end <= a.start) c = null;
                }
                if(j != null)
                {
                    if(j.end <= a.start) j = null;
                }

                if(c == null){
                    c = a;
                    out[a.id] = "C";
                }
                else if(j == null) {
                    j = a;
                    out[a.id] = "J";
                }
                else {
                    impossible = true;
                    break;
                }
            }

            if(impossible) results[i-1] = new Data("IMPOSSIBLE");
            else results[i-1] = new Data(String.join("", Arrays.asList(out)));
        }

        for(int i = 1; i <= testCases; i++) {
            System.out.println(results[i-1].out(i));
        }
    }
}
