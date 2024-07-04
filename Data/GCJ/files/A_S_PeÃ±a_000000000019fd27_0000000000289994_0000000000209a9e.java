import java.util.*;


public class Solution {
    
    static Scanner SCNR = new Scanner(System.in);
    
    static String method1(int B) {
        
        int [] arr = new int [B];
        int i, j;
        int match1 = -1;
        int match2 = -1;
        int dif1 = -1;
        int dif2 = -1;
        int q = 0;
        int temp;
        
        for (i = 0; i < B/2; ++i) {
            ++q;
            if (q > 0 && q % 10 == 1) {
                
                if (match1 > -1) {
                    System.out.print("%d", match1);
                    temp = SCNR.nextInt();
                    ++q;
                    
                    if (arr[match1] != temp) {
                        for (j = 0; j < i; ++j) {
                            arr[j] = ^arr[j];
                            arr[B-j] = ^arr[B-j];
                        }
                    }
                    
                }
                
                if (dif1 > -1) {
                    System.out.print("%d", dif1);
                    temp = SCNR.nextInt();
                    ++q;
                    
                    if (arr[dif1] != temp) {
                        for (j = 0; j < i; ++j) {
                            temp = arr[j];
                            arr[j] = arr[B-j];
                            arr[B-j] = temp;
                        }
                    }
                    
                }
                
                if (q % 2 == 1) {
                    System.out.print("%d", dif1);
                    temp = SCNR.nextInt();
                }
                
                
            }
            
            System.out.print("%d", i);
            arr[i] = SCNR.nextInt();
            
            System.out.print("%d", B - i);
            arr[B - i] = SCNR.nextInt();
            
            if (match1 < 0 && arr[i] == arr[B-i]) {
                match1 = i;
                match2 = B - i;
            }
            
            else if (dif1 < 0 && arr[i] != arr[B-i]) {
                dif1 = i;
                dif2 = B - i;
            }
            
            ++q;
            ++q;
            
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (i = 0; i < B; ++i) {
            sb.append(arr[i]);
        }

        
        return sb.toString();
    }
    
    
    public static void main (String [] args) {
        
        
        int T = SCNR.nextInt();
        int B;
        int i;
        String num
        
        for (i = 1; i <= T; ++i) {
            W = SCNR.nextInt();
            num = method1(W);
            System.out.printf("Case #%d: %s\n", i, num);
        }
        
        SCNR.close();
    }
}