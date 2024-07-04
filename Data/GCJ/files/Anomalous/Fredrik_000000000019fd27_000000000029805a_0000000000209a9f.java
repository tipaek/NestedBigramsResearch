import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        for (int i = 1; i <= testCases; i++) {
            String line = scanner.nextLine();
            String result = processLine(line);
            printSolution(i, result);
        }
    }

    private static String processLine(String line) {
        StringBuilder sb = new StringBuilder();
        int openBrackets = 0;
        int previousNum = -1;

        for (char ch : line.toCharArray()) {
            int currentNum = Character.getNumericValue(ch);

            if (currentNum == previousNum) {
                sb.append(currentNum);
                continue;
            }

            while (openBrackets < currentNum) {
                sb.append("(");
                openBrackets++;
            }

            while (openBrackets > currentNum) {
                sb.append(")");
                openBrackets--;
            }

            sb.append(currentNum);
            previousNum = currentNum;
        }

        while (openBrackets > 0) {
            sb.append(")");
            openBrackets--;
        }

        return sb.toString();
    }

    private static void printSolution(int caseNumber, String result) {
        System.out.println("Case #""+ caseNumber + ": " + result);
    }
}