import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;

public class {

public static void main(String args[]){
    Scanner in = new Scanner(System.in);
    
    HashMap<Integer, String> al = new HashMap<>(); //check cols
    
    
    //read num test cases
    int caseCount = 0;
    int numCases = Integer.parseInt(in.nextLine());
    while((numCases-caseCount)>0){
        caseCount++;
        int k,r,c = 0; //0 if natural Latin square.
    //read size of given matrix(n)
    int n = Integer.parseInt(in.nextLine());
        //read rowCount lines
    int rowCount = Integer.parseInt(in.nextLine());
    
        //FIND TRACE
        //FIND R, C
    
    //end of row to beginning
    for(;rowCount>0;rowCount--){
        HashSet<Integer> rowCheck = new HashSet<>();
        String rowStr = in.nextLine();
        al.put(rowCount, rowStr); //TO CHECK COL DUPLICATES
        
        boolean enter = false; //
        for(int col=n;col>0;col--){
            
            int el = Integer.parseInt(rowStr.substring(2*col-2,2*col-1));
            
            if(col==rowCount) { 
                //add to k
                k += el;
            }
            if((!enter) && (rowCheck.contains(el))){
                enter = true; //entered
                r++;
            }   
                rowCheck.add(el);
            
        } //start col
        
    } //start row
    
    
    for(int i =n;i>0;i--){
        HashSet<Integer> finalCheck = new HashSet<>(); //new for each i 
        for(int w=n;w>0;w--){
            Integer re = Integer.parseInt(al.get(w).substring(2*i-2, 2*i-1));
            if(finalCheck.contains(re)){
                c++;
                break;
            }
            finalCheck.add(re); //if not already present
        }
    }
    //c is now correct.
    
    
    
    
    System.out.println("Case #"+caseCount+": "+k+" "+r+" "+c);
    //PRINT: Case #x: k(trace) r(repeated ct) c(repeated ct)
    } //end while test cases
    
} //main

}