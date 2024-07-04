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

	public static void main(String[] a) {
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
			int testCases = parseInt(inputReader.readLine());

			for (int t = 1; t <= testCases; t++) {
				int N = parseInt(inputReader.readLine());
				TreeSet<Event> orderedEvents = new TreeSet<>();
				int[] startTimes = new int[N];
				int[] endTimes = new int[N];

				for (int n = 0; n < N; n++) {
					Integer[] times = Arrays.stream(inputReader.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
					orderedEvents.add(new Event(n, times[0], times[1]));
//					startTimes[n] = times[0];
//					endTimes[n] = times[1];
				}
//				Integer[] sortedIndexes = sortIndexes(startTimes);

				byte[] output = new byte[N];
				int camFree = 0;
				int jamieFree = 0;
				/*for (int i = 0; i < N; i++) {
					if (camFree <= startTimes[sortedIndexes[i]]) {
						camFree = endTimes[sortedIndexes[i]];
						output[sortedIndexes[i]] = 'C';
					} else if (jamieFree <= startTimes[sortedIndexes[i]]) {
						jamieFree = endTimes[sortedIndexes[i]];
						output[sortedIndexes[i]] = 'J';
					} else {
						output = "IMPOSSIBLE".getBytes();
						break;
					}
				}*/
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

		Event(int i, int start, int end) {
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
