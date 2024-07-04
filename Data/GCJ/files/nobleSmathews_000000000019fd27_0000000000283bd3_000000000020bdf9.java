

import java.util.Set

public class Solution {
    private static Scanner sc;
    static int tn=1;
    public static void main (String[] args){
        sc= new Scanner (System.in);
        int t sc.nextInt();
        sc.nextLine();
        while(t-->0)
        {
            solve();
        }
    }
 
    private static void solve(){
        int n = sc.nextInt();
        int[][]mat=new int[n][2];
        int[][]matSorted=mat.clone();
        StringBuilder sb = new StringBuilder();
        char person='J';
        Map<int[],Integer>map=new HashMap<>();
        Stack<int[]> JStack= new Stack<>();
        Stack<int[]> CStack= new Stack<>();
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[i].length;j++){
                mat[i][j]=sc.nextInt();
            }
            map.put(mat[i],i);
        }
        Arrays.sort(matSorted,new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                return a[0]-b[0];
            }
        });
        //46
        for(int i=0;i<matSorted.length;i++){
            chars[map.get(matSorted[i])]=person;
            if(i<matSorted.length-1 && doesOverlap(matSorted[i],matSorted[i+1]){
            if(person=='J'){
            JStack.push(matSorted[i]);
            person=getPerson(person);
            if(!CStack.isEmpty() && doesOverlap(Cstack.peek(),matSorted[i+1])){
                impossible=true;
                break;
            }
            }else{
            CStack.push(matSorted[i]);
            person=getPerson(person);
            
            if(!JStack.isEmpty() && doesOverlap(Jstack.peek(),matSorted[i+1])){    
                impossible=true;
                break;
            }
            }
            }else {
            if(person=='J'){    
                JStack.push(mat[i]);
            }
            else{
                CStack.push(mat[i]);
                }
            }
        }

        System.out.println(Arrays.deepToString(mat));
        
    }
    private static char getPerson(char p){
        return p=='J'?'C':'J';
    }
    private static boolean doesOverlap(int[] a,int[] b){
        return a[1]>b[0];
    }
    
}
