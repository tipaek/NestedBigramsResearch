import java.util.Scanner;

public class Solution {

    public static int MAX_READS = 15;  5, 15, 150

    public static void main(String[] arg) {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        int B = input.nextInt();

        for (int ks = 1; ks = T; ks++) {
            String respuesta = solveCase(B, input);
            System.out.println(Case # + ks +   + respuesta);
            System.out.println(respuesta);
        }
        input.close();
    }

    private static String solveCase(int B, Scanner input) {
        String respuesta = ;
        int times = MAX_READS  B, time = 0;
        boolean firstTime = true;
        byte[][] numbers = new byte[times][B];
        byte[] first = new byte[B], complement = new byte[B], reversed = new byte[B], both = new byte[B];
        for (int i = 0; i  MAX_READS; i++) {
            int position = i % B;
            if (i % B == 0 && i  0) {
                time++;
            }
            if (i % B == 0 && firstTime && i  0) {
                for (int j = 0; j  B; j++) {
                    first[j] = numbers[time][j];
                }
                complement = calculateComplement(B, first);
                reversed = calculateReversed(B, first);
                both = calculateBoth(B, first);
                firstTime = false;
            }
            System.out.println(time  + time +  p + position);
            byte value = input.nextByte();
            numbers[time][position] = value;
        }
        respuesta = calculateSolution(B, times, first, complement, reversed, both, numbers);
        return respuesta;
    }

    private static byte[] calculateComplement(int B, byte[] first) {
        byte[] vector = new byte[B];
        for (int i = 0; i  B; i++) {
            vector[i] = (byte) ((byte) 1 - first[i]);
        }
        return vector;
    }

    private static byte[] calculateReversed(int B, byte[] first) {
        byte[] vector = new byte[B];
        for (int i = 0; i  B; i++) {
            int k = B - 1;
            vector[i] = first[k];
        }
        return vector;
    }

    private static byte[] calculateBoth(int B, byte[] first) {
        byte[] vector = new byte[B], vector2 = new byte[B];
        vector = calculateComplement(B, first);
        vector2 = calculateReversed(B, vector);
        return vector2;
    }

    private static String calculateSolution(int B, int times, byte[] first, byte[] complement, byte[] reversed, byte[] both, byte[][] numbers) {
        String mostRepited = ;
        if (B  100) {
            mostRepited = calculateMostRepited(B, times, numbers);
        }
        
        return mostRepited;
    }

    private static String calculateMostRepited(int B, int times, byte[][] numbers) {
        String selected = ;
        String listUniqueNumbers = ;
        for (int i = 0; i  times; i++) {
            String number = ;
            for (int j = 0; j  B; j++) {
                number = number + numbers[i][j];
            }
            System.out.println( + number);
            int pos = listUniqueNumbers.indexOf(number);
            if (pos  0)
                listUniqueNumbers += number + (1)-;
            else{
                int pos2 = listUniqueNumbers.indexOf(number + () + number.length();
                int pos3 = listUniqueNumbers.indexOf(), pos2);
                String sCount = listUniqueNumbers.substring(pos2 + 1, pos3);
                System.out.println(sCount  + sCount);
                int count = Integer.parseInt(sCount);
                String textToSearch = number + ( + count + );
                System.out.println(textToSearch  + textToSearch);
                String forReplace = number + ( + (count + 1) + );
                System.out.println(forReplace  + forReplace);
                listUniqueNumbers = listUniqueNumbers.replaceAll(textToSearch, forReplace);
            }
        }
        System.out.println(listUniqueNumbers  + listUniqueNumbers);
        String[] uniques = listUniqueNumbers.split(-);
        int topValue = 0;
        
        for (String unique  uniques) {
            System.out.println( + unique);
            int pos1 = unique.indexOf(();
            int pos2 = unique.indexOf(), pos1);
            String sCurrentValue = unique.substring(pos1 + 1, pos2);
            int currentValue = Integer.parseInt(sCurrentValue);
            if (currentValue  topValue) {
                topValue = currentValue; 
                selected = unique.substring(0, pos1);
            }
        }
        System.out.println( + selected);
        return selected;
    }
}
