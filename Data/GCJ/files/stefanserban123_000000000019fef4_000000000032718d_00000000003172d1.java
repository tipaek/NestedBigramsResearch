import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int tests = sc.nextInt();
		sc.nextLine(); // sometimes necesary after nextInt to go to next line
		
		for (int i = 1; i <= tests; i++) {
			String[] in = sc.nextLine().split(" "); 
			int n = Integer.parseInt(in[0]);
			int d = Integer.parseInt(in[1]);
			
			String[] slices = sc.nextLine().split(" "); 
			
			int result = solve(Stream.of(slices).mapToLong(Long::parseLong).toArray(), d, n);
			System.out.println("Case #" + i + ": " + result);			
		}
	}
	
	public static int solve (long[] slicess, Integer d, int n) {
		List<Long> slices = new ArrayList<>();
		Collections.addAll(slices, Arrays.stream(slicess).boxed().toArray(Long[]::new));
		
		Map<Long, Integer> map = new HashMap<>(); 
		List<Long> orderedList = new ArrayList<>();
		
        for (long current : slices) { 
            int count = map.getOrDefault(current, 0); 
            map.put(current, count + 1); 
        } 
        
        Map<Long, Integer> ordered = map.entrySet().stream()
        		.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        		.collect(Collectors.toMap(
        		          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));;
		
		
		orderedList.addAll(ordered.keySet());
		
//		System.out.println("=======================");
//		System.out.println(ordered);
		List<Long> orderBySize = orderedList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());  
		
		// case 1: is ez
		if (ordered.get(orderedList.get(0)) >= d ) {
//			System.out.println(0);
			return 0;
		}
		
		// case 1.5 only one slice
		if (n == 1) {
//			System.out.println(d - 1);
			return d - 1;
		}
		
		Integer result = 999999999;
		Long slice = orderedList.get(0);
			int internalCount = ordered.get(slice);
			int cuts = 0;
			long dblsVal = (long) orderedList.stream().filter(e -> e.equals(slice*2)).findAny().orElse(0L);
			long dbls = 0;
			
			
			
			List<Long> banList = new ArrayList<>();
			banList.add(slice);
			
			if (dblsVal != 0) {
				dbls = ordered.get(dblsVal);
				banList.add(dblsVal);
			}
			
			int oldResult = result;
			// case 2: find doubles
			for (int i=0;i<dbls;i++) {
				cuts++;
//				System.out.println("internalCount: " + internalCount);
//				System.out.println("dbls: " + dbls);
//				System.out.println("d: " + d);
				if (internalCount + cuts*2 >= d && result > cuts) {
					// found a result
//					System.out.println("@@@: " + cuts);
					result = cuts;
				}
			}
			
			int currentNumberOfSlices = internalCount + cuts *2;
			
			if (oldResult != result) {
				return result;
			}
			
			// case 3: not enough doubles
			//  - get biggest number (not our current slice)
			
			long biggest = 0;
			
			do {
				biggest = orderBySize.stream().filter(e->!banList.contains(e)).findFirst().orElse(0L);
				double potentialSlices = Math.floor( biggest / slice);
				int remaining = d - currentNumberOfSlices;
				
				if (remaining <= potentialSlices && result > cuts + remaining) {
					// last needed slice
					result = cuts + remaining;
					// job done
					break;
				} else {
					cuts += potentialSlices;
				}
				banList.add(biggest);
				// else keep going
			} while (biggest != 0);

//		System.out.println(result);
		return result;
	}
}

