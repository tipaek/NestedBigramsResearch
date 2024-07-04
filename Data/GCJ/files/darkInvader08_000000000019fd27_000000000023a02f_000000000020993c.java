import java.io.*
class Vestigium
{
    public static void main(String args[])throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int T,N;
        T=Integer.parseInt(br.readLine());
        for(int i=0;i<T;i++)
        {
            int N=Integer.parseInt(br.readLine());
            int [][]M=new int[N][N];
            for(int j=0;j<N;j++)
            {
                for(int k=0;k<N;k++)
                {
                    M[i][j]=Integer.parseInt(br.readLine());
                }
            }
            int k=0,r=0,c=0;
            for(int j=0;j<N;j++)
            {
                for(int k=0;k<N;k++)
                {
                   int f;
                   for(int l=j;l<N;l++)
                   {
                       if(M[j][k]==M[j][l])
                       {
                           r++;
                           break;
                       }
                   }
                   if(j==k)
                   s+=M[j][j];
                }
            }
            for(int j=0;j<N;j++)
            {
                for(int k=0;k<N;k++)
                {
                   
                   for(int l=j;l<N;l++)
                   {
                       if(M[k][j]==M[l][j])
                       {
                           r++;
                           break;
                       }
                   }
                   
                }
            }
            System.out.println("Case #"+T+" "+s+" "+r+" "+c);
            
        }
        
    }
}