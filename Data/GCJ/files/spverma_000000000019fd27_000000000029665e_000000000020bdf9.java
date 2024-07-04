// Java code to illustrate remove() method 
import java.util.*; 

public class Solution { 
	public static void main(String args[]) 
	{ 
		sc = new Scanner(System.in);
      int t = sc.nextLine();
      sc.nectLine();
      while(t-->0)
      {
        solve();
      }
	} 
  private static void solve() {
    int n = s.nextInt();
    int[][] mat = new int[n][2];
    int[][] mats = mat.clone();
    StringBuilder sb = new StringBuilder();
    char person = 'J';
    char[] chars = new char[n];
    Stack<int[]> js = new Stack<>();
    Stack<int[]> cs = new Stack<>();
    boolean impos = false;
    Map<int[] , Integer > map = new HashMap<>();
    
    
    for(int i=0;i<mat.length;i++) {
      for(int j=0;j<mat[i].length();j++) {
        mat[i][j]=sc.nextInt();
        
      }
      map.put(mat[i],i);
    }
    Arrays.sort(mats, new Comparator<int[]>() {
      @Override
      public int compare(int[] a,int[] b) {
        return a[0]-b[0];
      }
    });
    for(int i=0;i<mats.length;i++) {
      chars[map.get(mats[i])]=person;
      if(i< mats.length-1 && dover(mats[i], mats[i+1])) {
        if(person == 'J') {
          js.push(mats[i]);
          person = getp(person);
          /*if(!cs.isEmpty() && dover(mats[i], cs.peek())) {
            impos=true;
          
          break;*/
          
          if(!cs.isEmpty() && dover( cs.peek(),mats[i+1] )) {
            impos=true;
            break;
        }
          
          }
        } else {
          cs.push(mats[i]);
          person = getp(person);
        if(!js.isEmpty() && dover( js.peek() , mats[i+1])) {
            impos=true;
            break;
        }
          /*if(!cs.isEmpty() && dover(mats[i], js.peek())) {
            impos=true;
            break;*/
          }
        //}
      }
      else {
        
        
        if(person == 'J')
        {
          js.push(mats[i]);
        } else {
          cs.push(mats[i]);
        }
      }
      
      //
    }
    System.out.println("Case #" + (tn++) + ": " + (impos ? "IMPOSSIBLE" :  new String(chars)));
  }
  private static char getp(char p) {
    return p == 'J' ? 'C' : 'J';
  }
  private static boolean dover(int[] a, int[] b)
  {
    return a[1]>b[0];
  }
} 
/*class Pair {
  int first;
  int second;
  
  public Pair(int first, int second) {
    this.first = first;
    this.second = second;
  }
}*/
