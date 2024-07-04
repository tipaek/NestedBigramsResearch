class Depth
{
    static int dep(String N) {
        int max=0;
        int curr_max=0;
        int l=N.length();
        for(int i=0;i<l;i++) {
            if(N.charAt(i)=='('){
              curr_max++;}
              if(curr_max>max){
                 max=curr_max;
               }
        else if(N.charAt(i)==')') {
            if (curr_max>0) {
                curr_max--;
            } else {
                 return -1;
            }
        }
    }
    if(curr_max != 0) {
         return -1;
    }
return max;
}
public static void main(String[] args) {
    String u= "( ((X)) (((Y))) )";
    System.out.println(dep(u));
}