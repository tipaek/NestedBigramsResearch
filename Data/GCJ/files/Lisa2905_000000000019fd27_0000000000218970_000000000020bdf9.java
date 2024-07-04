import java.util.*;
class vestigium
{
    public static void main()
    {
        Scanner in=new Scanner(System.in);
        int T=in.nextInt();
        if(T<1||T>100)
        System.exit(0);
        for(int o=1;o<=T;o++)
        {
            int N=in.nextInt();
            int a[][]=new int[N][2];
            for(int u=0;u<N;u++)
            {
                for(int r=0;r<2;r++)
                a[u][r]=in.nextInt();
            }