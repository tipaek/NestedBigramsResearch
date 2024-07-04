import java.util.*;
class Solution{
    public static void main(String[] args)
    {
        Scanner sc=Scanner(System.in);
        int x=sc.nextInt();
        for(int i=0;i<x;i++)
        {
            int b=i+1;
            String s=sc.nextLine();
            String y=Solution.result(s);
            System.out.println("Case #"+b+":"+" "+y);
        }
    }
    static String result(String c)
    {
        String s=null;
        for(int j=0;j<c.length())
        {
           int x=Integer.parseInt().(c.charAt(j));
           string y=null;
           int i=j;
           while(c.charAt(i)==char(i+1))
           {
               y=y+char(i);
               i++;
               j++;
           }
           for(int k=0;k<x;k++)
           {
               s=s+'(';
           }
           s=s+y;
           for(int m=0;m<x;m++)
           {
               s=s+')';
           }
           
        }
        return s;
    }
}