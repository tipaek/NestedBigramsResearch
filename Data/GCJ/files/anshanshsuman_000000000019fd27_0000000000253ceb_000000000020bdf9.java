public class time
{
	public int high;
	public int low;
}

package <missing>;

public class GlobalMembers
{
	public static int fre(time x, time[] t, String arr, char c, int j)
	{
		int ans = 2;
		for (int i = 0;i < j;i++)
		{
			if (arr[i].equals(c))
			{
			if (((x.low >= t[i].high) && (x.high > t[i].high)) || ((x.low < t[i].low) && (x.high <= t[i].low)))
			{
				ans = 1;
			}
			else
			{
				ans = 0;
				break;
			}
			}
		}
		if (ans == 2)
		{
			return 1;
		}
		else if (ans == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	public static int Main()
	{
		int a;
		int b;
		int j = 0;
		String s;
		a = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
		while ((a--) != 0)
		{
			s = "";
			b = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
			j++;
			String arr = new String(new char[b]);
			time[] x = tangible.Arrays.initializeWithDefaulttimeInstances(b);
			for (int i = 0;i < b;i++)
			{
				x[i].low = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
				x[i].high = Integer.parseInt(ConsoleInput.readToWhiteSpace(true));
			}
			for (int i = 0;i < b;i++)
			{
				if (fre(x[i], x, arr, 'C', b) != 0)
				{
					arr = tangible.StringFunctions.changeCharacter(arr, i, 'C');
				}
				else if (fre(x[i], x, arr, 'J', b))
				{
					arr = tangible.StringFunctions.changeCharacter(arr, i, 'J');
				}
				else
				{
					arr = tangible.StringFunctions.changeCharacter(arr, i, 'I');
				}
			}
			for (int i = 0;i < b;i++)
			{
				if (arr.charAt(i) == 'I')
				{
					s = "IMPOSSIBLE";
					break;
				}
				else
				{
					s = s + arr.charAt(i);
				}

			}
			s = s.substring(0, remove(s.iterator(), s.end(), ' ')) + s.substring(remove(s.iterator(), s.end(), ' ') + s.end());
			System.out.print("Case #");
			System.out.print(j);
			System.out.print(": ");
			System.out.print(s);
			System.out.print("\n");
		}
	}
}

//Helper class added by C++ to Java Converter:

package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2006 - 2020 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class provides the ability to initialize and delete array elements.
//----------------------------------------------------------------------------------------
public final class Arrays
{
	public static time[] initializeWithDefaulttimeInstances(int length)
	{
		time[] array = new time[length];
		for (int i = 0; i < length; i++)
		{
			array[i] = new time();
		}
		return array;
	}

	public static <T extends java.io.Closeable> void deleteArray(T[] array)
	{
		for (T element : array)
		{
			if (element != null)
				element.close();
		}
	}
}

//Helper class added by C++ to Java Converter:

package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2006 - 2020 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class provides the ability to replicate various classic C string functions
//	which don't have exact equivalents in the Java framework.
//----------------------------------------------------------------------------------------
public final class StringFunctions
{
	//------------------------------------------------------------------------------------
	//	This method allows replacing a single character in a string, to help convert
	//	C++ code where a single character in a character array is replaced.
	//------------------------------------------------------------------------------------
	public static String changeCharacter(String sourceString, int charIndex, char newChar)
	{
		return (charIndex > 0 ? sourceString.substring(0, charIndex) : "")
			+ Character.toString(newChar) + (charIndex < sourceString.length() - 1 ? sourceString.substring(charIndex + 1) : "");
	}

	//------------------------------------------------------------------------------------
	//	This method replicates the classic C string function 'isxdigit' (and 'iswxdigit').
	//------------------------------------------------------------------------------------
	public static boolean isXDigit(char character)
	{
		if (Character.isDigit(character))
			return true;
		else if ("ABCDEFabcdef".indexOf(character) > -1)
			return true;
		else
			return false;
	}

	//------------------------------------------------------------------------------------
	//	This method replicates the classic C string function 'strchr' (and 'wcschr').
	//------------------------------------------------------------------------------------
	public static String strChr(String stringToSearch, char charToFind)
	{
		int index = stringToSearch.indexOf(charToFind);
		if (index > -1)
			return stringToSearch.substring(index);
		else
			return null;
	}

	//------------------------------------------------------------------------------------
	//	This method replicates the classic C string function 'strrchr' (and 'wcsrchr').
	//------------------------------------------------------------------------------------
	public static String strRChr(String stringToSearch, char charToFind)
	{
		int index = stringToSearch.lastIndexOf(charToFind);
		if (index > -1)
			return stringToSearch.substring(index);
		else
			return null;
	}

	//------------------------------------------------------------------------------------
	//	This method replicates the classic C string function 'strstr' (and 'wcsstr').
	//------------------------------------------------------------------------------------
	public static String strStr(String stringToSearch, String stringToFind)
	{
		int index = stringToSearch.indexOf(stringToFind);
		if (index > -1)
			return stringToSearch.substring(index);
		else
			return null;
	}

	//------------------------------------------------------------------------------------
	//	This method replicates the classic C string function 'strtok' (and 'wcstok').
	//------------------------------------------------------------------------------------
	private static String activeString;
	private static int activePosition;
	public static String strTok(String stringToTokenize, String delimiters)
	{
		if (stringToTokenize != null)
		{
			activeString = stringToTokenize;
			activePosition = -1;
		}

		//the stringToTokenize was never set:
		if (activeString == null)
			return null;

		//all tokens have already been extracted:
		if (activePosition == activeString.length())
			return null;

		//bypass delimiters:
		activePosition++;
		while (activePosition < activeString.length() && delimiters.indexOf(activeString.charAt(activePosition)) > -1)
		{
			activePosition++;
		}

		//only delimiters were left, so return null:
		if (activePosition == activeString.length())
			return null;

		//get starting position of string to return:
		int startingPosition = activePosition;

		//read until next delimiter:
		do
		{
			activePosition++;
		} while (activePosition < activeString.length() && delimiters.indexOf(activeString.charAt(activePosition)) == -1);

		return activeString.substring(startingPosition, activePosition);
	}
}

//Helper class added by C++ to Java Converter:

package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2006 - 2020 Tangible Software Solutions, Inc.
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
