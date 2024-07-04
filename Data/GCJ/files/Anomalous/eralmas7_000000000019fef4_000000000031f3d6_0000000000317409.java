import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Solution {

    private static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return 31 * (31 + x) + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position other = (Position) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public String toString() {
            return "[x=" + x + ", y=" + y + "]";
        }
    }

    private static final String CASES = "Case #";
    private static final String COLON = ": ";
    private static final String NEWLINE = "\n";

    public static void solve(final Input input, final PrintWriter output) throws IOException {
        final int numberOfTestCases = input.nextInt();
        final StringBuilder resultBuilder = new StringBuilder(2000 * numberOfTestCases);

        for (int z = 1; z <= numberOfTestCases; z++) {
            resultBuilder.append(CASES).append(z).append(COLON);
            int x = input.nextInt();
            int y = input.nextInt();
            Position targetPosition = new Position(x, y);
            String instruction = input.next();
            int steps = 0;
            Queue<Position> positionsQueue = new ArrayDeque<>();
            Set<Position> visitedPositions = new HashSet<>();
            positionsQueue.add(new Position(0, 0));
            boolean isFound = false;

            if (x == 0 && y == 0) {
                isFound = true;
                instruction = "";
                steps = 0;
            }

            for (char ch : instruction.toCharArray()) {
                int size = positionsQueue.size();
                while (size > 0) {
                    Position currentPosition = positionsQueue.poll();
                    visitedPositions.add(currentPosition);
                    if (currentPosition.equals(targetPosition)) {
                        isFound = true;
                        break;
                    }
                    positionsQueue.add(new Position(currentPosition.x + 1, currentPosition.y));
                    positionsQueue.add(new Position(currentPosition.x - 1, currentPosition.y));
                    positionsQueue.add(new Position(currentPosition.x, currentPosition.y + 1));
                    positionsQueue.add(new Position(currentPosition.x, currentPosition.y - 1));
                    size--;
                }
                if (isFound) break;

                switch (ch) {
                    case 'S' -> targetPosition.y -= 1;
                    case 'N' -> targetPosition.y += 1;
                    case 'E' -> targetPosition.x += 1;
                    case 'W' -> targetPosition.x -= 1;
                }

                steps++;
                if (visitedPositions.contains(targetPosition)) {
                    isFound = true;
                    break;
                }
            }

            int size = positionsQueue.size();
            while (size > 0) {
                Position currentPosition = positionsQueue.poll();
                if (currentPosition.equals(targetPosition)) {
                    isFound = true;
                    break;
                }
                positionsQueue.add(new Position(currentPosition.x + 1, currentPosition.y));
                positionsQueue.add(new Position(currentPosition.x - 1, currentPosition.y));
                positionsQueue.add(new Position(currentPosition.x, currentPosition.y + 1));
                positionsQueue.add(new Position(currentPosition.x, currentPosition.y - 1));
                positionsQueue.add(currentPosition);
                size--;
            }

            resultBuilder.append(isFound ? steps : "IMPOSSIBLE").append(NEWLINE);
        }

        output.println(resultBuilder);
    }

    public static void main(final String[] args) throws IOException {
        try (final PrintWriter output = new PrintWriter(System.out);
             final Input input = new Input(new BufferedReader(new InputStreamReader(System.in)))) {
            solve(input, output);
        }
    }

    private static final class Input implements Closeable {
        private final BufferedReader reader;
        private final StringBuilder sb = new StringBuilder();

        public Input(final BufferedReader reader) {
            this.reader = reader;
        }

        public String next() throws IOException {
            sb.setLength(0);
            int c;
            while ((c = reader.read()) != -1 && Character.isWhitespace(c)) {
                // Skip whitespace
            }
            if (c == -1) return null;
            sb.append((char) c);

            while ((c = reader.read()) != -1 && !Character.isWhitespace(c)) {
                sb.append((char) c);
            }

            return sb.toString();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }
}