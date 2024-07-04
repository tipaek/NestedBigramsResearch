import java.io*;
class GFG{
    public static void
    print pascal(int n)
{
    for(int line =1;line<=n;line++)
    {
        int c =1 
        for (inti =1 ;i<=line;i++)
        {
              system.out.print(c+"")
              c=c*(line-i)/i;
              system.out.print();
            
        }
            
        system.out.print(c+"")
        c=c*(line-i)/i;
    system.out.print();
 }
}
public static void main (string[]args){
    int n =10;
    print pascal(n);
    
}
}
