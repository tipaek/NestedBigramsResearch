import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
public class Solution
{
	public static void main(String args[]) throws IOException
	{
		InputStream inputStream=System.in;
		OutputStream outputStream=System.out;
		FastReader in=new FastReader(inputStream);
		PrintWriter out=new PrintWriter(outputStream);
		int t=in.nextInt();
		Parenting solver=new Parenting();
		for(int i=1;i<=t;i++)
			solver.solve(i,in,out);
		out.close();

	}

	static class Parenting
	{
		public void solve(int t,FastReader s,PrintWriter w)
		{

			int n=s.nextInt();
			int[] start=new int[n];
			int[] end=new int[n];
			int ce=0,je=0,cs=0,js=0,flag=0;
			String res="";
			for(int i=0;i<n;i++)
			{
				start[i]=s.nextInt();
				end[i]=s.nextInt();
			}
			for(int i=0;i<n;i++)
			{
				if(i==0) 
				{	
					res+="J";
					je=end[0];
					js=start[0];	
				}
				else
				{
					char ch=res.charAt(i-1);
					if(start[i]>end[i-1])
					{
						if(ch=='C')
						{
							res+='C';
							ce=end[i];
							cs=start[i];
						}
						else
						{
							res+='J';
							je=end[i];
							js=start[i];
						}
					}
					else if(start[i]==end[i-1])
					{
						if(ch=='C')
						{
							res+='C';
							ce=end[i];
						}
						else
						{
							res+='J';
							je=end[i];
						}
					}
					else
					{
						if(start[i]<end[i-1]&&start[i]>=start[i-1])
						{
							if(ch=='C')
							{
								if(je<=start[i]||js>=end[i])
								{
									res+="J";
									js=start[i];
									je=end[i];
								}
								else
								{
									flag=1;
									break;
								}
							}
							else
							{
								if(ce<=start[i]||cs>=end[i])
								{
									res+="C";
									cs=start[i];
									ce=end[i];
								}
								else
								{
									flag=1;
									break;
								}
							}
						}
						else if(start[i]<start[i-1])
						{
							if(end[i]<=end[i-1])
							{
								if(ch=='C'&&(js>=end[i]||je<=start[i]))
								{
									res+='J';
									js=start[i];
									je=end[i];
								}
								else
								{
									if(cs>=end[i]||ce<=start[i])
									{
										res+='C';
										cs=start[i];
										ce=end[i];
									}
									else
									{
										flag=1;
										break;
									}
								}
							}
							else
							{
								if(ch=='C')
								{
									if(js>=end[i]||je<=start[i])
									{
										res+='J';
										js=start[i];
										je=end[i];
									}
									else
									{
										flag=1;
										break;
									}
								}
								else
								{
									if(cs>=end[i]||ce<=start[i])
									{
										res+='J';
										cs=start[i];
										ce=end[i];
									}
									else
									{
										flag=1;
										break;
									}
								}
							}
						}
					}
				}
			}
			if(flag==1)
				w.println("Case #"+t+": IMPOSSIBLE");
			else
				w.println("Case #"+t+": "+res);
		}

	}
