import java.util.*;
public Solution{
    public static void main(String []args){
      Scanner s = new Scanner(System.in); 
      int T= s.nextInt();  
      for(int t=0;t<T;t++){
          String p=s.nextLine();
          StringBuilder sb= new StringBuilder();
          int depth=0;
          for(char c: s.toCharArray()){
              int dp=Integer.valueOf(c);
             while(depth<dp){
                 sb.append("(");
                 depth++;
             } 
             while(depth>dp){
                 sb.append(")");
             }
             sb.append(c);
          }
          System.out.println("Case #"+t+": "+sb.toString());
      }
    }
}