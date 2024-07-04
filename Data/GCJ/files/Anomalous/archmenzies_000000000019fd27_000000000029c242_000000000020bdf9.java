import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class Solution {

    private static Integer[] sortIndexes(int[] startTimes) {
        Integer[] indexes = IntStream.range(0, startTimes.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indexes, Comparator.comparingInt(i -> startTimes[i]));
        return indexes;
    }

    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            int testCases = parseInt(inputReader.readLine());

            for (int t = 1; t <= testCases; t++) {
                int N = parseInt(inputReader.readLine());
                TreeSet<Event> orderedEvents = new TreeSet<>();
                int[] startTimes = new int[N];
                int[] endTimes = new int[N];

                for (int n = 0; n < N; n++) {
                    String[] times = inputReader.readLine().split(" ");
                    int start = parseInt(times[0]);
                    int end = parseInt(times[1]);
                    orderedEvents.add(new Event(n, start, end));
                }

                byte[] output = new byte[N];
                int camFree = 0;
                int jamieFree = 0;

                while (!orderedEvents.isEmpty()) {
                    Event nextEvent = orderedEvents.pollFirst();
                    if (camFree <= nextEvent.start) {
                        camFree = nextEvent.end;
                        output[nextEvent.index] = 'C';
                    } else if (jamieFree <= nextEvent.start) {
                        jamieFree = nextEvent.end;
                        output[nextEvent.index] = 'J';
                    } else {
                        output = "IMPOSSIBLE".getBytes();
                        break;
                    }
                }

                System.out.printf("Case #%d: %s%n", t, new String(output));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Event implements Comparable<Event> {
        final int index, start, end;

        Event(int index, int start, int end) {
            this.index = index;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Event other) {
            return Integer.compare(this.start, other.start);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Event event = (Event) other;
            return start == event.start;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(start);
        }
    }
}