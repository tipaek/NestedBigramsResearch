import java.util.*;

class DynamicStack {
    private int capacity = 2;
    private int top = -1;
    private char[] stackArray = new char[capacity];

    public void push(char c) {
        if (top + 1 == capacity) {
            capacity *= 2;
            stackArray = Arrays.copyOf(stackArray, capacity);
        }
        stackArray[++top] = c;
        System.out.print(c);
    }

    public void pop() {
        if (!isEmpty()) {
            top--;
        }
    }

    public char peek() {
        return stackArray[top];
    }

    public boolean isEmpty() {
        return top < 0;
    }

    public int size() {
        return top + 1;
    }

    public void display() {
        for (int i = 0; i <= top; i++) {
            System.out.print(stackArray[i] + " ");
        }
        System.out.println();
    }
}

public class Solution {
    public static void nest(char[] s) {
        DynamicStack openStack = new DynamicStack();
        DynamicStack digitStack = new DynamicStack();

        for (char c : s) {
            if (openStack.isEmpty() && c == '0') {
                digitStack.push('0');
            } else {
                if (openStack.isEmpty()) {
                    int num = Character.getNumericValue(c);
                    for (int j = 0; j < num; j++) {
                        openStack.push('(');
                    }
                    digitStack.push(c);
                } else {
                    int currentNum = Character.getNumericValue(c);
                    int topNum = Character.getNumericValue(digitStack.peek());

                    if (currentNum == topNum) {
                        digitStack.push(c);
                    } else if (currentNum > topNum) {
                        int numToPush = currentNum - openStack.size();
                        for (int j = 0; j < numToPush; j++) {
                            openStack.push('(');
                        }
                        digitStack.push(c);
                    } else {
                        int numToPop = openStack.size() - currentNum;
                        for (int j = 0; j < numToPop; j++) {
                            openStack.pop();
                            System.out.print(")");
                        }
                        digitStack.push(c);
                    }
                }
            }
        }

        while (!openStack.isEmpty()) {
            openStack.pop();
            System.out.print(")");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = Integer.parseInt(scanner.nextLine());
        String[] inputs = new String[t];

        for (int i = 0; i < t; i++) {
            inputs[i] = scanner.nextLine();
        }

        for (int i = 0; i < t; i++) {
            System.out.print("Case #" " + (i + 1) + ": ");
            nest(inputs[i].toCharArray());
        }

        scanner.close();
    }
}