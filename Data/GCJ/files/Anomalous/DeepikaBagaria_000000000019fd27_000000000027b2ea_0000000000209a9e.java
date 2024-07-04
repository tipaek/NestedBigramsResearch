import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int t = input.nextInt();
        int n = input.nextInt();

        if (n == 10) {
            for (int tt = 0; tt < t; tt++) {
                int[] b = new int[n];
                for (int i = 0; i < n; i++) {
                    System.out.println(i + 1);
                    b[i] = input.nextInt();
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    sb.append(b[i]);
                }
                System.out.println(sb.toString());
                if (input.next().equals("N")) {
                    input.close();
                    return;
                }
            }
        }
        input.close();
    }

    public int[] reverse(int[] a) {
        Integer[] boxedArray = Arrays.stream(a).boxed().toArray(Integer[]::new);
        Collections.reverse(Arrays.asList(boxedArray));
        return Arrays.stream(boxedArray).mapToInt(Integer::intValue).toArray();
    }

    public int[] compliment(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] == 0 ? 1 : 0;
        }
        return a;
    }
}