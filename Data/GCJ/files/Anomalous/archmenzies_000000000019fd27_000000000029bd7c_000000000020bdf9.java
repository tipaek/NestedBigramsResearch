import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Solution {

    private static Integer[] getSortedIndexes(int[] startTimes) {
        Integer[] indexes = IntStream.range(0, startTimes.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indexes, Comparator.comparingInt(i -> startTimes[i]));
        return indexes;
    }

    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            int testCases = Integer.parseInt(inputReader.readLine());

            for (int t = 1; t <= testCases; t++) {
                int N = Integer.parseInt(inputReader.readLine());
                int[] startTimes = new int[N];
                int[] endTimes = new int[N];

                for (int n = 0; n < N; n++) {
                    String[] times = inputReader.readLine().split(" ");
                    startTimes[n] = Integer.parseInt(times[0]);
                    endTimes[n] = Integer.parseInt(times[1]);
                }

                Integer[] sortedIndexes = getSortedIndexes(startTimes);
                byte[] output = new byte[N];
                int camFree = 0;
                int jamieFree = 0;
                boolean possible = true;

                for (int i = 0; i < N; i++) {
                    int index = sortedIndexes[i];
                    if (camFree <= startTimes[index]) {
                        camFree = endTimes[index];
                        output[index] = 'C';
                    } else if (jamieFree <= startTimes[index]) {
                        jamieFree = endTimes[index];
                        output[index] = 'J';
                    } else {
                        possible = false;
                        break;
                    }
                }

                if (!possible) {
                    System.out.printf("Case #%d: IMPOSSIBLE%n", t);
                } else {
                    System.out.printf("Case #%d: %s%n", t, new String(output));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}