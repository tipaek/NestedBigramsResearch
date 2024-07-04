import java.util.*;

class Solution{

    Scanner s = new Scanner(System.in);

    public static void main(String args[]){
    int T = s.nextInt();
    while(T--!=0){
        int x=s.nextInt();
        task(x);
    }//end while
    }//end main

    boolean static Jworking = false;
    int Js = 0;
    int Je = 0;
    boolean static Cworking = false;
    static int count = 1;
    int Cs = 0;
    int Ce = 0;
    String a="";
    public static void task(int row){
        int ar[][] = new int[row][2];
        
        for(int i=0; i<row; i++){
            ar[i][0] = s.nextInt();
            ar[i][1] = s.nextInt();
        }//for end
        
        
        for(int i=0; i<row; i++){
            if(Jworking==false && Js<=ar[i][1] && Je<=ar[i][0]){
                Jworking=true;
                Js=ar[i][0];
                Je=ar[i][1];
                a=a+"J";
            }else if (Cworking==false && Cs<=ar[i][1] && Ce<=ar[i][0]){
                Cworking=true;
                Cs=ar[i][0];
                Ce=ar[i][1];
                a=a+"C";
            }else{
                a= "IMPOSSIBLE";
                break;
            }   
            
        }
        
        System.out.println("Case #"+count+": "+a);

        count++;        
        
    }//end task
    
}