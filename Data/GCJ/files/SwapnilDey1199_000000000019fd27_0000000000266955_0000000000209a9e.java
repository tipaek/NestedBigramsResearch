import java.util.*;
public class Solution{
    public static void main(String[]args)throws IOException{
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int b = sc.nextInt();
        while(t-->0){
            if(b==10){
                StringBuffer sb = new StringBuffer();
                for(int i = 1;i<=b;i++){
                    System.out.println(i);
                    System.out.flush();
                    String s = sc.next();
                    sb.insert(i-1,s)
                }
            }
            System.out.println(sb);
        }
    }
}