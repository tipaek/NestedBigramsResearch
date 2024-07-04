import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static int targetX = 0;
    private static int targetY = 0;
    private static List<List<Integer>> validPaths = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrintWriter output = new PrintWriter(System.out);
        int testCases = scanner.nextInt();
        
        for (int i = 0; i < testCases; i++) {
            targetX = scanner.nextInt();
            targetY = scanner.nextInt();
            List<Integer> currentPath = new ArrayList<>();
            findPath(0, 0, 1, currentPath);
            
            if (!validPaths.isEmpty()) {
                List<Integer> optimalPath = validPaths.get(0);
                for (List<Integer> path : validPaths) {
                    if (path.size() < optimalPath.size()) {
                        optimalPath = path;
                    }
                }
                
                List<String> directions = new ArrayList<>();
                for (int step : optimalPath) {
                    switch (step) {
                        case 1 -> directions.add("E");
                        case -1 -> directions.add("W");
                        case 2 -> directions.add("N");
                        case -2 -> directions.add("S");
                    }
                }
                
                output.print("Case #" + (i + 1) + ": ");
                for (String direction : directions) {
                    output.print(direction);
                }
                output.println();
                validPaths.clear();
            } else {
                output.println("Case #" + (i + 1) + ": IMPOSSIBLE");
            }
        }
        output.close();
    }

    public static void findPath(int currentX, int currentY, int stepSize, List<Integer> path) {
        if (Math.abs(currentX) > Math.abs(targetX) * 3 || Math.abs(currentY) > Math.abs(targetY) * 3) {
            return;
        }
        
        if (currentX == targetX && currentY == targetY) {
            validPaths.add(new ArrayList<>(path));
            return;
        }
        
        path.add(1);
        findPath(currentX + stepSize, currentY, stepSize * 2, path);
        path.remove(path.size() - 1);
        
        path.add(2);
        findPath(currentX, currentY + stepSize, stepSize * 2, path);
        path.remove(path.size() - 1);
        
        path.add(-1);
        findPath(currentX - stepSize, currentY, stepSize * 2, path);
        path.remove(path.size() - 1);
        
        path.add(-2);
        findPath(currentX, currentY - stepSize, stepSize * 2, path);
        path.remove(path.size() - 1);
    }
}