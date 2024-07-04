import java.util.Scanner;
import java.util.Random
class Solution
{
  public static void main(String ar[])
  {
    int t=0,b=0,p=0;
    Scanner sc=new Scanner(System.in);
    t=sc.nextInt();
    Random rand=new Random();
    while(t--!=0)
    {
      b=sc.nextInt();    
      p=rand.nextInt(b);
      if(p==0)
      {
          p=p+1;
      }
      System.out.println(p);
    }
    
      
  }
}
