import java.io.*;
import java.util.*;
/**
 * Write a description of class Snail here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Solution
{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int nn = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for(int n = 1; n <= nn; n++){
            	String str = br.readLine();
		String result = "";
		int nump = 0;

	    	for(int index = 0; index < str.length(); index++){
	    		int x = Integer.parseInt(str.charAt(index));
			if(
			for(int i = 0; i < x; i++){
				result += "(";
				nump++;
			}
			result += x;
		}

            out.println("Case #" + nn + ": " + token + " " + r + " " + c);
        }
        out.close();
    }
}

public static void sortbyColumn(int arr[][]) 
    { 
        Arrays.sort(arr, new Comparator<int[]>() { 
                @Override
                public int compare(final int[] entry1, final int[] entry2) {
                    if(entry1[0] == entry2[0]){
                        return entry2[1] - entry1[1];
                    }
                    return entry1[0] - entry2[0];
                }
            });  
    } 
