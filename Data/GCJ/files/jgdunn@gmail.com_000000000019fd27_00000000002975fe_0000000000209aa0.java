import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Codejam 2020 -- Indicium
 *
 */
public class Solution
{
    static Integer[][] Order2MatrixTrace2 = {{1,2},{2,1}};
    static Integer[][] Order2MatrixTrace4 = {{2,1},{1,2}};

    static Integer[][] Order3MatrixTrace3 = {{1,2,3},{3,1,2},{2,3,1}};
    static Integer[][] Order3MatrixTrace6 = {{1,2,3},{2,3,1},{3,1,2}};
    static Integer[][] Order3MatrixTrace9 = {{3,2,1},{1,3,2},{2,1,3}};

    static Integer[][] Order4MatrixTrace4 = {{1,2,3,4},{2,1,4,3},{3,4,1,2},{4,3,2,1}};
    static Integer[][] Order4MatrixTrace8 = {{2,3,4,1},{3,2,1,4},{4,1,2,3},{1,4,3,2}};
    static Integer[][] Order4MatrixTrace10 = {{1,4,2,3},{3,2,4,1},{4,1,3,2},{2,3,1,4}};
    static Integer[][] Order4MatrixTrace12 = {{3,1,2,4},{4,3,1,2},{2,4,3,1},{1,2,4,3}};
    static Integer[][] Order4MatrixTrace16 = {{4,2,3,1},{2,4,1,3},{3,1,4,2},{1,3,2,4}};

    static Integer[][] Order5MatrixTrace5 = {{1,2,3,4,5},{5,1,2,3,4},{4,5,1,2,3},{3,4,5,1,2},{2,3,4,5,1}};
    static Integer[][] Order5MatrixTrace10 = {{2,5,1,3,4},{1,2,3,4,5},{3,4,2,5,1},{4,1,5,2,3},{5,3,4,1,2}};
    static Integer[][] Order5MatrixTrace15 = {{3,5,1,2,4},{1,3,2,4,5},{2,4,3,5,1},{4,1,5,3,2},{5,2,4,1,3}};
    static Integer[][] Order5MatrixTrace20 = {{4,5,1,2,3},{1,4,2,3,5},{2,3,4,5,1},{3,1,5,4,2},{5,2,3,1,4}};
    static Integer[][] Order5MatrixTrace25 = {{5,2,3,4,1},{1,5,2,3,4},{4,1,5,2,3},{3,4,1,5,2},{2,3,4,1,5}};

    public static void main( String[] args )
    {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        /*
        Scanner in = null;
        try {
            in = new Scanner(new File("src/main/resources/indicium.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
*/
        List<String> tests = new ArrayList<>();
        int numberOfTests = in.nextInt();
        String emptyToIgnore = in.nextLine();

        while (in.hasNext()) {
            String inputString = in.nextLine();
            tests.add(inputString);
        }

        int testCaseNumber = 1;
        for (String test: tests) {
            analyzeTest(testCaseNumber++, test);
        }
    }

    private static void analyzeTest(Integer testCaseNumber, String test) {
        System.out.print("Case #" + testCaseNumber + ": " );
        String[] testInputs = test.split(" ");
        boolean possible = calculateTraceWithinPossibleBounds(Integer.parseInt(testInputs[0]), Integer.parseInt(testInputs[1]));
        if (!possible) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        Integer[][] solution = calculateMatrixOfDesiredOrderAndTrace(Integer.parseInt(testInputs[0]), Integer.parseInt(testInputs[1]));
        if (solution == null) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        Integer duplicates = calculateDuplicateColumns(solution);
        if (duplicates > 0) {
            throw new IllegalArgumentException();
        }
        duplicates = calculateDuplicateRows(solution);
        if (duplicates > 0) {
            throw new IllegalArgumentException();
        }
        System.out.println("POSSIBLE");
        for (Integer[] row : solution) {
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println("");
        }
    }

    private static boolean calculateTraceWithinPossibleBounds(Integer order, Integer trace) {
        if (trace < order) {
            return false;
        } else if (trace > (order * order)) {
            return false;
        } return isTraceSupportedMultiple(order, trace);
    }

