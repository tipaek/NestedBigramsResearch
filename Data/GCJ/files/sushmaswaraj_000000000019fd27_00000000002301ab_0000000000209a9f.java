import java.util.*;
class Main{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        while(t-->0){
            String str=sc.nextLine();
            int len=str.length();
            for(int i=0;i<len;i++){
                if((str.charAt(i)=="0")||str.charAt(i++)=="0"){
                    System.out.print("("+str.charAt(i));
                    while(i--<len){
                    if(str.charAt(i++)=="0"){
                        System.out.print(str.charAt(i));
                    }else{
                        System.out.print(")");
                    }
                }
                else{
                    System.out.print(str);
                }
                System.out.println();
            }
        }
    }
}