import java.util.*;


public class Solution {
	private static int n;
	private static ArrayList<Integer> num = new ArrayList<Integer>();
	private static ArrayList<Integer> begin = new ArrayList<Integer>();
	private static ArrayList<Integer> end = new ArrayList<Integer>();
	private static boolean have(ArrayList<Integer> a, ArrayList<Integer> b) {
		for (int i = 0; i < b.size(); i++) {
			if(a.contains(b.get(i))) return false;
		}
		return true;
	}
	private static String solve() {
		int c = 0 , j = 0;
		ArrayList<Integer> cam = new ArrayList<Integer>();
		ArrayList<Integer> jam = new ArrayList<Integer>();
		ArrayList<Integer> m = new ArrayList<Integer>();
		String t = "";
		int cas = 0;
		for(int i = 0; i < n; i++) {
			m.addAll(num.subList(begin.get(i), end.get(i)));
			if(cas == 0) {
			if(c < 2 & have(cam,m)) {
				t+="C";
				c++;
				j--;
				cam.addAll(m);
				cas = 1;
			}else if(j < 2 & have(jam,m)) {
				t+="J";
				j++;
				c--;
				jam.addAll(m);
				cas = 0;
			}else {
				t = "IMPOSSIBLE";
				break;
			}}else if(cas == 1) {
				if(j < 2 & have(jam,m)) {
					t+="J";
					j++;
					c--;
					jam.addAll(m);
					cas = 0;
				}else if(c < 2 & have(cam,m)) {
					t+="C";
					c++;
					j--;
					cam.addAll(m);
					cas = 1;
				}else {
					t = "IMPOSSIBLE";
					break;}
			}else {
				if(c < 2 & have(cam,m)) {
					t+="C";
					c++;
					cam.addAll(m);
					cas = 1;
				}else if(j < 2 & have(jam,m)) {
					t+="J";
					j++;
					jam.addAll(m);
					cas = 0;
				}else {
					t = "IMPOSSIBLE";
					break;}
			}
			m.clear();
		}
		
		return t;
	}