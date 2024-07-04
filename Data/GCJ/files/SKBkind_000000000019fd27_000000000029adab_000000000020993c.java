import java.util.Scanner;

class Vestigium
{
	
	public static void main(String args[])
	{
		int t,n,col, rows;
		int[][] m;
		String[] q;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
		t=Integer.parseInt(line);
		for(int i=0;i<t;i++){//test cases
			line=br.readLine();
			n=Integer.parseInt(line);
			m=new int[n][n];
			q=new String[n];
			for(int j=0;j<n;j++){//lines
				line=br.readLine;
				q=line.split("\\s");
				for(int k=0; k<n;k++){//columns
					m[j][k]=Integer.parseInt(q[k]);
				}
				
			}
			col=0;
			rows=0;
			for(int j=0;j<n;j++){
				for(int k=0;k<n;k++){
					if(m[j][k]==m[j][n-k]){
						rows=rows+1;
						break;
					}
				}
				for(int k=0;k<n;k++){
					if(m[k][j]==m[n-k][j]){
						col=col+1;
						break;
					}
				}
				
				
				
			
			}
			System.out.println("Case #".t.": ".n." ".rows." ".col);
		}

		
	}
}