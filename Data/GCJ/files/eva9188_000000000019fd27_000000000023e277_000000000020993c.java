private static int Main()
{
	int n;
	int i;
	int j;
	int k;
	int t;
	int f = 0;
	int m = 0;
	int l = 0;
	int[] r = new int[3];
	int[] c = new int[3];
	int[] s = new int[3];
	int x = 0;
	int z;
	int[][] a = new int[10][10];
	int[] b = new int[10];
	int[] d = new int[10];
	z = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
	for (i = 0;i < 10;i++)
	{
		b[i] = 0;
		s[i] = 0;
		r[i] = 0;
		c[i] = 0;
	}
	for (x = 0;x < z;x++)
	{
		l = 0;
	for (i = 0;i < 10;i++)
	{
		b[i] = 0;
	}
	n = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
	for (i = 0;i < n;i++)
	{
		for (j = 0;j < n;j++)
		{
			a[i][j] = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
			if (i == j)
			{
			s[x] = s[x] + a[i][j];
			}
		}
	}
for (k = 0;k < n;k++)
{
	for (i = 0;i < n;i++)
	{
		t = a[k][i];
		for (j = i;j < n;j++)
		{

			if (t == a[k][j])
			{
				b[l] = b[l] + 1;
			}
		}

		l++;
	}
}
	r[x] = b[0];
	for (i = 0;i < 10;i++)
	{
		if (r[x] < b[i])
		{
			r[x] = b[i];
		}
	}
	if (r[x] == 1)
	{
		r[x] = 0;
	}

	for (i = 0;i < 10;i++)
	{
		d[i] = 0;
	}
	l = 0;
	for (k = 0;k < n;k++)
	{
	for (i = 0;i < n;i++)
	{
		t = a[i][k];
		for (j = i;j < n;j++)
		{
			if (t == a[j][k])
			{
				d[l] = d[l] + 1;
			}
		}
		l++;
	}
	}
	c[x] = d[0];
	for (i = 0;i < 10;i++)
	{
		if (c[x] < d[i])
		{
			c[x] = d[i];
		}
	}
	if (c[x] == 1)
	{
	c[x] = 0;
	}
	}
	for (i = 0;i < 3;i++)
	{
	System.out.print("Case #");
	System.out.print(i + 1);
	System.out.print(": ");
	System.out.print(s[i]);
	System.out.print(" ");
	System.out.print(r[i]);
	System.out.print(" ");
	System.out.print(c[i]);
	System.out.print("\n");

	}
	return 0;
}

package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2006 - 2015 Tangible Software Solutions Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class provides the ability to convert basic C++ 'cin' behavior.
//----------------------------------------------------------------------------------------
public final class ConsoleInput
{
	private static boolean goodLastRead = false;
	public static boolean lastReadWasGood()
	{
		return goodLastRead;
	}

	public static String readToWhiteSpace(boolean skipLeadingWhiteSpace)
	{
		String input = "";
		char nextChar;
		while (Character.isWhitespace(nextChar = (char)System.in.read()))
		{
			//accumulate leading white space if skipLeadingWhiteSpace is false:
			if (!skipLeadingWhiteSpace)
			{
				input += nextChar;
			}
		}
		//the first non white space character:
		input += nextChar;

		//accumulate characters until white space is reached:
		while (!Character.isWhitespace(nextChar = (char)System.in.read()))
		{
			input += nextChar;
		}

		goodLastRead = input.length() > 0;
		return input;
	}

	public static String scanfRead()
	{
		return scanfRead(null, -1);
	}

	public static String scanfRead(String unwantedSequence)
	{
		return scanfRead(unwantedSequence, -1);
	}

	public static String scanfRead(String unwantedSequence, int maxFieldLength)
	{
		String input = "";

		char nextChar;
		if (unwantedSequence != null)
		{
			nextChar = '\0';
			for (int charIndex = 0; charIndex < unwantedSequence.length(); charIndex++)
			{
				if (Character.isWhitespace(unwantedSequence.charAt(charIndex)))
				{
					//ignore all subsequent white space:
					while (Character.isWhitespace(nextChar = (char)System.in.read()))
					{
					}
				}
				else
				{
					//ensure each character matches the expected character in the sequence:
					nextChar = (char)System.in.read();
					if (nextChar != unwantedSequence.charAt(charIndex))
						return null;
				}
			}

			input = (new Character(nextChar)).toString();
			if (maxFieldLength == 1)
				return input;
		}

		while (!Character.isWhitespace(nextChar = (char)System.in.read()))
		{
			input += nextChar;
			if (maxFieldLength == input.length())
				return input;
		}

		return input;
	}
}