    private static boolean isTraceSupportedMultiple(Integer order, Integer trace) {
        for (int i = 1; i <= order; i++) {
            if (trace == order * i) {
                return true;
            }
        }
        if (order == 4 && trace == 10) {
            return true;
        }
        if (order == 5 && trace == 15) {
            return true;
        }
        return false;
    }

    private static Integer[][] calculateMatrixOfDesiredOrderAndTrace(int order, int desiredTrace) {
        if (order == 2 && desiredTrace == 2) {
            return Order2MatrixTrace2;
        } else if (order == 2 && desiredTrace == 4) {
            return Order2MatrixTrace4;
        } else if (order == 3 && desiredTrace == 3) {
            return Order3MatrixTrace3;
        } else if (order == 3 && desiredTrace == 6) {
            return Order3MatrixTrace6;
        } else if (order == 3 && desiredTrace == 9) {
            return Order3MatrixTrace9;
        } else if (order == 4 && desiredTrace == 4) {
            return Order4MatrixTrace4;
        } else if (order == 4 && desiredTrace == 8) {
            return Order4MatrixTrace8;
        } else if (order == 4 && desiredTrace == 10) {
            return Order4MatrixTrace10;
        } else if (order == 4 && desiredTrace == 12) {
            return Order4MatrixTrace12;
        } else if (order == 4 && desiredTrace == 16) {
            return Order4MatrixTrace16;
        }  else if (order == 5 && desiredTrace == 5) {
            return Order5MatrixTrace5;
        }  else if (order == 5 && desiredTrace == 10) {
            return Order5MatrixTrace10;
        } else if (order == 5 && desiredTrace == 15) {
            return Order5MatrixTrace15;
        } else if (order == 5 && desiredTrace == 20) {
            return Order5MatrixTrace20;
        } else if (order == 5 && desiredTrace == 25) {
            return Order5MatrixTrace25;
        }
        return null;
    }

    private static String[][] generate2DMatrix(List<String> rows) {
        String[][] matrix = new String[rows.size()][rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            String[] numbersInRow = rows.get(i).split(" ");
            matrix[i] = numbersInRow;
        }
        return matrix;
    }

    private static Integer calculateDuplicateColumns(Integer[][] matrix) {

        int duplicates = 0;

        for (int i = 0; i < matrix[0].length; i++) {
            Integer[] column = getColumn(i, matrix);

            List<Integer> numbersList = Arrays.asList(column);
            HashSet<Integer> numbersSet = new HashSet<>();
            numbersSet.addAll(numbersList);

            if (numbersList.size() != numbersSet.size()) {
                duplicates++;
            }
        }

        return duplicates;
    }

    private static Integer[] getColumn(int address, Integer[][] from) {
        return (Arrays.stream(from).map(x -> x[address]).toArray(Integer[]::new));
    }

    private static Integer calculateDuplicateRows(Integer[][] rows) {
        int duplicates = 0;
        for (int i = 0; i < rows.length; i++) {
            Integer[] numbersInRow = rows[i];
            List<Integer> numbersList = Arrays.asList(numbersInRow);
            HashSet<Integer> numbersSet = new HashSet<>();
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

    private static List<Integer[]> generatePotentialTrace(int order, int targetTrace) {
        ArrayList<Integer[]> listOfPotentialTraces = new ArrayList<>();


        if (targetTrace > order * order || targetTrace < order) {
            return null;
        }

        for (int i = 1; i <= order; i++) {
            Integer[] potentialTrace = generateNewInitializedTrace(order);
            for (int j = 0; j < potentialTrace.length; j++) {
                for (int k = 1 ; k <= order; k++) {
                    potentialTrace[j] = k;
                    if (isEqualToTarget(potentialTrace, targetTrace)) {
                        listOfPotentialTraces.add(potentialTrace);
                    }
                }
            }
        }

        return listOfPotentialTraces;
    }

    private static boolean isEqualToTarget(Integer[] potentialTrace, int targetTrace) {
        long sum = 0;
        //add up contents of trace and see if it equals the target
        for (int i = 0; i < potentialTrace.length; i++) {
            sum += potentialTrace[i];
        }
        return (sum == targetTrace);
    }

    private static Integer[] generateNewInitializedTrace(int order) {
        Integer[] toReturn = new Integer[order];
        //initialize whole array to 1;
        for (int i = 0; i < order; i++) {
            toReturn[i] = 1;
        }
        return toReturn;
    }

}
