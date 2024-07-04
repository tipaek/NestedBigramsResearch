import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Solution {

	public static void main(String[] args) throws IOException {
		IO io = new IO();
		int t = io.getInt();
		for(int i = 1; i <= t; i++) {
			int ans = solve(io);
			io.output("Case #" + i + ": ", ans == -1 ? "IMPOSSIBLE" : ans);
		}

		io.close();
	}

	private static int solve(IO io) throws IOException {
		int px = io.getInt(), py = io.getInt(), x = 0, y = 0;
		String ppath = io.getString();
		int steps = 0;
		Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
		map.putIfAbsent(new Pair<>(0, 0), 0);
		for(char c : ppath.toCharArray()) {
			if(c == 'N') py++;
			else if(c == 'S') py--;
			else if(c == 'E') px++;
			else px--;

			if(map.containsKey(new Pair<>(px, py))) {
				return map.get(new Pair<>(px, py)) + 1;
			}

			if(px != x) {
				if(px < x) {
					x--;
				} else {
					x++;
				}

				steps++;
				map.putIfAbsent(new Pair<>(x, y), steps);
			} else if(py != y) {
				if(py < y) {
					y--;
				} else {
					y++;
				}

				steps++;
				map.putIfAbsent(new Pair<>(x, y), steps);
			} else {
				return steps + 1;
			}
		}

		if(map.containsKey(new Pair<>(px, py))) {
			return map.get(new Pair<>(px, py));
		}

		if(x == px && y == py) {
			return steps;
		}

		return -1;
	}

	public static class Pair<T, R> {
		T left;
		R right;

		public Pair(T left, R right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return "(" + left.toString() + ", " + right.toString() + ")";
		}

		@Override
		public boolean equals(Object o) {
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			Pair<?, ?> pair = (Pair<?, ?>) o;
			return Objects.equals(left, pair.left) &&
					Objects.equals(right, pair.right);
		}

		@Override
		public int hashCode() {
			return Objects.hash(left, right);
		}
	}

	public static class IO {
		private BufferedReader input;
		private BufferedWriter output;

		public IO() {
			this(System.in, System.out);
		}

		public IO(InputStream input) {
			this(input, System.out);
		}

		public IO(OutputStream output) {
			this(System.in, output);
		}

		public IO(InputStream input, OutputStream output) {
			this.input = new BufferedReader(new InputStreamReader(input));
			this.output = new BufferedWriter(new OutputStreamWriter(output));
		}

		public IO(String input, String output) throws IOException {
			this.input = new BufferedReader(new FileReader(input));
			this.output = new BufferedWriter(new FileWriter(output));
		}

		public IO(String input, OutputStream output) throws FileNotFoundException {
			this.input = new BufferedReader(new FileReader(input));
			this.output = new BufferedWriter(new OutputStreamWriter(output));
		}

		public IO(InputStream input, String output) throws IOException {
			this.input = new BufferedReader(new InputStreamReader(input));
			this.output = new BufferedWriter(new FileWriter(output));
		}

		public int getInt() throws IOException {
			return Integer.valueOf(this.getString());
		}

		public BigInteger getBigInt() throws IOException {
			return new BigInteger(this.getString());
		}

		public double getDouble() throws IOException {
			return Double.valueOf(this.getString());
		}

		public BigDecimal getBigDecimal() throws IOException {
			return new BigDecimal(this.getString());
		}

		public boolean getBoolean() throws IOException {
			return Boolean.valueOf(this.getString());
		}

		public char getChar() throws IOException {
			return getString().charAt(0);
		}

		public String getString() throws IOException {
			StringBuilder value = new StringBuilder();
			int temp = this.input.read();
			while(temp != -1 && !Character.isWhitespace(temp)) {
				value.append((char) temp);
				temp = this.input.read();
			}

			return value.toString();
		}

		public int[] getIntArray() throws IOException {
			return Arrays.stream(this.getStringArray())
						 .mapToInt(Integer::valueOf)
						 .toArray();
		}

		public BigInteger[] getBigIntArray() throws IOException {
			return Arrays.stream(this.getStringArray())
						 .map(BigInteger::new)
						 .toArray(BigInteger[]::new);
		}

		public double[] getDoubleArray() throws IOException {
			return Arrays.stream(this.getStringArray())
						 .mapToDouble(Double::valueOf)
						 .toArray();
		}

		public BigDecimal[] getBigDecimalArray() throws IOException {
			return Arrays.stream(this.getStringArray())
						 .map(BigDecimal::new)
						 .toArray(BigDecimal[]::new);
		}

		public boolean[] getBooleanArray() throws IOException {
			String[] str = this.getStringArray();
			boolean[] arr = new boolean[str.length];
			for(int i = 0; i < str.length; i++) {
				arr[i] = Boolean.valueOf(str[i]);
			}

			return arr;
		}

		public char[] getCharArray() throws IOException {
			String[] str = this.getStringArray();
			char[] arr = new char[str.length];
			for(int i = 0; i < str.length; i++) {
				arr[i] = str[i].charAt(0);
			}

			return arr;
		}

		public String[] getStringArray() throws IOException {
			return this.input.readLine().split("\\s+");
		}

		public <T> void output(T output) throws IOException {
			this.output("", output);
		}

		public <T> void output(String prefix, T output) throws IOException {
			this.output.write(prefix);
			this.output.write(output.toString());
			this.output.newLine();
		}

		public void output(int[] output) throws IOException {
			this.output("", output);
		}

		public void output(String prefix, int[] output) throws IOException {
			List<Integer> list = new ArrayList<>(output.length);
			for(int e : output) {
				list.add(e);
			}

			this.output(prefix, list);
		}

		public void output(double[] output) throws IOException {
			this.output("", output);
		}

		public void output(String prefix, double[] output) throws IOException {
			List<Double> list = new ArrayList<>(output.length);
			for(double e : output) {
				list.add(e);
			}

			this.output(prefix, list);
		}

		public void output(boolean[] output) throws IOException {
			this.output("", output);
		}

		public void output(String prefix, boolean[] output) throws IOException {
			List<Boolean> list = new ArrayList<>(output.length);
			for(boolean e : output) {
				list.add(e);
			}

			this.output(prefix, list);
		}

		public <T> void output(String prefix, T[] output) throws IOException {
			this.output(prefix, Arrays.asList(output));
		}

		public <T> void output(List<T> output) throws IOException {
			this.output("", output);
		}

		public <T> void output(String prefix, List<T> output) throws IOException {
			this.output.write(prefix);
			for(int i = 0; i < output.size(); i++) {
				if(i != 0) {
					this.output.write(" ");
				}

				this.output.write(output.get(i).toString());
			}

			this.output.newLine();
		}

		public void close() throws IOException {
			this.input.close();
			this.output.close();
		}
	}
}