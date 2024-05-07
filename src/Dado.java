import java.util.Random;

import javax.swing.JOptionPane;

public class Dado {
    public static int Busqueda() {
        Random random = new Random();
        int dado = random.nextInt(100) + 1;
        if (dado >= 1 && dado <= 75) {
        	JOptionPane.showMessageDialog(null, "No has encontrado nada.");
            return 1;
        } else if (dado >= 76 && dado <= 90) {
        	JOptionPane.showMessageDialog(null, "Encontraste un botiquín.");
            return 2;
        } else if (dado >= 91 && dado <= 95) {
        	JOptionPane.showMessageDialog(null, "Encontraste una protección.");
            return 3;
        } else if (dado >= 96 && dado <= 100) {
        	JOptionPane.showMessageDialog(null, "Encontraste un arma.");
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
