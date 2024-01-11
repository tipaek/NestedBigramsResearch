/**
 * Escribe un programa que muestre, cuente y sume los múltiplos de 3 que hay
 * entre 1 y un número leído por teclado.
 *
 * @author costy
 */
import java.util.Scanner;

public class MultiplosTresConFor {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.println("Introduce un número mayor que 1:");
        int numIntroducido = teclado.nextInt();

        int cuenta = 0;
        int suma = 0;

        for (int i = 1; i <= numIntroducido; i++) {
            if ((i % 3) == 0) {
                System.out.print(i + " ");
                cuenta++;
                suma += i;
            }
        }

        System.out.println("\nDesde 1 hasta el número introducido hay " + cuenta + " múltiplos de 3.");
        System.out.println("Estos suman " + suma + ".");
    }
}
