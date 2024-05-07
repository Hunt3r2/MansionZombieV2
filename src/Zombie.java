import java.util.Random;
public class Zombie {
    private int vida;
    private int ataque;

    public Zombie(int cantidadDeHabitaciones) {
        Random random = new Random();
        this.vida = random.nextInt(2) + 2;
        this.ataque = random.nextInt(2) + 2;
    }
    public int getVida() {
        return vida;
    }
    public int getAtaque() {
        return ataque;
    }
    public void recibirAtaque(int cantidad) {
        vida -= cantidad;
    }
}

