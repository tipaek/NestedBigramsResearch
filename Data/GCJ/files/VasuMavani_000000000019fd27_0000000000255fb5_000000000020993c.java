import java.util.*;

public class vest
{
	public static void main(String args[]){
		Scanner in=new Scanner(System.in);
		int test=in.nextInt();
		int case=1;
		while(test-->0){
			int trace=0;
			int c1=0,c2=0;
			int n=in.nextInt();
			int a[][]=new int[n][n];
			Set<Integer> s = new HashSet<Integer>();
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					a[i][j]=in.nextInt();
				}
			}
			for(int i=0;i<n;i++){
				trace=trace+a[i][i];
			}
			
			for (int i=0;i<n;i++){
              for (int j=0;j<n;j++){
                 int CheckedNumber=a[i][j];
                 if(!s.add(CheckedNumber)){
					 c1++;
                     break;
                 }
              }
			  s.clear();
           }
		   
		   	for (int i=0;i<n;i++){
              for (int j=0;j<n;j++){
                 int CheckedNumber=a[j][i];
                 if(!s.add(CheckedNumber)){
					 c2++;
                     break;
                 }
              }
			  s.clear();
           }
		   
		   System.out.println("Case #"+c+": "+trace+" "+c1+" "+c2);
			case++;
			
		}
	}
} 