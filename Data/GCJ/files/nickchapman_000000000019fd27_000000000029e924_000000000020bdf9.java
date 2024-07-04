import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int noOfScenarios = Integer.parseInt(reader.readLine());
        String[] output = new String[noOfScenarios];

        for (int i = 0; i < noOfScenarios; i++) {
            int noOfActivities = Integer.parseInt(reader.readLine());

            String[][] times = parseToArray(noOfActivities);

            output[i] = getOrder(times);
        }

        for (int i=1; i<=output.length; i++) {
            System.out.println("Case #" + i + ": " + output[i-1]);
        }
    }

    private static String[][] orderTimes(String[][] arr)
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            String[] key = arr[i];
            int j = i - 1;

            while (j >= 0 && Integer.valueOf(arr[j][0]) > Integer.valueOf(key[0])) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }

        return arr;
    }

    private static String[][] parseToArray(int noOfActivities) throws IOException {
        String[][] array = new String[noOfActivities][2];

        for (int i=0; i<noOfActivities; i++) {
            array[i] = stringIntoArray(reader.readLine());
        }

        return array;
    }

    private static String[] stringIntoArray(String line) {
        return line.split(" ");
    }

    private static String getOrder(String[][] times) {
        boolean isChanged = false;

        String[][] copy = Arrays.stream(times).map(String[]::clone).toArray(String[][]::new);
        String originalOrder = "";

        if (!(Arrays.deepToString(times).equals(Arrays.deepToString(orderTimes(times))))) {
            isChanged = true;
            times = orderTimes(times);

            for (int i=0; i<copy.length; i++) {
                for (int j=0; j<copy.length; j++) {
                    if(Arrays.deepEquals(copy[j],times[i])) {
                        originalOrder += j;
                    }
                }
            }
        }

        String finalOrder = "";

        String[] cameron = times[0];
        finalOrder += "C";
        String[] jamie = null;

        for (int i=1; i<times.length; i++) {
            if (Integer.valueOf(times[i][0]) < Integer.valueOf(cameron[1])) {
                if (jamie != null && Integer.valueOf(times[i][0]) < Integer.valueOf(jamie[1])) {
                    return "IMPOSSIBLE";
                }
                jamie = times[i];
                finalOrder += "J";
            } else {
                cameron = times[i];
                finalOrder += "C";
            }
        }

        if (isChanged) {
            String result = "";
            for (int i=0; i<originalOrder.length(); i++) {
                result += finalOrder.charAt(Integer.parseInt(String.valueOf(originalOrder.charAt(i))));
            }
            finalOrder = result;
        }

        return finalOrder;
    }
}