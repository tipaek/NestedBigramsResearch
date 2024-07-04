import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import java.math.*;
import java.util.*;
import java.io.*;

public class Solution{
    static Scanner sc;
    public static void main(String[] args){
        sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int casenum = 1; casenum <= n; casenum++) {
            int size = sc.nextInt();
            int trace = sc.nextInt();
            int k = n+1; 
            
            HashMap map<Integer,List<Integer>> = new HashMap<>();
            List list = new ArrayList();
            int hashmapIndex = 0;
        // Loop to print rows 
            for (int i = 1; i <= n; i++) 
            { 
                int index = 0;
                // This loops runs only after 
                // first iteration of outer  
                // loop. It prints 
                // numbers from n to k 
                int temp = k; 
  
                while (temp <= n) 
                { 
                    list[index++] = temp; 
                    temp++; 
                } 
      
            // This loop prints numbers from 
            // 1 to k-1. 
            for (int j = 1; j < k; j++) 
                list[index++] = j
      
            map.put(hashmapIndex++, list);
            k--; 
        } 
        hashmapIndex--;
        checkTrace = 0;
        list size = n 
        for(int i = 0; i<hashmapIndex; i++ ){
            checkTrace += (map.get(test--)).get(lsit_size-1)
        }
            
        }
    }
    
    public static void createMatrix(int n, int t, int casenum){
        size = n;
        HashSet<String> set = new HashSet<>();
        backtrack("",0,0,t,set);
        if(result.size() == 0){
            System.out.println("Case #"+casenum+": IMPOSSIBLE");
        }
        else{
            System.out.println("Case #"+casenum+": POSSIBLE");
            String[] values = result.get(0).split(",");
            for(int i=0; i<size; i++){
                StringBuilder sb = new StringBuilder();
                for(int j=0; j<size; j++){
                    sb.append(values[i*size+j]);
                    sb.append(" ");
                }
                System.out.println(sb.toString());
            }
            
        }
    
    }
}