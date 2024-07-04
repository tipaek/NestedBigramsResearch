class Solution1{

  public static void solvef(String s,int case){
    String result = "";

    char ch = s.charAt(0);
    if(ch=='1') result = result + "(" + ch;
    else result = result + ch;

    for(int i=1;i<s.length();++i){
      char temp = s.charAt(i);
      if(temp == ch) result = result+temp;
      else if(temp != ch && temp == '0') result = result+")"+ch;
      else if(temp != ch && temp == '1')result = result+"("+ch;
      ch = temp;
    }
      System.out.println("Case #" + case + ": " + result);
  }

  public static void main(String[] args){
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt();
    for(int i=0;i<=t;++i){
      String s = Integer.toString(in.nextInt());
      solvef(s,i);
    }
    in.close();

  }
}
