    /******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.*;
public class Main { 
      
    public static void main(String [] args)
    { 
        
        Scanner kb=new Scanner (System.in);
        int  h=kb.nextInt();
        for(int k=0;k<h;k++)
        {int w[]=new int[2];
        for (int g=0;g<n;g++){
            
            w[g]=kb.nextInt();
        }
        int n=w[0];
        int p=w[i];
        int a[]=new int [n*n] ;
        int b [][]=new int[n][n];
        int k = n+1; 
      int co=0;
        for (int i = 1; i <= n; i++) 
        { 
          int temp = k; 
  
            while (temp <= n) 
            { 
                a[co]=(temp );
                co++;
                temp++; 
            } 
            for (int j = 1; j < k; j++) 
                a[co]=j; 
                co++;
      
            k--; 
        } 
            for(int i=0;i<n;i++)

        {

            for(int j=0;j<n;j++)

            {

                if(count==a.length) break;

            b[i][j]=a[count];

            
            count++;

            }

        }int sum=0;
    for (int i=0i<n;i++)
    {
        
        for (int c=0;c<n;c++)
        {
            if(r==c){
                sum+=b[r][c];
            }
            
        }
    }

    if(sum==p){
    System.out.println("Case #"+(k+1)+": POSSIBLE");
    for (int d=0;d<n;d++)
    {
        for (int e=0;e<n;e++){
            System.out.print(b[d][e]);
            
        }
        System.out.println();
        
    }
    }
        }
        else {
            
    System.out.println("Case #"+(k+1)+": IMPOSSIBILE");
            
        }
    }  
     
} 
  