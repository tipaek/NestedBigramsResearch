import java.util.*;
import java.io.*;
    public class Solution {
      public static void main(String[] args) {
      static char[] complimentString(char[] c){
          for (int i=0;i<c.length;i++) {
          c[i] = c[i] == '0' ? '1' : '0';
          }
        return c; 
      }
      
      static char[] reversalString(char[] c){
          char[] newChar=new Char[c.length];
          for (int i=c.length()-1;i>=0;i--) {
          newChar[c.length-1-i]=c[i];
          }
        return newChar; 
      }
          
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        
        public static void main(String[]args){
        String[] tb = scanner.nextLine().split(" ");

        int t = Integer.parseInt(tb[0]);
        int b = Integer.parseInt(tb[1]);
        for(int i=0;i<t;i++){
                char[] array = new char[b];
                for(int j=0;j<b;j++){
                    array[b]=scanner.next();
                }
                
                if(i%10==1){
                int num=new Random().nextInt(4 + 1)  + 1;
                    if(num==1||num==3){
                        array=complimentString(array);
                    }else if(num==2||num==3){
                        array=reversalString(array);
                    }
                }
                
                int queryNum=scanner.nextInt();
                
                System.out.println( Arrays.binarySearch(array,queryNum));
                
                for(char c: array){System.out.print(c);}
                if(scanner.nextLine()=="N"){break;}
        }
      }
    }