import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Solution {

    private static void test() throws IOException {
        StringBuilder total = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/testIn"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line).append("\n");
            }
        }
        InputStream testInput = new ByteArrayInputStream(total.toString().getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
    }

    public static void main(String[] args) throws IOException {
        // test();
        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
            int t = Integer.parseInt(scanner.nextLine());
            for (int x = 1; x <= t; ++x) {
                System.out.println("Case #" + x + ": " + getResult(scanner.nextLine()));
            }
        }
    }

    private static String getResult(String targetString) {
        String[] settings = targetString.split(" ");
        int east = Integer.parseInt(settings[0]);
        int north = Integer.parseInt(settings[1]);
        char[] directions = settings[2].toCharArray();
        if (north == 0 && east == 0) return "0";

        int maxTurns = directions.length;
        for (int turn = 0; turn < maxTurns; turn++) {
            char direction = directions[turn];
            int turnInt = turn + 1;
            switch (direction) {
                case 'S' -> north--;
                case 'N' -> north++;
                case 'E' -> east++;
                case 'W' -> east--;
            }
            if (Math.abs(north) + Math.abs(east) <= turnInt) {
                return String.valueOf(turnInt);
            }
        }
        return "IMPOSSIBLE";
    }
}