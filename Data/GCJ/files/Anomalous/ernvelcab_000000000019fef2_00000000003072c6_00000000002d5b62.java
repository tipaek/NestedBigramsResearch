import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            int numberOfCases = Integer.parseInt(bufferedReader.readLine());

            for (int c = 1; c <= numberOfCases; c++) {
                String[] parts = bufferedReader.readLine().split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                boolean flippedX = x < 0;
                boolean flippedY = y < 0;
                x = Math.abs(x);
                y = Math.abs(y);

                int current = 1;
                StringBuilder verticalSteps = new StringBuilder();
                StringBuilder horizontalSteps = new StringBuilder();

                if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) {
                    while ((x != 0 || y != 0) && (x == 0 || Math.abs(x) >= current) && (y == 0 || Math.abs(y) >= current)) {
                        int option;

                        if (y % 2 == 1) {
                            option = (x == 0 || (y - current != x && y - current != 0)) ? 1 : 0;
                        } else if (x % 2 == 1) {
                            option = (y == 0 || (x - current != y && x - current != 0)) ? 3 : 2;
                        } else {
                            if ((x != 0 && Math.abs(x) < current) || (y != 0 && Math.abs(y) < current)) {
                                break;
                            }

                            if (x == 0) {
                                option = (y < 0) ? 0 : 1;
                            } else if (y == 0) {
                                option = (x < 0) ? 2 : 3;
                            } else {
                                option = (x < y) ? (x < 0 ? 2 : 3) : (y < 0 ? 0 : 1);
                            }
                        }

                        switch (option) {
                            case 0 -> {
                                verticalSteps.append("N");
                                horizontalSteps.append(" ");
                                y += current;
                            }
                            case 1 -> {
                                verticalSteps.append("S");
                                horizontalSteps.append(" ");
                                y -= current;
                            }
                            case 2 -> {
                                verticalSteps.append(" ");
                                horizontalSteps.append("E");
                                x += current;
                            }
                            default -> {
                                verticalSteps.append(" ");
                                horizontalSteps.append("W");
                                x -= current;
                            }
                        }

                        current *= 2;
                    }
                }

                String result = "IMPOSSIBLE";
                if (x == 0 && y == 0) {
                    result = buildResult(verticalSteps, horizontalSteps, flippedX, flippedY);
                }
                System.out.println("Case #" + c + ": " + result);
            }
        }
    }

    private static String buildResult(StringBuilder verticalSteps, StringBuilder horizontalSteps, boolean flippedX, boolean flippedY) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < verticalSteps.length(); i++) {
            char verticalStep = verticalSteps.charAt(i);
            char horizontalStep = horizontalSteps.charAt(i);
            result.append(verticalStep == ' ' ? horizontalStep : verticalStep);
        }
        if (!flippedX) {
            replaceAll(result, "E", "T", "W", "E", "T", "W");
        }
        if (!flippedY) {
            replaceAll(result, "N", "T", "S", "N", "T", "S");
        }
        return result.toString();
    }

    private static void replaceAll(StringBuilder sb, String... replacements) {
        for (int i = 0; i < replacements.length; i += 3) {
            String target = replacements[i];
            String temp = replacements[i + 1];
            String replacement = replacements[i + 2];
            int index;
            while ((index = sb.indexOf(target)) != -1) {
                sb.replace(index, index + target.length(), temp);
            }
            while ((index = sb.indexOf(temp)) != -1) {
                sb.replace(index, index + temp.length(), replacement);
            }
        }
    }
}