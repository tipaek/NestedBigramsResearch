import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class Pair {
    int x;
    int y;
    String s;

    public Pair(int x, int y) {
        this(x, y, "");
    }

    public Pair(int x, int y, String s) {
        this.x = x;
        this.y = y;
        this.s = s;
    }

    ArrayList<Pair> getChildren() {
        int term = (int) Math.pow(2, s.length());
        ArrayList<Pair> list = new ArrayList<>();
        list.add(new Pair(x + term, y, s + "E"));
        list.add(new Pair(x - term, y, s + "W"));
        list.add(new Pair(x, y + term, s + "N"));
        list.add(new Pair(x, y - term, s + "S"));
        return list;
    }
}

public class Solution {

    static boolean contains(ArrayList<Pair> list, Pair pair) {
        for (Pair p : list) {
            if ((p.x == pair.x && p.y == pair.y) || (p.x == -pair.x && p.y == -pair.y)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        for (int i = 0; i < t; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            StringBuilder sb = new StringBuilder();
            String out = "hello";

            ArrayList<Pair> visited = new ArrayList<>();
            Stack<Pair> stack = new Stack<>();

            stack.push(new Pair(0, 0));
            boolean goal = false;

            while (!stack.isEmpty()) {
                Pair parent = stack.pop();
                visited.add(parent);

                for (Pair child : parent.getChildren()) {
                    if (!contains(visited, child) && Math.abs(child.x) <= Math.abs(x) && Math.abs(child.y) <= Math.abs(y)) {
                        if (child.x == x && child.y == y) {
                            out = child.s;
                            goal = true;
                            break;
                        } else if (child.x == -x && child.y == -y) {
                            for (char c : child.s.toCharArray()) {
                                switch (c) {
                                    case 'N' -> sb.append('S');
                                    case 'E' -> sb.append('W');
                                    case 'W' -> sb.append('E');
                                    case 'S' -> sb.append('N');
                                }
                            }
                            out = sb.toString();
                            goal = true;
                            break;
                        } else {
                            stack.push(child);
                        }
                    }
                }

                if (goal) {
                    break;
                }
            }

            if (!goal) {
                out = "IMPOSSIBLE";
            }

            System.out.println("Case #" + (i + 1) + ": " + out);
        }
        sc.close();
    }
}