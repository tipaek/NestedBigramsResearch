import java.util.*;
import java.io.*;
public class Solution {
      public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int testNum =  in.nextInt();
        for(int i = 1; i <= testNum; i++){
            int act = in.nextInt();
            Time[] arr = new Time[act];
            for(int j = 0; j < act; j++){
                int start = in.nextInt();
                int end = in.nextInt();
                arr[j] = new Time(start, end);
            }
            arr.sort();
            int c = arr[0].e;
            int j = arr[1].e;
            int turn = 0; // 0 c 1 j
            String result = "CJ";
            boolean possible = true;
            for(int j = 2; j < act; j++){
                int start = arr[j].s;
                if(start < c || start < j){
                    possible = false;
                    break;
                }
                else{
                    char add = turn ? 'J' : 'C';
                    result += add;
                }
            }
            if(possible){
                System.out.println("Case #" + i + ": " result);
            }
            else{
                System.out.println("Case #" + i + ": IMPOSSIBLE");
            }
        }
    }
    
    public class Time {
        public int s;
        public int e;
        public Time(int s, int e){
            this.s = s;
            this.e = e;
        }
        public int compareTo(Time time) {
            if(this.s == time.s){
                return this.e - time.e;
            }
            else{
                return this.s - time.s;
            }
        }
    }
}