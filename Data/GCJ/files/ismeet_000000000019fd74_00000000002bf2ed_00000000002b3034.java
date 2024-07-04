import java.util.*;
class problem
{
    public static void main(String a[])
    {
    int T=0,N=0;
    
    Scanner st =new Scanner(System.in);
    T = st.nextInt();
    N = st.nextInt();
    int P[] = new int[N];
    for(int i = 0;i<N ; i++)
    {
        P[i] = st.nextLine();
    }
    string str= P[0];
    for(int i =0;i<N ; i++)
    {
       if(P[i].charAt(0) == '*')
       {
         System.out.println("*");  
       }else
       {
           System.out.println("*");
       }
       }
        
    }
    
    
}
}