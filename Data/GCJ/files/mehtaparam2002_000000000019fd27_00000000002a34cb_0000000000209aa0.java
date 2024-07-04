import java.util.Scanner;
package codejam2019.qualification;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
public class Indicium {

  static void printLatin(int n)
  {
    int k = n+1;
    for (int i = 1; i <= n; i++)
    {
        int temp = k;
        while (temp <= n)
        {
            System.out.print(temp + " ");
            temp++;
        }
        for (int j = 1; j < k; j++)
            System.out.print(j + " ");
        k--;
        System.out.println();
    }
  }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        int noOfTestcases = sc.nextInt();
            for (int i = 1; i <= noOfTestcases; i++) {
                int counter = 0;
                String moves3;
                int moves = sc.nextInt();
                int moves2 = sc.nextInt();
                for (int y = 1; y<=moves; y++)
                {
                    if(y*moves == moves2)
                    {
                        counter += 1;
                    }
                }
                if (counter != 1)
                {
                    moves3 += "IMPOSSIBLE";
                }
                moves3 += "POSSIBLE";
                if (moves3.equals("POSSIBLE"))
                {
                    printLatin(moves);
                }

                System.out.println("Case #" + i + ": " + moves3);
            }
    }

}
