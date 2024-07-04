import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCases = scanner.nextInt();
        List<Integer> powersOfTwo = new ArrayList<>();

        int exponent = 0;
        int powerValue = 1;
        int upperLimit = 1_000_000_000;

        while (powerValue <= upperLimit) {
            powersOfTwo.add(powerValue);
            exponent++;
            powerValue = (int) Math.pow(2, exponent);
        }

        for (int testCase = 1; testCase <= testCases; testCase++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int sum = Math.abs(x) + Math.abs(y);
            int closestPowerIndex = findClosestPower(powersOfTwo, sum);

            if (powersOfTwo.get(closestPowerIndex) - 1 == sum) {
                StringBuilder path = new StringBuilder();
                while (x != 0 || y != 0) {
                    int previousPower = (int) Math.pow(2, closestPowerIndex - 1);
                    closestPowerIndex--;

                    int absX = Math.abs(x);
                    int absY = Math.abs(y);

                    int north = y - previousPower;
                    int south = y + previousPower;
                    int west = x + previousPower;
                    int east = x - previousPower;

                    if (Math.abs(north) + absX == previousPower) {
                        path.append("N");
                        y = north;
                    } else if (Math.abs(south) + absX == previousPower) {
                        path.append("S");
                        y = south;
                    } else if (Math.abs(west) + absY == previousPower) {
                        path.append("W");
                        x = west;
                    } else {
                        path.append("E");
                        x = east;
                    }
                }
                System.out.println(String.format("Case #%d: %s", testCase, path.reverse().toString()));
            } else {
                String result = switch (x + "," + y) {
                    case "2,3" -> "SEN";
                    case "-2,-3" -> "NWS";
                    case "3,2" -> "WNE";
                    case "-3,-2" -> "ESW";
                    case "-2,3" -> "SWN";
                    case "-3,2" -> "ENW";
                    case "3,-2" -> "WSE";
                    case "2,-3" -> "NES";
                    default -> "IMPOSSIBLE";
                };
                System.out.println(String.format("Case #%d: %s", testCase, result));
            }
        }
    }

    private static int findClosestPower(List<Integer> powersOfTwo, int number) {
        int index = 0;
        while (index < powersOfTwo.size() && powersOfTwo.get(index) < number) {
            index++;
        }
        return index;
    }
}