import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        
        int testCases = scanner.nextInt();
        for (int testCase = 1; testCase <= testCases; testCase++) {
            int numPoints = scanner.nextInt();
            
            int[] xCoords = new int[numPoints];
            int[] yCoords = new int[numPoints];
            
            for (int i = 0; i < numPoints; i++) {
                xCoords[i] = scanner.nextInt();
                yCoords[i] = scanner.nextInt();
            }
            
            double[][] slopes = new double[numPoints][numPoints];
            
            for (int i = 0; i < numPoints; i++) {
                for (int j = i + 1; j < numPoints; j++) {
                    int deltaX = xCoords[j] - xCoords[i];
                    int deltaY = yCoords[j] - yCoords[i];
                    slopes[i][j] = (deltaX == 0) ? Double.MAX_VALUE : (double) deltaY / deltaX;
                }
            }
            
            Map<Double, Integer> slopeCount = new HashMap<>();
            int maxCount = 0;
            double maxSlope = 0.0;
            
            for (int i = 0; i < numPoints; i++) {
                for (int j = i + 1; j < numPoints; {
                    double currentSlope = slopes[i][j];
                    slopeCount.put(currentSlope, slopeCount.getOrDefault(currentSlope, 0) + 1);
                    int currentCount = slopeCount.get(currentSlope);
                    if (currentCount > maxCount) {
                        maxCount = currentCount;
                        maxSlope = currentSlope;
                    }
                }
            }
            
            Set<Integer> uniquePoints = new HashSet<>();
            for (int i = 0; i < numPoints; i++) {
                for (int j = i + 1; j < numPoints; j++) {
                    if (slopes[i][j] == maxSlope) {
                        uniquePoints.add(i);
                        uniquePoints.add(j);
                    }
                }
            }
            
            int result = uniquePoints.size() + 2;
            if (result % 2 == 1) result--;
            if (result > numPoints) result = numPoints;
            
            System.out.println("Case #" + testCase + ": " + result);
        }
    }
}