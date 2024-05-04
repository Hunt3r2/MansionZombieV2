import java.util.Random;

public class Dado {
    public static int Busqueda() {
        Random random = new Random();
        int dado = random.nextInt(100) + 1;
        if (dado >= 1 && dado <= 75) {
            System.out.println("No has encontrado nada.");
            return 1;
        } else if (dado >= 76 && dado <= 90) {
            System.out.println("Encontraste un botiquín.");
            return 2;
        } else if (dado >= 91 && dado <= 95) {
            System.out.println("Encontraste una protección.");
            return 3;
        } else if (dado >= 96 && dado <= 100) {
            System.out.println("Encontraste un arma.");
            return 4;
        }
        return -1;
    }

    public static int ProbablidadAparicionZombie() {
        Random random = new Random();
        int dado = random.nextInt(100) + 1;
        if (dado >= 1 && dado <= 40) {
            return 1;
        } else if (dado >= 41 && dado <= 80) {
            return 2;
        } else if (dado >= 81 && dado <= 100) {
            return 3;
        }
        return -1;
    }
}
