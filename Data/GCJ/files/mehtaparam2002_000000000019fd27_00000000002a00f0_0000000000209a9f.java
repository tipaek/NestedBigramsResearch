import java.util.Scanner;

public class NestingDepth {

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        int noOfTestcases = sc.nextInt();
            for (int i = 1; i <= noOfTestcases; i++)
            {
                String model
                int counter = 0
                int x = sc.nextInt();
                String s = Integer.toString(x)
                for(int y = 0; y<s.lenth(); y++)
                {
                  int q = int(s.charAt(y))
                  if (q>counter){
                    counter = q
                    for (int z = 0; z < q; z ++)
                    {
                      model += '('
                    }
                }
                System.out.println("Case #" + i + ": " + model );
            }
    }

}
