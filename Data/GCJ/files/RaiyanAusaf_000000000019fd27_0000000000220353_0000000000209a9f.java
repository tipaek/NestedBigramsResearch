import java.util.*;

public static void main()
{
    Scanner obj = new Scanner(System.in);
    int numCases = sc.nextInt();
    for (int i = 0; i < numCases-1; i++) {
        String temp = sc.nextLine();
        String new = "";
        for (int j = 0; j < temp.length-1; j++) {
            if (temp.substring(j,j+1).equals("0"))
            {
                new+="0";
            }
            else {
                int iterations = 0;
                int k = j;
                while (temp.substring(k,k+1).equals("1")) {
                    iterations++;
                }
                new+="(";
                for (int z = 0; z <= iterations; z++) {
                    new+="1";
                }
                new+=")";
            }
        }
        System.out.println("Case #" + i+1 + ": " + new);
    }
}