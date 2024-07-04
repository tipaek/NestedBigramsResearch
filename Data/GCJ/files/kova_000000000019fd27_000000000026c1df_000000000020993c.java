import java.util.*;
class Solution
{public static void main(String[] args){
    int k,r,c,case;
    Scanner s=new Scanner(System.in);
    int T=s.nextInt();
    for(int i=1;i<=T;i++)
    {  k=0;
    r=0;c=0;
        case=i;
        int N=s.nextInt();
        int M[][]=new int[N][N];
        for(int a=0;a<N;a++)
        {
            for(int b=0;b<N;b++)
            {
                M[a][b]=s.nextInt();
            }
        }
        for(int a=0;a<N;a++)
        {
            for(int b=a;b<=a;b++)
            {
                k=k+M[a][b];
            }
        }
        for(int a=0;a<N;a++)
        {
            int first=M[a][0];
            boolean allsame=true;
            
            for(int b=1;b<M[a].length;b++)
            {
                if(M[a][b]!=first)
                {
                    allsame=false;
                    break;
                    
                }
            }
            if(allsame)
            r++;
        }
        for(int b=0;b<N;b++)
        {
            int first=M[0][b];
            boolean allsame=true;
            for(int a=1;a<M[b].length;b++)
            {
                if(M[b][a]!=first)
                {
                    allsame=false;
                    break;
                }
            }
            if(allsame)
            c++;
        }
        System.out.println("Case #"+case+":"+ k+ r+ c);
        
    }
}
}