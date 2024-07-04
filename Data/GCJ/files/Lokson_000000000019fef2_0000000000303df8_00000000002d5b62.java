public class Solution {


    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCases = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.

        for (int caseNumber = 1; caseNumber <= testCases; ++caseNumber) {
            int x = in.nextInt();
            int y = in.nextInt();
            if(x%2 == 0 && y%2 ==0){
                System.out.println(String.format("Case #%d: IMPOSSIBLE", caseNumber));
            }
            System.out.println(x + " " + y);
        }
}