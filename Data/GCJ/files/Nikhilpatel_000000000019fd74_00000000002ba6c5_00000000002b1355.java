import java.util.*;
import java.io.*;
public class Solution {
  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int size = in.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.


    ArrayList<Integer[][]> tests = new ArrayList<Integer[][]>(size);
    for (int k = 0; k < size; k++) {
      int r = in.nextInt();
      int c = in.nextInt();
      Integer[][] matrix = new Integer[r][c];
      for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
          //System.out.println(r + " " + c);
          matrix[i][j] = in.nextInt();
        }
      }
      tests.add(matrix);
    }

    ArrayList<Integer> scores = new ArrayList<Integer>(size);

    for (int i = 0; i < tests.size(); i++) {
      int interestTotal = 0;
      Integer[][] floor = tests.get(i);
      while (true) {
        //print(floor);
        int zeros = findZeros(floor);
        interestTotal += findRound(floor);
        ArrayList<Integer[]> remove = new ArrayList<Integer[]>();
        for (int r = 0; r < floor.length; r++) {
          for (int c = 0; c < floor[0].length; c++) {
            //System.out.println(r + " " + c);
            int[] arr = newFloor(floor, r, c);
            if (arr != null) {
              Integer[] what = Arrays.stream( arr ).boxed().toArray( Integer[]::new );
              remove.add(what);
            }
          }
        }
        for (Integer[] arr : remove) {
          floor[arr[0]][arr[1]] = 0;
        }
        if (zeros == findZeros(floor)) {
          break;
        }
        
      }
      //System.out.println("HERE");
      scores.add(interestTotal);
    }

    for (int i = 0; i < size; i++) {
        int j = i + 1;
        System.out.println("Case #" + j + ": " + scores.get(i));
    }
  }

  public static int findRound(Integer[][] floor) {
    int sum = 0;
    for (Integer[] i : floor) {
      for (Integer j : i) {
        sum += j;
      }
    }
    return sum;
  }

  public static int[] newFloor(Integer[][] floor, int r, int c) { 
    if (floor[r][c] == 0) return null;
    int left = left(floor, r, c);
    int right = right(floor, r, c);
    int up = up(floor, r, c);
    int down = down(floor, r, c);
    int nonZero = 0;
    if (left != 0) nonZero++;
    if (right != 0) nonZero++;
    if (up != 0) nonZero++;
    if (down != 0) nonZero++;
    double average = (1.0*left + right + up + down) / (1.0 * nonZero);
    //System.out.println(r + " " + c + " " + left + " " + right);
    if (average > floor[r][c]) {
      int[] remove = new int[2];
      remove[0] = r;
      remove[1] = c;
      return remove;
    }
    return null;
  }

  public static int up(Integer[][] floor, int r, int c) { 
    int level = floor[r][c];
    r--;
    while (r >= 0) {
      if (floor[r][c] != 0) return floor[r][c];
      r--;
    }
    return 0;
  }

  public static int down(Integer[][] floor, int r, int c) { 
    int level = floor[r][c];
    r++;
    while (r < floor.length) {
      if (floor[r][c] != 0) return floor[r][c];
      r++;
    }
    return 0;
  }

  public static int left(Integer[][] floor, int r, int c) { 
    int level = floor[r][c];
    c--;
    while (c >= 0) {
      if (floor[r][c] != 0) return floor[r][c];
      c--;
    }
    return 0;
  }

  public static int right(Integer[][] floor, int r, int c) { 
    int level = floor[r][c];
    c++;
    while (c < floor[0].length) {
      if (floor[r][c] != 0) return floor[r][c];
      c++;
    }
    return 0;
  }

  public static int findZeros(Integer[][] floor) {
    int zeros = 0;
    for (int i = 0; i < floor.length; i++) {
      for (int j = 0; j < floor[0].length; j++) {
        if (floor[i][j] == 0) zeros++;
      }
    }
    return zeros;
  }

  public static void print(Integer[][] floor) {
    for (int i = 0; i < floor.length; i++) {
      for (int j = 0; j < floor[0].length; j++) {
        System.out.print(floor[i][j] + " ");
      }
    }
    System.out.println(" ");
  }
}