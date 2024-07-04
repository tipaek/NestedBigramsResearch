import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        int b = scanner.nextInt();
        int size = (b + 1) / 2;
        StringBuilder sb = new StringBuilder(" ".repeat(b));
        int query = 0;

        for (int i = 0; i < t; i++) {
            Pair[] pairsArray = new Pair[size];
            System.out.println(1);
            scanner.nextInt();
            query++;
            System.out.println(1);
            scanner.nextInt();
            query++;
            Queue<Integer> myQueue = new LinkedList<>();
            Pair pairSame = null;
            Pair pairDiff = null;
            int j = 0;

            while (j < size) {
                if (query % 10 != 0 || j == 0) {
                    int first = j + 1;
                    if (first == size && b % 2 == 1) {
                        System.out.println(first);
                        int bite = scanner.nextInt();
                        pairsArray[j] = new Pair(first, false, bite);
                        query++;
                        j++;
                        continue;
                    }
                    System.out.println(first);
                    int bite = scanner.nextInt();
                    query++;
                    int end = b - j;
                    System.out.println(end);
                    int bite2 = scanner.nextInt();
                    query++;
                    boolean isDiff = bite != bite2;

                    if (isDiff) {
                        pairSame = new Pair(first, isDiff, bite);
                    } else {
                        pairDiff = new Pair(first, isDiff, bite);
                    }

                    pairsArray[j] = new Pair(first, isDiff, bite);
                    j++;
                } else {
                    System.out.println(pairSame.getIndex());
                    int value1 = scanner.nextInt();
                    System.out.println(pairDiff.getIndex());
                    int value2 = scanner.nextInt();
                    int lastOp = whichChange(pairSame, pairDiff, value1, value2);
                    query += 2;
                    myQueue.add(lastOp);
                }
            }

            int iteration = 1;
            while (!myQueue.isEmpty()) {
                int operation = myQueue.poll();
                int cur = iteration * 8;

                while (cur < size) {
                    switch (operation) {
                        case 1 -> pairsArray[cur].inverse();
                        case 2, 3 -> pairsArray[cur].inverseBit();
                    }
                    cur++;
                }
            }

            String r = "";
            int operation = 4;

            while (r.equals("N")) {
                Pair[] pairsArrayTemp = pairsArray;
                int cur = 0;

                while (cur < size) {
                    switch (operation) {
                        case 1 -> pairsArrayTemp[cur].inverseFinalValue();
                        case 2 -> pairsArrayTemp[cur].inverseBitFinalValue();
                        case 3 -> pairsArrayTemp[cur].inverse2FinalValue();
                        default -> {}
                    }
                    sb.setCharAt(cur, Character.forDigit(pairsArrayTemp[cur].getFinalValue(), 10));
                    sb.setCharAt(b - cur - 1, Character.forDigit(pairsArrayTemp[cur].getReversed(), 10));
                    cur++;
                }

                System.out.println(sb.toString());
                r = scanner.nextLine();
                operation--;
            }
        }
    }

    public static int whichChange(Pair pairSame, Pair pairDiff, int value1, int value2) {
        if (pairSame != null && pairDiff != null) {
            if (value1 == pairSame.getPrev()) {
                int operation = (value2 == pairDiff.getPrev()) ? 4 : 1;
                pairSame.setPrev(value1);
                pairDiff.setPrev(value2);
                return operation;
            } else {
                int operation = (value2 == pairDiff.getPrev()) ? 3 : 2;
                pairSame.setPrev(value1);
                pairDiff.setPrev(value2);
                return operation;
            }
        }
        return 4;
    }
}

class Pair {
    private int index;
    private boolean diff;
    private int prev;
    private int finalValue;

    public Pair() {
    }

    public Pair(int index, boolean diff, int prev) {
        this.index = index;
        this.diff = diff;
        this.prev = prev;
        this.finalValue = -1;
    }

    public int getIndex() {
        return index;
    }

    public int getPrev() {
        return prev;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public int getFinalValue() {
        return (finalValue == -1) ? prev : finalValue;
    }

    public void inverse() {
        if (diff) {
            prev = (prev == 0) ? 1 : 0;
        }
    }

    public void inverseBit() {
        prev = (prev == 0) ? 1 : 0;
    }

    public void inverseFinalValue() {
        finalValue = diff ? ((prev == 0) ? 1 : 0) : prev;
    }

    public void inverseBitFinalValue() {
        finalValue = (prev == 0) ? 1 : 0;
    }

    public void inverse2FinalValue() {
        finalValue = diff ? prev : ((prev == 0) ? 1 : 0);
    }

    public int getReversed() {
        finalValue = (finalValue == -1) ? prev : finalValue;
        return diff ? ((finalValue == 0) ? 1 : 0) : finalValue;
    }
}