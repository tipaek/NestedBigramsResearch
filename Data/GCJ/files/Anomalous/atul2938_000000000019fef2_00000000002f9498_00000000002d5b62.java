import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        int testCases = inputReader.nextInt();
        List<Long> powerList = new ArrayList<>();
        long powerValue = 1;

        while (powerValue > 0 && powerValue <= Long.MAX_VALUE) {
            powerList.add(powerValue);
            powerValue *= 2;
        }

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int t = 1; t <= testCases; t++) {
            long targetX = inputReader.nextLong();
            long targetY = inputReader.nextLong();

            Queue<Node> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            queue.add(new Node(0, 0, -1));
            int iterationCount = 0;
            Node resultNode = null;

            while (!queue.isEmpty() && iterationCount < 10000) {
                Node currentNode = queue.poll();
                iterationCount++;

                if (currentNode.x == targetX && currentNode.y == targetY) {
                    resultNode = currentNode;
                    break;
                }

                if (currentNode.index == powerList.size() - 1) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {
                    long newX = currentNode.x + powerList.get(currentNode.index + 1) * dx[k];
                    long newY = currentNode.y + powerList.get(currentNode.index + 1) * dy[k];
                    String key = newX + " " + newY;

                    if (!visited.contains(key)) {
                        visited.add(key);
                        StringBuilder newRoute = new StringBuilder(currentNode.route);

                        switch (k) {
                            case 0 -> newRoute.append('N');
                            case 1 -> newRoute.append('S');
                            case 2 -> newRoute.append('E');
                            case 3 -> newRoute.append('W');
                        }

                        Node newNode = new Node(newX, newY, currentNode.index + 1);
                        newNode.route = newRoute;
                        queue.add(newNode);
                    }
                }
            }

            StringBuilder outputRoute = new StringBuilder();
            if (resultNode == null) {
                outputRoute.append("IMPOSSIBLE");
            } else {
                outputRoute.append(resultNode.route);
            }
            outputWriter.printf("Case #%d: %s%n", t, outputRoute.toString());
        }

        inputReader.close();
        outputWriter.close();
    }

    static Reader inputReader = new Reader();
    static PrintWriter outputWriter = new PrintWriter(System.out);

    static class Node {
        long x, y;
        int index;
        StringBuilder route;

        Node(long x, long y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
            this.route = new StringBuilder();
        }
    }

    static class Reader {
        BufferedReader reader;
        StringTokenizer tokenizer;

        Reader() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        void close() throws IOException {
            reader.close();
        }
    }
}