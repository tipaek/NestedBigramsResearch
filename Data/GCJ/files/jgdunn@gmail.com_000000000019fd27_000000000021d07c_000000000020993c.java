import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Codejam 2020 -- Vestigium
 *
 */
public class Solution
{
    public static void main( String[] args ) throws Exception
    {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        //Scanner in = new Scanner(new File("src/main/resources/vestigium.txt"));
        int numberOfTests = in.nextInt();
        String emptyToIgnore = in.nextLine();
        List<String> content = new ArrayList<>();

        while (in.hasNext()) {
            String inputString = in.nextLine();
            content.add(inputString);
        }

        List<List<String>> tests = generateTestCases(content);
        int testCaseNumber = 1;
        for (List<String> test: tests) {
            analyzeTest(testCaseNumber++, test);
        }

    }
    private static List<List<String>> generateTestCases(List<String> testCases) {
        int i = 0;
        List<List<String>> matrices = new ArrayList<>();

        do {
            int sizeOfMatrix = Integer.parseInt(testCases.get(i));
            //System.out.println("sizeOfMatrix = " + sizeOfMatrix);
            List<String> testMatrix = testCases.subList(++i, i + sizeOfMatrix);
            //System.out.println("testMatrix.size() = " + testMatrix.size());
            i += sizeOfMatrix;
            matrices.add(testMatrix);
        } while (i < testCases.size());

        return matrices;
        //System.out.println("matrices.size() = " + matrices.size());

    }

    private static void analyzeTest(Integer testCaseNumber, List<String> test) {
        System.out.print("Case #" + testCaseNumber + ": " );
        BigInteger trace = calculateTrace(test);
        System.out.print("" + trace + " ");
        Integer duplicateRows = calculateDuplicateRows(test);
        System.out.print(duplicateRows + " " );
        Integer duplicateColumns = calculateDuplicateColumns(test);
        System.out.println(duplicateColumns);

    }

    private static String[][] generate2DMatrix(List<String> rows) {
        String[][] matrix = new String[rows.size()][rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            String[] numbersInRow = rows.get(i).split(" ");
            matrix[i] = numbersInRow;
        }
        return matrix;
    }

    private static Integer calculateDuplicateColumns(List<String> rows) {

        String[][] matrix = generate2DMatrix(rows);

        int duplicates = 0;

        for (int i = 0; i < rows.size(); i++) {
            String[] column = getColumn(i, matrix);

            List<String> numbersList = Arrays.asList(column);
            HashSet<String> numbersSet = new HashSet<>();
            numbersSet.addAll(numbersList);

            if (numbersList.size() != numbersSet.size()) {
                duplicates++;
            }
        }

        return duplicates;
    }

    private static String[] getColumn(int address, String[][] from) {
        return (Arrays.stream(from).map(x -> x[address]).toArray(String[]::new));
    }

    private static Integer calculateDuplicateRows(List<String> rows) {
        int duplicates = 0;
        for (int i = 0; i < rows.size(); i++) {
            String[] numbersInRow = rows.get(i).split(" ");
            List<String> numbersList = Arrays.asList(numbersInRow);
            HashSet<String> numbersSet = new HashSet<>();
            numbersSet.addAll(numbersList);
            if (numbersList.size() != numbersSet.size()) {
                duplicates++;
            }
        }
        return duplicates;
    }

    private static BigInteger calculateTrace(List<String> rows) {
        BigInteger trace = BigInteger.ZERO;
        for (int i = 0; i < rows.size(); i++) {
            String[] numbersInRow = rows.get(i).split(" ");
            trace = trace.add(new BigInteger(numbersInRow[i]));
        }
        return trace;
    }
    

}
