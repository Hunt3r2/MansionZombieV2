import java.io.Serializable;

import javax.swing.JOptionPane;

public class Superviviente implements Serializable {
    private int vida;
    private int ataque;
    private boolean botiquines;
    private int proteccion;
    private int arma;

    public Superviviente() {
        this.vida = 20;
        this.ataque = 4;
        this.botiquines = false;
        this.proteccion = 0;
        this.arma = 0;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public boolean getBotiquines() {
        return botiquines;
    }

    public int getProteccion() {
        return proteccion;
    }

    public int getArma() {
        return arma;
    }

    public void recibirAtaque(int cantidad) {
        vida -= cantidad;
    }

    public void usarBotiquin() {
        if (vida < 20) {
            vida = Math.min(vida + 4, 20);
            botiquines = false;
        } else {
        	JOptionPane.showMessageDialog(null, "Botiquin mal gastado.");
            botiquines = false;
        }
    }

    public boolean Muerte() {
        return vida <= 0;
    }

    public void aumentarProteccion() {
        proteccion++;
    }

    public void aumentarArma() {
        arma++;
    }

    public void aumentarBotiquin() {
        botiquines = true;
    }
    public void reiniciarStats() {
        vida = 20;
        ataque = 4;
        botiquines = false;
        proteccion = 0;
        arma = 0;
    }

	public void setVida(int vida) {
		this.vida = vida;
	}
    
    
}
