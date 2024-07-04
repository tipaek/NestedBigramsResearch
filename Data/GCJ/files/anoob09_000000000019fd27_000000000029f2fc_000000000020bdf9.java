/*package whatever //do not write package name here */

import java.io.*;
import java.util.*;
class GFG {
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int tc = 1;
		while (N > 0) {
		    int n = sc.nextInt();
		    int start[] = new int[n];
		    int end[] = new int[n];
		    for (int i = 0; i < n; i++) {
		        start[i] = sc.nextInt();
		        end[i] = sc.nextInt();
		    }String s = "C";
		    boolean c = true;
		    boolean j = false;
		    boolean impossible  = false;
		    int i = 1, k = 0;
		    int cet = end[0];
		    int jet = 0;
		    while (i < n) {
		        if (start[i] < end[k]) {
		            if (c && j) {
		                impossible = true;
		                break;
		            }
		            else if (!c) {
		                c = true;
		                s = s + "C";
		                cet = end[i];
		            }
		            else if (!j) {
		                j = true;
		                s = s + "J";
		                jet = end[i];
		            }
		            else{ 
		                s = s + "C";
		                c = true;
		                cet = end[i];
		            }
		            i++;
		        }
		        else {
		            if (cet <= start[i]) {
		                c = false;
		                cet = 0;
		            }
		            if (jet <= start[i]) {
		                j = false;
		                jet = 0;
		            }
		            k++;
		        }
		    }
		    if (impossible)
		        System.out.println("Case #" + tc + ": IMPOSSIBLE");
		    else
		        System.out.println("Case #" + tc + ": "+s);
		    N--;
		    tc++;
		}
	}
}ger, Integer> map = new HashMap<>();
		    int start[] = new int[n];
		    int end[] = new int[n];
		    for (int i = 0; i < n; i++) {
		        start[i] = sc.nextInt();
		        end[i] = sc.nextInt();
		        map.put(end[i], start[i]);
		    }String s = "C";
		    boolean c = true;
		    boolean j = false;
		    boolean impossible  = false;
		    int i = 1, k = 0;
		    int cet = end[0];
		    int jet = 0;
		    while (i < n) {
		        if (start[i] >= cet || start[i] <= map.get(cet)){
		            c = false;
		            cet = 0;
		        }
		        if (start[i] >= jet || start[i] <= map.get(jet)) {
		            j = false;
		            jet = 0;
		        }
		        if (c && j){
		            impossible = true;
		            break;
		        }
		        else if (!c) {
		            c = true;
		            s = s + "C";
		            cet = end[i]; 
		        }
		        else if (!j) {
		            j = true;
		            s = s + "J";
		            jet = end[i];
		        }
		        else {    
		            c = true;
		            s = s + "J";
		            cet = end[i];
		        }
		        i++;   
		    }
		    if (impossible)
		        System.out.println("Case #" + tc + ": IMPOSSIBLE");
		    else
		        System.out.println("Case #" + tc + ": "+s);
		    N--;
		    tc++;
		}
	}
}