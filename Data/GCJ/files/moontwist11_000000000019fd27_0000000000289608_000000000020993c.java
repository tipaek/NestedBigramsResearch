import java.util.*;

public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String firstLine = input.nextLine();
    int numTest = Integer.parseInt(firstLine.split("")[0]);
    for(int i = 0; i < numTest; i++) {
        System.out.print("Case #" + (i+1) + ": ");
        System.out.println();
    }
}