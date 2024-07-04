import java.util.*;
 public static void sol(){
        int s = may.next();
        int[][] ar = new int[s][s];
        int k=0;
        for(int i=0;i<ar.length;i++){
            for(int j=0;j<ar.length;j++){
              ar[i][j]=may.nextInt();
              if(i==j)
                k+=ar[i][j];
            }
        }
        int r =get(ar);
        int c =ger(ar);
        
        system.out.println("case #"+(a++)+":"+k+""+r+""+c);
    
    private static int getR(int[][]ar){
        int res=0;
        for(int i=0;i<ar.length;i++){
            set <Integer> set=new Hashset<>();
            for(int j=0;j<ar.length;j++){
                if(set.contains(ar[i][j]){
                    res++;
                    break;
                }
                set.add(ar[i][j]);
            }
        }
        return res;
    }
    private static int getC(int[][]ar){
        int res=0;
        for(int i=0;i<ar.length;i++){
            set <Integer> set=new Hashset<>();
            for(int j=0;j<ar.length;j++){
                if(set.contains(ar[i][j]){
                    res++;;
                    break;
                }
                set.add(ar[i][j]);
            }
        }
        return res;
    }
    }

 



public class Solve{
    private static Scanner may;
    static int a=1;
    public  static void main(Strig[] args){
        may=  new Scanner(System.in);
        int  b=may.nextInt();
        may.nextLine();
        while(t-->0){
            sol();
            
        }
    }

    private static void sol() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

     