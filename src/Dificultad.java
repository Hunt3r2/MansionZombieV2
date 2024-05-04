import java.util.Scanner;
public class Dificultad {
    public static int Elegido() {
        Scanner scanner = new Scanner(System.in);
        int numerFuncional= -1;
        while (numerFuncional <= 0) {
            try {
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Hola usuario, vamos a elegir la dificultad de juego:");
                System.out.println("1. Fácil[Cantidad de habitaciones totales son 5]");
                System.out.println("2. Difícil [Cantidad de habitaciones totales son 10]");
                int dificultad = scanner.nextInt();
                if (dificultad == 1) {
                    numerFuncional++;
                    return 5;
                } else if (dificultad == 2) {
                    numerFuncional++;
                    return 10;
                } else {
                    System.out.println("ERROR DE ESCRITURA. Elije una opcion valida");
                }
            }
            catch(Exception ex) {
                System.out.println("ERROR DE ESCRITURA: Por favor usuario vuelve a escribir numerica ");
                scanner.nextLine();
            }
        }
        return numerFuncional;
    }
}
