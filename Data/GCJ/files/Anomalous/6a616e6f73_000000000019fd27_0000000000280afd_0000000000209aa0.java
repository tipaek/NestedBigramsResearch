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
            if (matrix == null) {
                System.out.println(String.format("Case #%d: IMPOSSIBLE", t + 1));
            } else {
                System.out.println(String.format("Case #%d: POSSIBLE", t + 1));
                for (int[] row : matrix) {
                    System.out.println(Arrays.stream(row)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" ")));
                }
            }
        }
    }

    private static int[][] solve(int N, int K) {
        List<int[]> rowPermutations = generateRowPermutations(N);
        Iterator<int[][]> iterator = new MatrixIterator(N, rowPermutations);

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

    private static void generatePermutations(int N, List<int[]> permutations, int[] current, int index) {
        if (index == N) {
            permutations.add(current.clone());
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
                generatePermutations(N, permutations, current, index + 1);
            }
        }
    }

    private static int calculateTrace(int[][] matrix) {
        int trace = 0;
        for (int i = 0; i < matrix.length; i++) {
            trace += matrix[i][i];
        }
        return trace;
    }

    private static class MatrixIterator implements Iterator<int[][]> {
        private final int N;
        private final List<int[]> rowPermutations;
        private final int[][] matrix;
        private final int[] indices;
        private boolean hasNext;

        MatrixIterator(int N, List<int[]> rowPermutations) {
            this.N = N;
            this.rowPermutations = rowPermutations;
            this.matrix = new int[N][N];
            this.indices = new int[N];
            this.hasNext = true;
            initialize();
        }

        private void initialize() {
            int rowIndex = 0;
            while (rowIndex >= 0) {
                if (indices[rowIndex] < rowPermutations.size()) {
                    matrix[rowIndex] = rowPermutations.get(indices[rowIndex]);
                    if (areColumnsUniqueUpToRow(matrix, rowIndex)) {
                        indices[rowIndex]++;
                        rowIndex++;
                        if (rowIndex == N) {
                            return;
                        }
                    } else {
                        indices[rowIndex]++;
                    }
                } else {
                    indices[rowIndex] = 0;
                    rowIndex--;
                    if (rowIndex >= 0) {
                        indices[rowIndex]++;
                    } else {
                        hasNext = false;
                        return;
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public int[][] next() {
            int[][] result = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
            advance();
            return result;
        }

        private void advance() {
            int rowIndex = N - 1;
            while (rowIndex >= 0) {
                if (indices[rowIndex] < rowPermutations.size()) {
                    matrix[rowIndex] = rowPermutations.get(indices[rowIndex]);
                    indices[rowIndex]++;
                    if (areColumnsUniqueUpToRow(matrix, rowIndex)) {
                        rowIndex++;
                        if (rowIndex == N) {
                            return;
                        }
                    }
                } else {
                    indices[rowIndex] = 0;
                    rowIndex--;
                    if (rowIndex >= 0) {
                        indices[rowIndex]++;
                    } else {
                        hasNext = false;
                        return;
                    }
                }
            }
        }

        private boolean areColumnsUniqueUpToRow(int[][] matrix, int lastRow) {
            for (int col = 0; col < N; col++) {
                boolean[] seen = new boolean[N + 1];
                for (int row = 0; row <= lastRow; row++) {
                    if (seen[matrix[row][col]]) {
                        return false;
                    }
                    seen[matrix[row][col]] = true;
                }
            }
            return true;
        }
    }
}