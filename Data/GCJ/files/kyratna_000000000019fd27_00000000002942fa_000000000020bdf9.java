import java.io.*;
import java.util.*;

public class Solution {
	static void arrangeSchedule(int[][] arr, int pos) {
		int [][] camersonSchedule = new int [arr.length][2];
		int [][] jamieSchedule = new int[arr.length][2];
		int camSchedulePos=0, jamSchedulePos=0;
		boolean overLapping1,overLapping2;
		String output="";

		for(int i=0; i<arr.length; i++){
			overLapping1=false;
			overLapping2=false;

			if(camersonSchedule.length>0){
				for(int j=0; j<camersonSchedule.length;j++){
					if(isOverlapping(arr[i][0],arr[i][1],camersonSchedule[j][0])){
						overLapping1=true;
						break;
					}
				}
			}
			if(overLapping1==true && overLapping2==true){
				output = 'IMPOSSIBLE';
				break;
			}
			else{
				if(overLapping1){
					output="C";
					camersonSchedule[camSchedulePos][0]=arr[i][0];
					camersonSchedule[camSchedulePos][1]=arr[i][1];
					camSchedulePos++;
				}
				else{
					output="J";
					jamieSchedule[jamSchedulePos][0]=arr[i][0];
					jamieSchedule[jamSchedulePos][1]=arr[i][1];
					jamSchedulePos++;

				}
			}
		}
		System.out.println("Case #"+pos": "+output);
	}
	static boolean isOverlapping(int start1, int end1, int start2, int end2){
		int totalRange = Math.max(end1,end2) - Math.min(start1, start2);
		int sumOfRanges = (end1 - start1) + (end2 - start2);
		int overlappingInterval = 0;

		if(sumOfRanges > totalRange){
			return true;
		}

		return false;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args){
		int n = scanner.nextInt();

		for(int h = 1; h<=n; h++){
			int d = scanner.nextInt();
			int [][] arr = new int[d][2];

			for(int i=0; i<d; i++){
				for(int j=0; j<2; j++){
					arr[i][j] = scanner.nextline();
				}
			}
			arrangeSchedule(arr,h);
		}
		Scanner.close();
	}
}
