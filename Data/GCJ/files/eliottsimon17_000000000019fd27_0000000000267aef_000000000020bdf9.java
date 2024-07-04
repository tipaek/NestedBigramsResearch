import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {

        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testCount = Integer.parseInt(in.nextLine());
        for(int i = 0 ; i < testCount; i++){
            int numberOfAcitivities = Integer.parseInt(in.nextLine());
            String[] schedules = new String[numberOfAcitivities];
            for(int j = 0 ; j < numberOfAcitivities; j++){
                String schedule = in.nextLine();
                schedules[j] = schedule;
            }
            findActivities(schedules);
            System.out.println("Case #" + (i+1) + ": " + findActivities(schedules));
        }
    }

    private static String findActivities(String[] schedules) {
        ArrayList<Integer[]> getSchedulesTime = new ArrayList<Integer[]>();
        for(int i = 0; i < schedules.length; i++){
            String[] splits = schedules[i].split(" ");
            int[] numArray = Arrays.stream(splits).mapToInt(Integer::parseInt).toArray();
            Integer[] boxArray = Arrays.stream(numArray).boxed().toArray(Integer[]::new);
            getSchedulesTime.add(boxArray);
        }
        boolean flag = false;
        String res = "";
        String[] newString = new String[getSchedulesTime.size()];
        for(int i = 0 ; i < getSchedulesTime.size();i++){
            for(int j = i+1; j <= getSchedulesTime.size()-1; j++) {
                if(i==0) {
                    newString[i] = "J";
                }
                if (getSchedulesTime.get(i)[1] > getSchedulesTime.get(j)[0] && getSchedulesTime.get(i)[0] < getSchedulesTime.get(j)[1] && newString[i]==newString[j]) {
                   res = "IMPOSSIBLE";
                }
                    if (getSchedulesTime.get(i)[1] > getSchedulesTime.get(j)[0] && getSchedulesTime.get(i)[0] < getSchedulesTime.get(j)[1]) {
                         if(i==0) {
                         newString[j] = "C";
                    }
                }
                else{
                    if(i==0) {
                        newString[j] = "J";
                    }
                }
            }

        }
        if(res!="IMPOSSIBLE") {
            for (String s : newString) {
                res += s;
            }
        }
        return res;
    }

}

