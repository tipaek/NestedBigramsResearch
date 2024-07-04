import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static int targetX = 0;
    private static int targetY = 0;
    private static List<List<Integer>> possiblePaths = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int testCases = scanner.nextInt();
        
        for (int i = 0; i < testCases; i++) {
            targetX = scanner.nextInt();
            targetY = scanner.nextInt();
            List<Integer> moves = new ArrayList<>();
            findPath(0, 0, 1, moves);
            
            if (!possiblePaths.isEmpty()) {
                List<Integer> optimalPath = possiblePaths.get(0);
                for (List<Integer> path : possiblePaths) {
                    if (path.size() < optimalPath.size()) {
                        optimalPath = path;
                    }
                }
                List<String> directions = new ArrayList<>();
                for (int move : optimalPath) {
                    switch (move) {
                        case 1 -> directions.add("E");
                        case -1 -> directions.add("W");
                        case 2 -> directions.add("N");
                        case -2 -> directions.add("S");
                    }
                }
                writer.print("Case #" + (i + 1) + ": ");
                for (String direction : directions) {
                    writer.print(direction);
                }
                writer.println();
                possiblePaths.clear();
            } else {
                writer.println("Case #" + (i + 1) + ": IMPOSSIBLE");
            }
        }
        writer.close();
    }

    private static void findPath(int currentX, int currentY, int stepSize, List<Integer> moves) {
        if (Math.abs(currentX) > Math.abs(targetX) || Math.abs(currentY) > Math.abs(targetY)) {
            return;
        }
        if (currentX == targetX && currentY == targetY) {
            possiblePaths.add(new ArrayList<>(moves));
            return;
        }
        
        List<Integer> newMoves = new ArrayList<>(moves);
        newMoves.add(1);
        findPath(currentX + stepSize, currentY, stepSize * 2, newMoves);
        
        newMoves.remove(newMoves.size() - 1);
        newMoves.add(2);
        findPath(currentX, currentY + stepSize, stepSize * 2, newMoves);
        
        newMoves.remove(newMoves.size() - 1);
        newMoves.add(-1);
        findPath(currentX - stepSize, currentY, stepSize * 2, newMoves);
        
        newMoves.remove(newMoves.size() - 1);
        newMoves.add(-2);
        findPath(currentX, currentY - stepSize, stepSize * 2, newMoves);
    }
}