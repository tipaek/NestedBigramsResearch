import java.util.*;
import java.io.*;

public class Solution {
	
	public static void main(String[] args)
	{
		Scanner in=new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		int T=in.nextInt();
		for(int i=1;i<=T;i++)
		{
			int N=in.nextInt();
			int[][] tmp=new int[N][N];
			int[][] tracerow=new int[N+1][N];
			int[][] tracecol=new int[N+1][N];
			int total_val=0;
			for(int j=0;j<N;j++)
			{
			    for(int k=0;k<N;k++)
			    {
			        tmp[j][k]=in.nextInt();
			        if(tracerow[j][tmp[j][k]]==0)
			            tracerow[j][tmp[j][k]]=1;
			        else
			            tracerow[j][N]=1;
			        if(tracecol[j][tmp[j][k]]==0)
			            tracecol[j][tmp[j][k]]=1;
			        else
			            tracecol[j][N]=1;
			        if(j==k)
			            total_val+=tmp[j][k];
			    }
			}
			int rowcnt=0,colcnt=0;
			for(int j=0;j<N;j++)
			{
			    if(tracecol[j][N]==1)
			        colcnt++;
			    if(tracerow[j][N]==1)
			        rowcnt++;
			}
			System.out.println("Case #" + i + ": " + total_val + " " +rowcnt+ " " +colcnt);
			
		}
	}