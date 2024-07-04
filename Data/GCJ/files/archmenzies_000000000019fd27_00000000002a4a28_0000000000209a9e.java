import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {

	static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] a) {
		try {
			Integer[] tAndB = Arrays.stream(inputReader.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);

			int testCases = tAndB[0];
			int arrLength = tAndB[1];

			for (int t = 1; t <= testCases; t++) {

				int sameIndex = -1;
				boolean[] sameBit = new boolean[0];
				int diffIndex = -1;
				boolean[] diffBit = new boolean[0];

				int queryCount = 0;
				Boolean[] array = new Boolean[arrLength];
				for (int i = 0; i < arrLength/2; i++) {
					array[i] = query(i);
					queryCount++;

					if (queryCount != 1 && queryCount % 10 == 1) {
						array = quantumUpdate(array, sameIndex, sameBit, diffIndex, diffBit);
						queryCount += 2;
					}

					array[arrLength-1-i] = query(arrLength-1-i);
					queryCount++;

					if (array[i] == null) {
						throw new IOException("NPE: " + array);
					}
					boolean equalBits = array[i].equals(array[arrLength-1-i]);
					if (sameIndex == -1 && equalBits) {
						sameIndex = i;
						sameBit = new boolean[] { array[i] };
					}
					if (diffIndex == -1 && !equalBits) {
						diffIndex = i;
						diffBit = new boolean[] { array[i] };
					}
				}

				if (!guess(array)) {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static boolean query(int index) throws IOException {
		System.out.println(index+1);
		System.out.flush();
		String response = inputReader.readLine();
		if (response.equals("N")) {
			throw new IOException(format("Bad Query at index %d (Sent index %d)", index, index+1));
		}
		return response.equals("1");
	}

	private static boolean guess(Boolean[] array) throws IOException {
		System.out.println(Arrays.stream(array).map(bit -> bit ? "1" : "0").collect(joining()));
		System.out.flush();
		return inputReader.readLine().equals("Y");
	}

	static Boolean[] reverse(Boolean[] array) {
		Boolean[] reversed = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			reversed[array.length-1-i] = array[i];
		}
		return  reversed;
	}

	static Boolean[] compliment(Boolean[] array) {
		Boolean[] compliment = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			compliment[i] = array[i] == null ? null : !array[i];
		}
		return  compliment;
	}

	static Boolean[] reverseCompliment(Boolean[] array) {
		Boolean[] reversed = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			reversed[array.length-1-i] = array[i] == null ? null : !array[i];
		}
		return  reversed;
	}

	static Boolean[] quantumUpdate(Boolean[] oldArray, int sameIndex, boolean[] sameBit, int diffIndex, boolean[] diffBit) throws IOException {
		try {
			boolean sameChanged = false;
			if (sameIndex != -1) {
				boolean newSameBit = query(sameIndex);
				sameChanged = newSameBit != sameBit[0];
				sameBit[0] = newSameBit;
			} else {
				// Want to keep numbers of queries even
				query(0);
			}

			boolean diffChanged = false;
			if (diffIndex != -1) {
				boolean newDiffBit = query(diffIndex);
				diffChanged = newDiffBit != diffBit[0];
				diffBit[0] = newDiffBit;
			} else {
				// Want to keep numbers of queries even
				query(0);
			}

			if (sameChanged && diffChanged) {
				return compliment(oldArray);
			} else if (sameChanged) {
				return reverseCompliment(oldArray);
			} else if (diffChanged) {
				return reverse(oldArray);
			} else {
				return oldArray;
			}
		} catch (IOException e) {
			throw new IOException(format("Quantum Update Failed with sameIndex = %d, diffIndex = %d", sameIndex, diffIndex), e);
		}
	}
}
