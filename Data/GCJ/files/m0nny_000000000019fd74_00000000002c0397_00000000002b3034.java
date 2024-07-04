import java.util.*;
import java.io.*;
public class Solution {
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int t = sc.nextInt();
		for(int i = 1; i <= t; i++){
			int n = sc.nextInt();
			String[] s = new String[n];
			for(int j = 0; j < n; j++) {
				
			}
			System.out.println("Case #" + i + ": ");
		}
	}
	static class name implements comparable(){
		String s; int aCount;
		
	}
	static boolean evaluate(String large, String small) {
		for(int i = 0; i < large.length()-small.length(); i++) {
			if(large.charAt(i) == small.charAt(i)) {
				for(int j = i; j < large.length()-(large.length()-small.length()) + i; j++) {
					if(large.charAt(j) != small.charAt(j)) return false;
				}
			}
			else			return false;
		}
		if(large.length() == small.length()) {
			for(int i = 0; i < large.length(); i++) {
				if(large.charAt(i) != small.charAt(i)) return false;
			}
		}
		return true;
	}
	
}