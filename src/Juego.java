import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Juego extends JDialog {
    private int CantidadDeHabitaciones;
    private int HabitacionesPasadas;
    private int zombies;
    private int RealizadasBusquedas;
    private Superviviente superviviente;
    private JButton fightButton;
    private JButton buscarButton;
    private JButton avanzarButton;
    private JButton curarButton;
    private JTextArea infoTextArea; // Movido al nivel de la clase

    public Juego(int cantidadDeHabitaciones, JFrame juego) {
    	this.CantidadDeHabitaciones = cantidadDeHabitaciones;
        this.HabitacionesPasadas = 0;
        this.zombies = 1; // Inicializado en 1
        this.RealizadasBusquedas = 0;
        this.superviviente = new Superviviente();

        setTitle("Juego de Supervivencia");
        setSize(670, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        infoTextArea = new JTextArea(); // Usar JTextArea en lugar de JLabel
        infoTextArea.setEditable(false); // Deshabilitar la edición del JTextArea
        infoTextArea.setBounds(10, 30, 634, 60);
        panel.add(infoTextArea);

        fightButton = new JButton("Combatir contra un zombie");
        fightButton.setBounds(20, 209, 207, 23);
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                luchar(zombies);
            }
        });
        panel.add(fightButton);

        buscarButton = new JButton("Buscar en la habitación");
        buscarButton.setBounds(20, 140, 207, 23);
        buscarButton.setEnabled(false);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RealizadasBusquedas < 3 && zombies == 0) {
                    Busquedashabitaciones();
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes buscar más en esta habitación.");
                }
            }
        });
        panel.add(buscarButton);

        avanzarButton = new JButton("Avanzar a la siguiente habitación");
        avanzarButton.setBounds(20, 243, 207, 23);
        avanzarButton.setEnabled(false);
        avanzarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasarHabitacion();
            }
        });
        panel.add(avanzarButton);

        curarButton = new JButton("Usar botiquín");
        curarButton.setBounds(20, 175, 207, 23);
        curarButton.setEnabled(false);
        curarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usarBotiquin();
            }
        });
        panel.add(curarButton);
        setVisible(true);
    }

    public void iniciarJuego() {
    }

    void cargarPartida() {
        // Lógica para cargar partida guardada
    }

    public void verHistorico() {
        // Lógica para ver histórico de partidas
    }

    public void ejecutarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (HabitacionesPasadas < CantidadDeHabitaciones) {
            try {
                if (!superviviente.Muerte()) {
                    HabitacionesPasadas++; // Se incrementa el contador al entrar a una habitación
                    actualizarInfoLabel(); // Se actualiza la información en la interfaz
                    if (HabitacionesPasadas < (CantidadDeHabitaciones)) {
                        if (zombies > 0) {
                            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("Estadisticas del jugador (" + superviviente.getVida() + ") ["
                                    + barrita(superviviente.getVida()) + "] hp, daño 4, cantidad de armas "
                                    + superviviente.getArma() + " y las protecciones " + superviviente.getProteccion()
                                    + "botiquin [0 = false] [1= true] = " + superviviente.getBotiquines()
                                    + " .Estas en la habitación " + HabitacionesPasadas);
                            System.out.println("1. Luchar contra " + zombies + " zombies");
                            int Accion = scanner.nextInt();
                            luchar(Accion);
                        } else if (zombies <= 0) {
                            opciones();
                            int Accion = scanner.nextInt();
                            FuncionAccion(Accion);
                        }
                    }
                } else {
                    System.out.println("Has muerto usuario");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println("¿Quieres reiniciar el juego[1]?");
                    int Accion = scanner.nextInt();
                    if (Accion == 1) {
                        reiniciarJuego();
                    } else {
                        System.out.println("Fin del juego");
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Solo se aceptan números: ");
                scanner.next();
            }
        }
    }

    public void luchar(int Accion) {
        switch (Accion) {
            case 1:
                while (zombies > 0) {
                    Zombie zombie = new Zombie(HabitacionesPasadas);
                    while (zombie.getVida() > 0 && superviviente.getVida() > 0) {
                        superviviente.recibirAtaque(zombie.getAtaque());
                        if (superviviente.getVida() <= 0) {
                            JOptionPane.showMessageDialog(null, "¡Has muerto!");
                            reiniciarJuego();
                            return;
                        }
                        zombie.recibirAtaque(superviviente.getAtaque() + superviviente.getArma());
                    }
                    System.out.println("Has eliminado al zombie\n");
                    zombies--;
                }
                // Después de derrotar a todos los zombies, habilitar los botones nuevamente
                buscarButton.setEnabled(true);
                avanzarButton.setEnabled(true);
                curarButton.setEnabled(true);
                break;
        }
        actualizarInfoLabel(); // Actualizar la información en la interfaz
    }

    public void FuncionAccion(int Accion) {
        switch (Accion) {
        case 1:
            RealizadasBusquedas++;
            Busquedashabitaciones();
            break;
        case 2:
            pasarHabitacion();
            break;
        case 3:
            usarBotiquin();
            break;
        }
    }

    public void reiniciarJuego() {
        superviviente.reiniciarStats();
        this.HabitacionesPasadas = 0; // Reiniciar el contador de habitaciones
        this.zombies = 0; // Reiniciar el contador de zombies
        this.RealizadasBusquedas = 0;
        actualizarInfoLabel(); // Actualizar la información en la interfaz
    }

    public void usarBotiquin() {
        if (superviviente.getBotiquines()) {
            superviviente.usarBotiquin();
            JOptionPane.showMessageDialog(null, "Has usado un botiquín. Vida actual: " + superviviente.getVida());
        } else {
            JOptionPane.showMessageDialog(null, "No tienes botiquines para usar.");
        }
    }

    public void opciones() {
        if (HabitacionesPasadas < (CantidadDeHabitaciones - 1)) {
            if (superviviente.getBotiquines()) {
                JOptionPane.showMessageDialog(null,
                        "Estadisticas del jugador (" + superviviente.getVida() + ") ["
                                + barrita(superviviente.getVida()) + "] hp, daño 4, cantidad de armas "
                                + superviviente.getArma() + " y las protecciones " + superviviente.getProteccion()
                                + "botiquin [0 = false] [1= true] = " + superviviente.getBotiquines()
                                + " .Estas en la habitación " + HabitacionesPasadas
                                + "\n1. Buscar en una habitación\n2. Avanzar a la siguiente habitación \n3. Te puedes curar");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Estadisticas del jugador (" + superviviente.getVida() + ") ["
                                + barrita(superviviente.getVida()) + "] hp, daño 4, cantidad de armas "
                                + superviviente.getArma() + " y las protecciones " + superviviente.getProteccion()
                                + "botiquin [0 = false] [1= true] = " + superviviente.getBotiquines()
                                + " .Estas en la habitación " + HabitacionesPasadas
                                + "\n1. Buscar en una habitación\n2. Avanzar a la siguiente habitación");
            }
        } else if (HabitacionesPasadas < (CantidadDeHabitaciones)) {
            JOptionPane.showMessageDialog(null,
                    "Estadisticas del jugador (" + superviviente.getVida() + ") ["
                            + barrita(superviviente.getVida()) + "] hp, daño 4, cantidad de armas "
                            + superviviente.getArma() + " y las protecciones " + superviviente.getProteccion()
                            + "botiquin [0 = false][1 = true] = " + superviviente.getBotiquines()
                            + " .Estas en la habitación " + HabitacionesPasadas + "\n1. Buscar en una habitación\n2. Final del juego");
        }
    }

    public void Busquedashabitaciones() {
        if (RealizadasBusquedas <= 3) {
            int suerte = Dado.Busqueda();
            switch (suerte) {
            case 1:
                hacerRuido();
                break;
            case 2:
                if (superviviente.getBotiquines()) {
                    JOptionPane.showMessageDialog(null, "Has encontrado un botiquin pero ya tienes uno ");
                } else {
                    superviviente.aumentarBotiquin();
                    int Cura = JOptionPane.showConfirmDialog(null, "Superviviente quiere usar el botiquín?");
                    if (Cura == JOptionPane.YES_OPTION) {
                        superviviente.usarBotiquin();
                    }
                }
                break;

            case 3:
                superviviente.aumentarProteccion();
                break;
            case 4:
                superviviente.aumentarArma();
                break;

            }
            RealizadasBusquedas++;
            JOptionPane.showMessageDialog(null,
                    "Cantidad de busquedas realizadas son: " + RealizadasBusquedas);
        } else {
            JOptionPane.showMessageDialog(null, "Has realizado 3 busquedas no se puede realizar mas busquedas");
        }
    }

    public void hacerRuido() {
        int suerte2 = Dado.ProbablidadAparicionZombie();
        if (suerte2 == 1) {
            JOptionPane.showMessageDialog(null, "Qué suerte jugador, no has atraído ningún zombie");
        } else if (suerte2 == 2) {
            JOptionPane.showMessageDialog(null, "Mala suerte jugador, has atraído a un zombie");
            zombies++;
            // Deshabilitar los otros botones cuando entran zombies
            buscarButton.setEnabled(false);
            avanzarButton.setEnabled(false);
            curarButton.setEnabled(false);
        } else if (suerte2 == 3) {
            JOptionPane.showMessageDialog(null, "Mala suerte jugador, has atraído a ¡DOS ZOMBIES!");
            zombies += 2;
            // Deshabilitar los otros botones cuando entran zombies
            buscarButton.setEnabled(false);
            avanzarButton.setEnabled(false);
            curarButton.setEnabled(false);
        }
    }

    public String barrita(int vida) {
        int vidaMaxima = 20;
        StringBuilder barra = new StringBuilder();
        for (int i = 0; i < vida; i++) {
            barra.append("*");
        }
        for (int i = vida; i < vidaMaxima; i++) {
            barra.append("-");
        }
        return barra.toString();
    }

    public void pasarHabitacion() {
        HabitacionesPasadas++; // Incrementar el número de habitación
        RealizadasBusquedas = 0; // Reiniciar el contador de búsquedas
        actualizarInfoLabel(); // Actualizar la información en la interfaz
        if (HabitacionesPasadas == CantidadDeHabitaciones) {
            JOptionPane.showMessageDialog(null, "¡Felicitaciones! Has escapado.");
            dispose();
        } else {
            // Restablecer el número de zombies a 1
            zombies = 1; 
            // Deshabilitar todos los botones excepto el de combatir contra el zombie
            buscarButton.setEnabled(false);
            avanzarButton.setEnabled(false);
            curarButton.setEnabled(false);
            fightButton.setEnabled(true); // Habilitar el botón de combatir
            JOptionPane.showMessageDialog(null, "Has avanzado a la siguiente habitación.");
        }
        actualizarInfoLabel(); // Actualizar la información en la interfaz
    }


    public void actualizarInfoLabel() {
        // Actualizar el texto del JLabel con la información actualizada
        String infoText = "Habitaciones jugadas: " + HabitacionesPasadas + "/" + CantidadDeHabitaciones
                + " | Busquedas realizadas: " + RealizadasBusquedas + " | Zombies: " + zombies
                + "| Vida del jugador: " + barrita(superviviente.getVida());
        infoTextArea.setText(infoText);
    }
}
