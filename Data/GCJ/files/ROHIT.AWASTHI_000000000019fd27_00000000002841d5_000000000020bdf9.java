import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

public class Solution {
	
	private static final String IMPOSSIBLE = "IMPOSSIBLE";
	private static final char CAMERON = 'C';
	private static final char JAMMIE = 'J';
	
	private static class Timing {
		private int start;
		private int end;
		private int index;

		public Timing(int start, int end, int index) {
			this.start = start;
			this.end = end;
			this.index = index;
		}
		
		@Override
		public String toString() {
			return "Timing [start=" + start + ", end=" + end + ", index=" + index + "]";
		}

		public int getStart() {
			return start;
		}

		public int getIndex() {
			return index;
		}

		public boolean isConflictingWithOther(Timing timing) {
			if(timing==null) {
				return false;
			}
			return ((this.start>=timing.start && this.start<timing.end) || (this.end<=timing.end && this.end>timing.start));
		}
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	    int t = in.nextInt();
	    for (int i = 1; i <= t; ++i) {
		    int n = in.nextInt();
		    StringBuilder result = new StringBuilder(n);
		    Timing[] timingArr = new Timing[n];
		    for(int j=0 ; j<n; j++) {
		    	timingArr[j] = new Timing(in.nextInt(), in.nextInt(), j);
		    	result.append(CAMERON);
		    }
		    timingArr = Stream.of(timingArr).sorted(Comparator.comparing(e->e.getStart())).toArray(Timing[]::new);
		    Timing cameronTiming = null;
		    Timing jammieTiming = null;
		    boolean isImpossible = false;
		    for(int j=0; j<n; j++) {
		    	if(timingArr[j].isConflictingWithOther(cameronTiming)) {
		    		if(timingArr[j].isConflictingWithOther(jammieTiming)) {
		    			isImpossible = true;
		    			break;
		    		} else {
		    			jammieTiming = timingArr[j];
		    			result.setCharAt(jammieTiming.getIndex(), JAMMIE);
		    		}
		    	} else {
		    		cameronTiming = timingArr[j];
	    			result.setCharAt(cameronTiming.getIndex(), CAMERON);
		    	}
		    }
	    	System.out.println("Case #" + i + ": " + (isImpossible ? IMPOSSIBLE : result.toString()));
	    }
	    in.close();
	}
	
	
}
