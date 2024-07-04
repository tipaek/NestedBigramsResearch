import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int T = scanner.nextInt();
    for (int t = 0; t < T; t++) {
      int N = scanner.nextInt();
      int K = scanner.nextInt();

      int[][] matrix = solve(N, K);
      System.out.printf("Case #%d: %s%n", t + 1, matrix == null ? "IMPOSSIBLE" : "POSSIBLE");
      if (matrix != null) {
        for (int[] row : matrix) {
          System.out.println(IntStream.of(row).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        }
      }
    }
  }

  private static int[][] solve(int N, int K) {
    List<int[]> rowPermutations = generateRowPermutations(N);

    Iterator<int[][]> iterator = new Iterator<int[][]>() {
      final int[][] matrix = new int[N][N];
      final int[] permutations = new int[N];
      boolean finished = false;

      {
        int rowIndex = 0;
        while (true) {
          for (; permutations[rowIndex] < rowPermutations.size(); permutations[rowIndex]++) {
            matrix[rowIndex] = rowPermutations.get(permutations[rowIndex]);
            if (areColumnsUnique(matrix, rowIndex)) {
              rowIndex++;
              if (rowIndex == N) break;
            }
          }

          if (rowIndex == N) break;

          permutations[rowIndex] = 0;
          rowIndex--;

          if (rowIndex < 0) {
            finished = true;
            break;
          }

          permutations[rowIndex]++;
        }
      }

      @Override
      public boolean hasNext() {
        return !finished;
      }

      @Override
      public int[][] next() {
        int[][] result = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);

        int rowIndex = permutations.length - 1;
        while (true) {
          for (; permutations[rowIndex] < rowPermutations.size(); permutations[rowIndex]++) {
            matrix[rowIndex] = rowPermutations.get(permutations[rowIndex]);
            if (areColumnsUnique(matrix, rowIndex)) {
              permutations[rowIndex]++;
              rowIndex++;
              if (rowIndex == N) return result;
            }
          }

          permutations[rowIndex] = 0;
          rowIndex--;

          if (rowIndex < 0) {
            finished = true;
            break;
          }

          permutations[rowIndex]++;
        }

        return result;
      }

      private boolean areColumnsUnique(int[][] matrix, int lastRow) {
        for (int col = 0; col < N; col++) {
          boolean[] seen = new boolean[N + 1];
          for (int row = 0; row <= lastRow; row++) {
            if (seen[matrix[row][col]]) return false;
            seen[matrix[row][col]] = true;
          }
        }
        return true;
      }
    };

    while (iterator.hasNext()) {
      int[][] matrix = iterator.next();
      if (calculateTrace(matrix) == K) {
        return matrix;
      }
    }

    return null;
  }

  private static List<int[]> generateRowPermutations(int N) {
    List<int[]> permutations = new ArrayList<>();
    generatePermutations(N, permutations, new int[N], 0);
    return permutations;
  }

  private static void generatePermutations(int N, List<int[]> acc, int[] current, int index) {
    if (index == N) {
      acc.add(current.clone());
      return;
    }

    for (int i = 1; i <= N; i++) {
      boolean used = false;
      for (int j = 0; j < index; j++) {
        if (current[j] == i) {
          used = true;
          break;
        }
      }
      if (!used) {
        current[index] = i;
        generatePermutations(N, acc, current, index + 1);
      }
    }
  }

  private static int calculateTrace(int[][] matrix) {
    return IntStream.range(0, matrix.length).map(i -> matrix[i][i]).sum();
  }
}