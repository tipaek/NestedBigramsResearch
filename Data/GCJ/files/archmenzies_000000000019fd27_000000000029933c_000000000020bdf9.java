import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.TreeSet;

public class Solution {

	public static void main(String[] a) {
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
			int testCases = parseInt(inputReader.readLine());

			for (int t = 1; t <= testCases; t++) {
				int N = parseInt(inputReader.readLine());
				TreeSet<Event> orderedEvents = new TreeSet<>();

				for (int n = 0; n < N; n++) {
					Integer[] times = Arrays.stream(inputReader.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
					orderedEvents.add(new Event(n, times[0], times[1]));
				}

				byte[] output = new byte[N+1];
				output[N] = 0x0;
				int camFree = 0, jamieFree = 0;
				while (!orderedEvents.isEmpty()) {
					Event nextEvent = orderedEvents.pollFirst();
					if (camFree <= nextEvent.start) {
						camFree = nextEvent.end;
						output[nextEvent.i] = 'C';
					} else if (jamieFree <= nextEvent.start) {
						jamieFree = nextEvent.end;
						output[nextEvent.i] = 'J';
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
		final int i, start, end;

		public Event(int i, int start, int end) {
			this.i = i;
			this.start = start;
			this.end = end;
		}

		@Override
		public int compareTo(Event that) {
			return this.start - that.start;
		}
	}
}
