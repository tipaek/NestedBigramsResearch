import java.util.*;
class Solution{

static boolean trace(int r,int c){
for(int i=1;i<=r;i++)
{
	if((i*r)==c)
		return true;
			
}
return false;
}
static void makeMatrix(int r,int c)
{
	int matrix[][]=new int[r][r];
	int m=c/r;
	for(int i=0;i<r;i++)
	{
		for(int j=0;j<r;j++)
		{
			if(i==j){
			matrix[i][j]=m;}
		
		}
		
			
	}
	for(i =0;i<r;i++){
		for(j=0;j<r;j++){
			if(i==0&&i!=j&&matrix[i][j-1]!=(m-1))
			{
				matrix[i][j]=m-1;
			}
			else{
				matrix[i][j]=m+1;
			}
			if(i==1&&(i!=j)&&matrix[i][j-1]!=(m-1))
			{
				matrix[i][j]=m+1;
			}
			else
				matrix[i][j]=m-1;
			if(i==2 &&(i!=j)&&(matrix[i][j-1]!=(m-1)))
			{
				matrix[i][j]=m-1;
			}
			else
				matrix[i][j]=m+1;
			
	}
	for(int a=0;a<r;a++)
	{
		for(int b=0;b<r;b++)
		{
			System.out.print(matrix[a][b]+"");
		}
		System.out.println();
	}
}
public static void main(String args[]) {
Scanner s = new Scanner(System.in);
int t;
int ar[]=new int[2];
t=s.nextInt();
for (int a=0;a<t;a++)
{
	for(int j=0;j<=1;j++)
		ar[j]=s.nextInt();
	if(trace(ar[0],ar[1]))
	{
		System.out.println("Case #"+(a+1)+": POSSIBLE");
		makeMatrix(ar[0],ar[1]);
	}
	else
	{
		System.out.println("Case #"+(a+1)+": IMPOSSIBLE");
	}
	
}
}
}