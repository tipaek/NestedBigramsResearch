import java.util.*;
public class Solution {
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int T = input.nextInt();
		int a = input.nextInt(), b = input.nextInt();
		//input.nextLine();
		int diff = 5;
		cases: (int t = 1; t<=T; t++)
		{
			int maxLeft = diff*2, maxRight = diff*2, maxTop = diff*2, maxBottom = diff*2;
			
			for(int i = -10; i<=10; i++)
			{
				for(int j = -10; j <= 10; j++)
				{
					System.out.println(i+" "+j);
					//System.out.flush();
					String verdict = input.nextLine();
					
					if(verdict.equals("CENTER"))
					{
						continue cases;
					}
				}
			}
		}
	}
}