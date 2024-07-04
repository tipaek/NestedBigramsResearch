import java.util.*;

public class Charf 
{

			public static void peppurr(int x,int y,String str,int t)
			{
				
				int min=1111;
				
				char[] ch=str.toCharArray();
				if(x==0&&y==0)
				{
					System.out.print("0");
				}
				else
				{
					int i=0;
					for(i=0;i<str.length();i++)
					{
						if(x+y<=i)
						{
							if(i<min)
							{
								min=i;
							}
						}
						if(ch[i]=='S')
						{
							y--;
						}
						else if(ch[i]=='N')
						{
							y++;
						}
						else if(ch[i]=='E')
						{
							x++;
						}
						else
						{
							x--;
						}
					}
					if(x+y<=i)
					{
						if(i<min)
						{
							min=i;
						}
					}
					if(min==1111)
					{
						System.out.print("Case #"+t+": IMPOSSIBLE");
					}
					else
					{
						System.out.print("Case #"+t": "+min);
					}
				}
			}
		 public static void main(String args[])
			{
				Scanner sc=new Scanner(System.in);
				int test=sc.nextInt();
				int t=1;
				while(t<=test)
				{			
					int x=sc.nextInt();
					int y=sc.nextInt();
					String str=sc.next();
					peppurr(x,y,str,t);
					t++;
				}
				sc.close();
			}

		}