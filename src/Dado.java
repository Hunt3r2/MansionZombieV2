import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Clase que representa un dado.
 */
public class Dado {
    private Random random;

    /**
     * Constructor de la clase Dado.
     */
    public Dado() {
        this.random = new Random();
    }

    /**
     * Metodo estatico que simula una busqueda.
     * 
     * @return El resultado de la busqueda:
     *         1 si no se encontro nada,
     *         2 si se encontro un botiquin,
     *         3 si se encontro una proteccion,
     *         4 si se encontro un arma.
     */
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

    /**
     * Metodo estatico que simula la probabilidad de aparicion de un zombie.
     * 
     * @return El nivel de probabilidad de aparicion de un zombie:
     *         1 si baja,
     *         2 si media,
     *         3 si alta.
     */
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

    /**
     * Metodo para lanzar un dado con un rango especifico.
     * 
     * @param min El valor minimo del rango.
     * @param max El valor maximo del rango.
     * @return El resultado del lanzamiento del dado dentro del rango especificado.
     */
    public int lanzarDado(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
