
import java.util.ArrayList;
import java.util.Scanner;

public class Solution{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < testCases; i++){
            int activities = sc.nextInt();
            sc.nextLine();
            boolean[] C = new boolean[1441];
            boolean[] J = new boolean[1441];
            String whoDoesWhat = "";

            for (int j = 0; j < activities; j++){
                boolean cIsFree = true;
                boolean jIsFree = true;
                int start = sc.nextInt();
                int end = sc.nextInt();


                for (int k = start; k < end; k++){
                    if (C[k]){
                        cIsFree = false;
                        break;
                    }
                }
                if (cIsFree){
                    whoDoesWhat 
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Solution{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCases=0;
        try {
            testCases = sc.nextInt();
        } catch (InputMismatchException e){
            sc.nextLine();
        }

        for (int i = 0; i < testCases; i++){
            int activities = sc.nextInt();
            sc.nextLine();
            boolean[] C = new boolean[1441000];
            boolean[] J = new boolean[1441000];
            String whoDoesWhat = "";

            for (int j = 0; j < activities; j++){
                boolean cIsFree = true;
                boolean jIsFree = true;
                int start = sc.nextInt();
                int end = sc.nextInt();


                for (int k = start; k < end; k++){
                    if (C[k]){
                        cIsFree = false;
                        break;
                    }
                }
                if (cIsFree){
                    whoDoesWhat += "C";
                    for (int k = start; k < end; k++){
                        C[k] = true;
                    }

                }else{
                    for (int k = start; k < end; k++){
                        if (J[k]){
                            jIsFree = false;
                            break;
                        }
                    }

                    if (jIsFree){
                        whoDoesWhat += "J";
                        for (int k = start; k < end; k++){
                            J[k] = true;
                        }

                    }
                    else{
                        whoDoesWhat = "IMPOSSIBLE";
                        break;
                    }

                }



            }
            System.out.println("Case #" + (i+1) + ": " + whoDoesWhat);


        }


    }
}
+= "C";
                    for (int k = start; k < end; k++){
                        C[k] = true;
                    }

                }else{
                    for (int k = start; k < end; k++){
                        if (J[k]){
                            jIsFree = false;
                            break;
                        }
                    }

                    if (jIsFree){
                        whoDoesWhat += "J";
                        for (int k = start; k < end; k++){
                            J[k] = true;
                        }

                    }
                    else{
                        whoDoesWhat = "IMPOSSIBLE";
                        break;
                    }

                }



            }
            System.out.println("Case #" + (i+1) + ": " + whoDoesWhat);


        }


    }
}
