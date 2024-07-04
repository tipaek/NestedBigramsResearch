import java.util.Scanner;
class Demo{
    public static func(String s){
        StringBuffer sb=new StringBuffer();
        if(s[0]=='1'){
                sb.append("(1");
            }
            else{
                sb.append("0");
            }
        for(int i=1;i<s.length()-1;i++){
            if(s[i]=='1' && s[i-1]=='0'){
                sb.append("(1");
            }
            else if(s[i]=='1' && s[i+1]=='0'){
                sb.append("1)");
            }
            else if(s[i]=='0'){
                sb.append("0");
            }
        }
        return sb.toString();
    }
    Scanner sc=new Scanner(System.in);
    int n=sc.nextInt();
    for(int i=0;i<n;i++){
        String s=sc.next();
        System.out.println(func(s));
    }
}