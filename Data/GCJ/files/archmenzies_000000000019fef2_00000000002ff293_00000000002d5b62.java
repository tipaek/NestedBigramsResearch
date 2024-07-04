import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.util.Arrays.stream;
import static round1.b.Expogo.Direction.E;
import static round1.b.Expogo.Direction.N;
import static round1.b.Expogo.Direction.S;
import static round1.b.Expogo.Direction.W;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	enum Direction {
		N(1),
		E(1),
		S(-1),
		W(-1);

		private int sign;

		Direction(int sign) {
			this.sign = sign;
		}

		public int getDistance(int i) {
			return sign * (int)pow(2, i);
		}
	}

	public static void main(String[] a) {
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
			int testCases = parseInt(inputReader.readLine());

			for (int t = 1; t <= testCases; t++) {
				Integer[] xY = stream(inputReader.readLine().split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
				int x = xY[0];
				int y = xY[1];

				int maxJumps = (int) ceil(log(abs(x) + abs(y) + 1) / log(2));

				String path;
				if (x != 0 && x % 2 != 0) {
					// Move X first
					String east = getPath(E.name(), x - E.getDistance(0), y, maxJumps);
					String west = getPath(W.name(), x - W.getDistance(0), y, maxJumps);
					path = reduce(east, west);
				} else if (y != 0 && y % 2 != 0) {
					// Move Y first
					String north = getPath(N.name(), x, y - N.getDistance(0), maxJumps);
					String south = getPath(S.name(), x, y - S.getDistance(0), maxJumps);
					path = reduce(north, south);
				} else if (x == 0 && y ==0) {
					path = "";
				} else {
					// Impossible
					path = "IMPOSSIBLE";
				}

				System.out.printf("Case #%d: %s%n", t, path);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getPath(String currentPath, int x, int y, int maxJumps) {
		if (currentPath.length() > maxJumps && (x != 0 || y != 0)) {
			return "IMPOSSIBLE";
		} else if (x == 0 && y == 0) {
			return currentPath;
		} else {
			String north = getPath(currentPath + N.name(), x, y - N.getDistance(currentPath.length()), maxJumps);
			String south = getPath(currentPath + S.name(), x, y - S.getDistance(currentPath.length()), maxJumps);
			String east = getPath(currentPath + E.name(), x - E.getDistance(currentPath.length()), y, maxJumps);
			String west = getPath(currentPath + W.name(), x - W.getDistance(currentPath.length()), y, maxJumps);
			String northSouth = reduce(north, south);
			String eastWest = reduce(east, west);
			return reduce(northSouth, eastWest);
		}
	}

	private static String reduce(String path1, String path2) {
		String path;
		if (path1.equals("IMPOSSIBLE") && path2.equals("IMPOSSIBLE")) {
			path = "IMPOSSIBLE";
		} else if (path1.equals("IMPOSSIBLE")) {
			path = path2;
		} else if (path2.equals("IMPOSSIBLE")) {
			path = path1;
		} else {
			path = path2.length() < path1.length() ? path2 : path1;
		}
		return path;
	}

}
