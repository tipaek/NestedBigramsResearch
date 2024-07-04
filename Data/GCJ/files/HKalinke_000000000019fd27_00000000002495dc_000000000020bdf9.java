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

        class Person
        {
            String name;
            Map<Integer, Activity> activities = new HashMap<>();

            Person(String name)
            {
                this.name = name;
            }

            boolean addIfFree(Activity a)
            {
                for (Activity act: activities.values()) {
                    if(!(a.end <= act.start || a.start >= act.end)) return false;
                }
                activities.put(a.id, a);
                return true;
            }

            String getSchedule(int maxTasks)
            {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < maxTasks; i++)
                {
                    sb.append(activities.getOrDefault(i, null) == null ? "?" : name);
                }

                return sb.toString();
            }
        }


        Scanner scanner = new Scanner(System.in);
        int testCases = Integer.parseInt(scanner.nextLine());
        Data[] results = new Data[testCases];

        for(int i = 1; i <= testCases; i++) {
            int N = Integer.parseInt(scanner.nextLine());
            List<Activity> activities = new ArrayList<>();
            Person C = new Person("C");
            Person J = new Person("J");
            boolean impossible = false;

            for(int j = 0; j < N; j++)
            {
                Integer[] row = Arrays.stream(scanner.nextLine().split("\\s+")).map(Integer::parseInt)
                        .toArray(Integer[]::new);
                Activity a = new Activity(j, row[0], row[1]);

                if(!C.addIfFree(a))
                {
                    if(!J.addIfFree(a))
                    {
                        results[i - 1] = new Data("IMPOSSIBLE");
                        impossible = true;
                        break;
                    }
                }
            }
            if(!impossible) {

                String tmp = C.getSchedule(N);
                results[i - 1] = new Data(tmp.replace('?', 'J'));
            }
        }

        for(int i = 1; i <= testCases; i++) {
            System.out.println(results[i-1].out(i));
        }
    }
}
