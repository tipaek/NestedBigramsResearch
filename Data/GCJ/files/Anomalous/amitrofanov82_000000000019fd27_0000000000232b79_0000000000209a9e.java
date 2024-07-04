import java.io.FileInputStream;
import java.util.Scanner;

public class Solution {
    
    static Scanner sc = null;
    
    public static void main(String[] args) throws Exception {
        initializeScanner();
        
        String[] inputLine = sc.nextLine().split(" ");
        int testCases = Integer.parseInt(inputLine[0]);
        int B = Integer.parseInt(inputLine[1]);
        
        for (int i = 0; i < testCases; i++) {
            processTestCase(B);
            System.out.flush();
            String response = sc.nextLine();
            if ("N".equals(response)) {
                break;
            }
        }
        sc.close();
    }

    private static void initializeScanner() throws Exception {
        if ("Alexey".equals(System.getProperty("user.name"))) {
            sc = new Scanner(new FileInputStream("input.txt"));
            System.err.println("Development mode, reading from file");
        } else {
            sc = new Scanner(System.in);
        }
    }

    private static void processTestCase(int B) {
        boolean[] bitSet = new boolean[B];
        int bitCounter = 0;

        for (int i = 1; i <= 10; i++) {
            if (bitCounter == B) {
                getBit(0);
                continue;
            }
            if (i % 2 == 1) {
                bitSet[bitCounter / 2] = getBit(bitCounter / 2);
            } else {
                bitSet[B - 1 - (bitCounter / 2)] = getBit(B - 1 - (bitCounter / 2)];
            }
            bitCounter++;
        }

        if (bitCounter == B) {
            printBitSet(bitSet);
            return;
        }

        for (int i = 1; i <= 14; i++) {
            handleBitSetChanges(bitSet, bitCounter);
            for (int j = 1; j <= 8; j++) {
                if (bitCounter == B) {
                    getBit(0);
                    continue;
                }
                if (j % 2 == 1) {
                    bitSet[bitCounter / 2] = getBit(bitCounter / 2);
                } else {
                    bitSet[B - 1 - (bitCounter / 2)] = getBit(B - 1 - (bitCounter / 2)];
                }
                bitCounter++;
            }
        }

        printBitSet(bitSet);
    }

    private static void handleBitSetChanges(boolean[] bitSet, int bitCounter) {
        if (onlyEqualBitsLeftAndRight(bitSet, bitCounter)) {
            if (bitSet[0] != getBit(0)) {
                complement(bitSet);
                getBit(0);
            } else {
                getBit(0);
            }
        } else if (onlyDifferentBitsLeftAndRight(bitSet, bitCounter)) {
            if (bitSet[0] == getBit(0)) {
                getBit(0);
            } else {
                complement(bitSet);
                getBit(0);
            }
        } else {
            int equalBitIndex = findEqualBit(bitSet, bitCounter);
            int differentBitIndex = findDifferentBit(bitSet, bitCounter);
            boolean newEqualValue = getBit(equalBitIndex);
            boolean newDifferentValue = getBit(differentBitIndex);

            if (newEqualValue == bitSet[equalBitIndex]) {
                if (newDifferentValue != bitSet[differentBitIndex]) {
                    swap(bitSet);
                }
            } else {
                if (newDifferentValue != bitSet[differentBitIndex]) {
                    complement(bitSet);
                } else {
                    swap(bitSet);
                    complement(bitSet);
                }
            }
        }
    }

    private static void printBitSet(boolean[] bitSet) {
        StringBuilder result = new StringBuilder();
        for (boolean bit : bitSet) {
            result.append(bit ? 1 : 0);
        }
        System.out.println(result);
    }

    private static boolean getBit(int index) {
        System.out.println(index + 1);
        System.out.flush();
        return sc.nextByte() == 1;
    }

    private static void swap(boolean[] bitSet) {
        for (int i = 0; i < bitSet.length / 2; i++) {
            boolean temp = bitSet[i];
            bitSet[i] = bitSet[bitSet.length - 1 - i];
            bitSet[bitSet.length - 1 - i] = temp;
        }
    }

    private static int findDifferentBit(boolean[] bitSet, int bitCounter) {
        for (int i = 0; i < bitCounter / 2; i++) {
            if (bitSet[i] != bitSet[bitSet.length - 1 - i]) {
                return i;
            }
        }
        return -1;
    }

    private static int findEqualBit(boolean[] bitSet, int bitCounter) {
        for (int i = 0; i < bitCounter / 2; i++) {
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

    private static boolean onlyDifferentBitsLeftAndRight(boolean[] bitSet, int bitCounter) {
        for (int i = 0; i < bitCounter / 2; i++) {
            if (bitSet[i] == bitSet[bitSet.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean onlyEqualBitsLeftAndRight(boolean[] bitSet, int bitCounter) {
        for (int i = 0; i < bitCounter / 2; i++) {
            if (bitSet[i] != bitSet[bitSet.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }
}