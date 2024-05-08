import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    private JTextField vidaTextField;
    private JTextField habitacionesTextField;
    private JTextField busquedasTextField;
    private JTextField zombiesTextField;
    private JTextField textFieldArmaPB;

    public Juego(int cantidadDeHabitaciones, JFrame juego) {
        this.CantidadDeHabitaciones = cantidadDeHabitaciones;
        this.HabitacionesPasadas = 0;
        this.zombies = 1;
        this.RealizadasBusquedas = 0;
        this.superviviente = new Superviviente();

        setTitle("Juego de Supervivencia");
        setSize(670, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        vidaTextField = new JTextField();
        vidaTextField.setEditable(false);
        vidaTextField.setBounds(10, 30, 200, 20);
        panel.add(vidaTextField);

        habitacionesTextField = new JTextField();
        habitacionesTextField.setEditable(false);
        habitacionesTextField.setBounds(220, 30, 200, 20);
        panel.add(habitacionesTextField);

        busquedasTextField = new JTextField();
        busquedasTextField.setEditable(false);
        busquedasTextField.setBounds(10, 58, 200, 20);
        panel.add(busquedasTextField);

        zombiesTextField = new JTextField();
        zombiesTextField.setEditable(false);
        zombiesTextField.setBounds(220, 58, 200, 20);
        panel.add(zombiesTextField);

        fightButton = new JButton("Combatir contra un zombie");
        fightButton.setBounds(20, 209, 207, 23);
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                luchar();
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
        
        textFieldArmaPB = new JTextField();
        textFieldArmaPB.setEditable(false);
        textFieldArmaPB.setBounds(10, 89, 225, 20);
        panel.add(textFieldArmaPB);
        
        JButton btnGuardarPartida = new JButton("Guardar Partida");
        btnGuardarPartida.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		guardarPartida();
        	}
        });
        btnGuardarPartida.setBounds(483, 327, 161, 23);
        panel.add(btnGuardarPartida);
        setVisible(true);

        actualizarInfoFields(); // Actualizar los campos de texto con la información inicial
    }

    public void iniciarJuego() {
    }

    public void luchar() {
        // Generar atributos aleatorios para el zombie
    	int vidaZombie = (int) (Math.random() * 2) + 2 + (HabitacionesPasadas - 1);
    	int ataqueZombie = (int) (Math.random() * 3) + 3 + (HabitacionesPasadas - 1);

        // Turno del superviviente
        int ataqueSuperviviente = superviviente.getAtaque() + superviviente.getArma();
        vidaZombie -= ataqueSuperviviente;

        // Verificar si el zombie ha sido derrotado
        if (vidaZombie <= 0) {
            JOptionPane.showMessageDialog(null, "¡Has eliminado al zombie!");
            zombies--; // Reducir el número de zombies en la habitación
        } else {
            // Turno del zombie solo si el superviviente sigue vivo
            int ataqueZombieFinal = (int) (Math.random() * ataqueZombie) + 1;
            superviviente.recibirAtaque(ataqueZombieFinal);
            if (superviviente.getVida() <= 0) {
                JOptionPane.showMessageDialog(null, "¡Has sido derrotado por el zombie!");
                reiniciarJuego();
                return; // Salir del método si el superviviente muere
            } else {
                // Mostrar el daño hecho por el zombie
                JOptionPane.showMessageDialog(null, "El zombie te ha quitado " + ataqueZombieFinal + " puntos de vida.");
            }
        }
        buscarButton.setEnabled(true);
        avanzarButton.setEnabled(true);
        curarButton.setEnabled(true);
        fightButton.setEnabled(false); // Habilitar el botón de combatir

        // Actualizar la información en la interfaz
        actualizarInfoFields();
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
        actualizarInfoFields(); // Actualizar la información en la interfaz
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
        actualizarInfoFields();
    }

    public void hacerRuido() {
        int suerte2 = Dado.ProbablidadAparicionZombie();
        if (suerte2 == 1) {
            JOptionPane.showMessageDialog(null, "Qué suerte jugador, no has atraído ningún zombie");
        } else if (suerte2 == 2) {
            JOptionPane.showMessageDialog(null, "Mala suerte jugador, has atraído a un zombie");
            zombies++;
            // Habilitar el botón de combatir
            fightButton.setEnabled(true);
            // Deshabilitar los otros botones cuando entran zombies
            buscarButton.setEnabled(false);
            avanzarButton.setEnabled(false);
            curarButton.setEnabled(false);
        } else if (suerte2 == 3) {
            JOptionPane.showMessageDialog(null, "Mala suerte jugador, has atraído a ¡DOS ZOMBIES!");
            zombies += 2;
            // Habilitar el botón de combatir
            fightButton.setEnabled(true);
            // Deshabilitar los otros botones cuando entran zombies
            buscarButton.setEnabled(false);
            avanzarButton.setEnabled(false);
            curarButton.setEnabled(false);
        }
        actualizarInfoFields();
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
        actualizarInfoFields(); // Actualizar la información en la interfaz
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
        actualizarInfoFields(); // Actualizar la información en la interfaz
    }


    public void actualizarInfoFields() {
        // Actualizar los JTextField con la información actualizada
        vidaTextField.setText("Vida: " + superviviente.getVida());
        habitacionesTextField.setText("Habitaciones: " + HabitacionesPasadas + "/" + CantidadDeHabitaciones);
        busquedasTextField.setText("Busquedas: " + RealizadasBusquedas);
        zombiesTextField.setText("Zombies: " + zombies);
        
        // Agregar información sobre botiquín, arma y protección
        String infoExtra = " | Arma: " + superviviente.getArma() 
                        + " | Protección: " + superviviente.getProteccion() 
                        + " | Botiquín: " + (superviviente.getBotiquines() ? "Sí" : "No");
        textFieldArmaPB.setText(textFieldArmaPB.getText() + infoExtra);
    }
    
    public void guardarPartida() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("partida.dat"))) {
            // Guardar los datos necesarios
            outputStream.writeInt(CantidadDeHabitaciones);
            outputStream.writeInt(HabitacionesPasadas);
            outputStream.writeInt(zombies);
            outputStream.writeInt(RealizadasBusquedas);
            outputStream.writeObject(superviviente); // Guardar el objeto superviviente
            JOptionPane.showMessageDialog(null, "Partida guardada correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la partida: " + e.getMessage());
        }
    }

    public static Juego cargarPartida(Juego juegoExistente) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("partida.dat"))) {
            // Cargar los datos guardados
            int cantidadDeHabitaciones = inputStream.readInt();
            int habitacionesPasadas = inputStream.readInt();
            int zombies = inputStream.readInt();
            int realizadasBusquedas = inputStream.readInt();
            Superviviente superviviente = (Superviviente) inputStream.readObject(); // Cargar el objeto superviviente

            // Actualizar los datos del juego existente con los datos cargados
            juegoExistente.setHabitacionesPasadas(habitacionesPasadas);
            juegoExistente.zombies = zombies;
            juegoExistente.RealizadasBusquedas = realizadasBusquedas;
            juegoExistente.superviviente = superviviente;

            JOptionPane.showMessageDialog(null, "Partida cargada correctamente.");
            return juegoExistente; // Devolver la instancia actualizada del juego
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la partida: " + e.getMessage());
            return null; // Devolver null si hay un error
        }
    }



    
    public void setHabitacionesPasadas(int habitacionesPasadas) {
        this.HabitacionesPasadas = habitacionesPasadas;
    }



	public void verHistorico() {
		// TODO Auto-generated method stub
		
	}
}
