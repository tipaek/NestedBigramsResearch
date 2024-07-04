import java.io.FileInputStream;
import java.util.Scanner;

public class Solution {

    private static Scanner sc;

    public static void main(String[] args) throws Exception {
        initializeScanner();
        
        String[] inputLine = sc.nextLine().split(" ");
        int testCases = Integer.parseInt(inputLine[0]);
        int B = Integer.parseInt(inputLine[1]);
        
        for (int i = 0; i < testCases; i++) {
            solveSingleCase(B);
            System.out.flush();
            String response = sc.nextLine();
            if (response.equals("N")) break;
        }
        
        sc.close();
    }

    private static void initializeScanner() throws Exception {
        if (System.getProperty("user.name").equals("Alexey")) {
            sc = new Scanner(new FileInputStream("input.txt"));
            System.err.println("Development mode: reading from file");
        } else {
            sc = new Scanner(System.in);
        }
    }

    private static void solveSingleCase(int B) {
        boolean[] bitSet = new boolean[B];
        int bCounter = 0;

        for (int i = 0; i < 10; i++) {
            if (bCounter == B) {
                getBit(0);
                continue;
            }
            if (i % 2 == 0) {
                bitSet[bCounter / 2] = getBit(bCounter / 2);
            } else {
                bitSet[B - 1 - (bCounter / 2)] = getBit(B - 1 - (bCounter / 2)];
            }
            bCounter++;
        }

        for (int i = 0; i < 14; i++) {
            handleBitSetTransformation(bitSet, bCounter);

            for (int j = 0; j < 8; j++) {
                if (bCounter == B) {
                    getBit(0);
                    continue;
                }
                if (j % 2 == 0) {
                    bitSet[bCounter / 2] = getBit(bCounter / 2);
                } else {
                    bitSet[B - 1 - (bCounter / 2)] = getBit(B - 1 - (bCounter / 2)];
                }
                bCounter++;
            }
        }

        printResult(bitSet);
    }

    private static void handleBitSetTransformation(boolean[] bitSet, int bCounter) {
        if (onlyEqualBits(bitSet, bCounter)) {
            if (bitSet[0] != getBit(0)) {
                complement(bitSet);
                getBit(0);
            } else {
                getBit(0);
            }
        } else if (onlyDifferentBits(bitSet, bCounter)) {
            if (bitSet[0] == getBit(0)) {
                getBit(0);
            } else {
                complement(bitSet);
                getBit(0);
            }
        } else {
            int eqBit = findEqualBit(bitSet, bCounter);
            int diffBit = findDifferentBit(bitSet, bCounter);
            boolean newEqVal = getBit(eqBit);
            boolean newDiffVal = getBit(diffBit);

            if (newEqVal == bitSet[eqBit]) {
                if (newDiffVal != bitSet[diffBit]) {
                    swap(bitSet);
                }
            } else {
                if (newDiffVal != bitSet[diffBit]) {
                    complement(bitSet);
                } else {
                    swap(bitSet);
                    complement(bitSet);
                }
            }
        }
    }

    private static void swap(boolean[] bitSet) {
        for (int i = 0; i < bitSet.length / 2; i++) {
            boolean temp = bitSet[i];
            bitSet[i] = bitSet[bitSet.length - 1 - i];
            bitSet[bitSet.length - 1 - i] = temp;
        }
    }

    private static int findDifferentBit(boolean[] bitSet, int bCounter) {
        for (int i = 0; i < bCounter / 2; i++) {
            if (bitSet[i] != bitSet[bitSet.length - 1 - i]) {
                return i;
            }
        }
        return -1;
    }

    private static int findEqualBit(boolean[] bitSet, int bCounter) {
        for (int i = 0; i < bCounter / 2; i++) {
            if (bitSet[i] == bitSet[bitSet.length - 1 - i]) {
                return i;
            }
        }
        return -1;
    }

    private static void complement(boolean[] bitSet) {
        for (int i = 0; i < bitSet.length; i++) {
            bitSet[i] = !bitSet[i];
        }
    }

    private static boolean onlyDifferentBits(boolean[] bitSet, int bCounter) {
        for (int i = 0; i < bCounter / 2; i++) {
            if (bitSet[i] == bitSet[bitSet.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean onlyEqualBits(boolean[] bitSet, int bCounter) {
        for (int i = 0; i < bCounter / 2; i++) {
            if (bitSet[i] != bitSet[bitSet.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean getBit(int i) {
        System.out.println(i + 1);
        System.out.flush();
        return sc.nextLine().equals("1");
    }

    private static void printResult(boolean[] bitSet) {
        StringBuilder result = new StringBuilder();
        for (boolean bit : bitSet) {
            result.append(bit ? 1 : 0);
        }
        System.out.println(result);
    }
}