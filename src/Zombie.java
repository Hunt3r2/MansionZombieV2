import java.util.Random;

/**
 * Clase que representa a un zombie en el juego.
 */
public class Zombie {
    private int vida;
    private int ataque;

    /**
     * Constructor de la clase Zombie.
     */
    public Zombie() {
        Random random = new Random();
        // Se establece la vida del zombie como un valor aleatorio entre 3 y 4.
        this.vida = random.nextInt(2) + 3;
        // Se establece el ataque del zombie como un valor aleatorio entre 3 y 4.
        this.ataque = random.nextInt(2) + 3;
    }

    /**
     * Obtiene la cantidad de vida del zombie.
     * @return La cantidad de vida del zombie.
     */
    public int getVida() {
        return vida;
    }

    /**
     * Obtiene la cantidad de ataque del zombie.
     * @return La cantidad de ataque del zombie.
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Reduce la vida del zombie al recibir un ataque.
     * @param cantidad La cantidad de daño recibida.
     */
    public void recibirAtaque(int cantidad) {
        vida -= cantidad;
    }
}
