import java.io.Serializable;

import javax.swing.JOptionPane;

/**
 * Clase que representa al superviviente.
 */
public class Superviviente implements Serializable {
    private int vida;
    private int ataque;
    private boolean botiquines;
    private int proteccion;
    private int arma;

    /**
     * Constructor de la clase Superviviente. Inicializa los atributos con valores predeterminados.
     */
    public Superviviente() {
        this.vida = 20;
        this.ataque = 4;
        this.botiquines = false;
        this.proteccion = 0;
        this.arma = 0;
    }

    /**
     * Obtiene la cantidad de vida del superviviente.
     * @return La cantidad de vida del superviviente.
     */
    public int getVida() {
        return vida;
    }

    /**
     * Obtiene el valor de ataque del superviviente.
     * @return El valor de ataque del superviviente.
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Comprueba si el superviviente tiene botiquines.
     * @return true si el superviviente tiene botiquines, false si no tiene.
     */
    public boolean getBotiquines() {
        return botiquines;
    }

    /**
     * Obtiene el nivel de proteccion del superviviente.
     * @return El nivel de proteccion del superviviente.
     */
    public int getProteccion() {
        return proteccion;
    }

    /**
     * Obtiene el nivel de arma del superviviente.
     * @return El nivel de arma del superviviente.
     */
    public int getArma() {
        return arma;
    }

    /**
     * Reduce la vida del superviviente al recibir un ataque.
     * @param cantidad La cantidad de daño recibida.
     */
    public void recibirAtaque(int cantidad) {
        vida -= cantidad;
    }

    /**
     * Utiliza un botiquin para aumentar la vida del superviviente.
     */
    public void usarBotiquin() {
        if (vida < 20) {
            vida = Math.min(vida + 4, 20);
            botiquines = false;
        } else {
        	JOptionPane.showMessageDialog(null, "Botiquin mal gastado.");
            botiquines = false;
        }
    }

    /**
     * Comprueba si el superviviente ha muerto.
     * @return true si el superviviente ha muerto, false en caso contrario.
     */
    public boolean Muerte() {
        return vida <= 0;
    }

    /**
     * Aumenta el nivel de protección del superviviente.
     */
    public void aumentarProteccion() {
        proteccion++;
    }

    /**
     * Aumenta el nivel de arma del superviviente.
     */
    public void aumentarArma() {
        arma++;
    }

    /**
     * Establece que el superviviente tiene un botiquín.
     */
    public void aumentarBotiquin() {
        botiquines = true;
    }

    /**
     * Reinicia los atributos del superviviente a sus valores predeterminados.
     */
    public void reiniciarStats() {
        vida = 20;
        ataque = 4;
        botiquines = false;
        proteccion = 0;
        arma = 0;
    }

    /**
     * Establece la vida del superviviente.
     * @param vida La nueva cantidad de vida del superviviente.
     */
	public void setVida(int vida) {
		this.vida = vida;
	}
    
    
}
