import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

	private final Scanner in;
	private InputStream inputStream;
	private final PrintWriter out;

	public static void main(String[] args) throws IOException {
		Solution solver = new Solution();
		solver.solve();
	}

	public Solution() {
		inputStream = System.in;

		in = new Scanner(inputStream);
		out = new PrintWriter(System.out);
	}

	public void solve() throws IOException {
		int nbTests = in.nextInt();
		for (int t = 1; t <= nbTests; t++) {
			solveTest(t);
		}

		out.close();
	}

	private void solveTest(int t) throws IOException {

		int x = in.nextInt();
		int y = in.nextInt();

		String P = in.next();

		int d = Math.abs(x) + Math.abs(y);

		int c = 0;

		while (c < d) {

			if (c >= P.length()) {
				out.println("Case #" + t + ": " + "IMPOSSIBLE");
				return;
			}

			char s = P.charAt(c);

			if (s == 'N') {
				y++;
			} else if (s == 'S') {
				y--;
			} else if (s == 'W') {
				x--;
			} else if (s == 'E') {
				x++;
			}

			d = Math.abs(x) + Math.abs(y);
			c++;
		}

		out.println("Case #" + t + ": " + c);
	}

	class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(String s) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(s));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public List<Integer> lineToInt(String delimiter) throws IOException {
			return Arrays.stream(nextLine().split(delimiter))
					.mapToInt(s -> Integer.parseInt(s))
					.boxed()
					.collect(Collectors.toList());
		}

		public Array2DInt array2DInt(int nbLines, int nbElmtsPerLine, String delimiter, Object... matches)
				throws IOException {
			Map<String, Integer> internal = new HashMap<>(matches.length >> 1);
			for (int i = 0; i < matches.length; i += 2) {
				String k = Objects.requireNonNull((String) matches[i]);
				int v = Objects.requireNonNull((int) matches[i + 1]);
				internal.put(k, v);
			}

			boolean convert = !internal.isEmpty();
			Array2DInt arr = new Array2DInt(nbLines, nbElmtsPerLine, 0);
			for (int i = 0; i < nbLines; i++) {
				String[] tokens = nextLine().split(delimiter);
				for (int j = 0; j < nbElmtsPerLine; j++) {
					arr.set(i, j, convert ? internal.get(tokens[j]) : Integer.parseInt(tokens[j]));
				}
			}
			return arr;
		}

		public int[] lineToIntArray(String delimiter) throws IOException {
			return Arrays.stream(nextLine().split(delimiter)).mapToInt(s -> Integer.parseInt(s)).toArray();
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}

	class Array2D<T> {
		private final T[][] array;
		private final int dimI;
		private final int dimJ;

		public Array2D(int dimI, int dimJ) {
			this.array = (T[][]) new Object[dimI][dimJ];
			this.dimI = dimI;
			this.dimJ = dimJ;
		}

		public Array2D(int dimI, int dimJ, T initValue) {
			this(dimI, dimJ);
			for (int i = 0; i < dimI; i++) {
				for (int j = 0; j < dimJ; j++) {
					set(i, j, initValue);
				}
			}
		}

		public Array2D(T[][] arrayInput) {
			this(arrayInput.length, arrayInput[0].length);
			for (int i = 0; i < dimI; i++) {
				for (int j = 0; j < dimJ; j++) {
					set(i, j, arrayInput[i][j]);
				}
			}
		}

		public int getDimI() {
			return dimI;
		}

		public int getDimJ() {
			return dimJ;
		}

		public T get(int i, int j) {
			return array[i][j];
		}

		public void set(int i, int j, T value) {
			array[i][j] = value;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < dimI; i++) {
				for (int j = 0; j < dimJ; j++) {
					builder.append(get(i, j)).append("\t");
				}
				builder.append(System.lineSeparator());
			}

			return builder.toString();
		}
	}

	class Array2DInt extends Array2D<Integer> {

		public Array2DInt(int dimI, int dimJ, int initValue) {
			super(dimI, dimJ, initValue);
		}

		public Array2DInt(int[][] arrayInput) {
			super(IntStream.range(0, arrayInput.length)
					.boxed()
					.map(i -> Arrays.stream(arrayInput[i]).boxed().toArray(Integer[]::new))
					.toArray(Integer[][]::new));
		}
	}
}
