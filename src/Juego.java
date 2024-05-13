import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import javax.swing.ImageIcon;

public class Juego extends JDialog {
    private int CantidadDeHabitaciones;
    private int HabitacionesPasadas;
    private int zombies;
    private int RealizadasBusquedas;
    private Superviviente superviviente;
    private Zombie zombie;
    private JButton fightButton;
    private JButton buscarButton;
    private JButton avanzarButton;
    private JButton curarButton;
    private JTextField vidaTextField;
    private JTextField habitacionesTextField;
    private JTextField busquedasTextField;
    private JTextField zombiesTextField;
    private JTextField textFieldArmaPB;
    private Historico historico;
    String resultadoJuego;


    public Juego(JFrame juego, int cantidadDeHabitaciones)  {
    	super(juego, false);
    	setResizable(false);
        this.CantidadDeHabitaciones = cantidadDeHabitaciones;
        this.HabitacionesPasadas = 0;
        this.zombies = 1;
        this.RealizadasBusquedas = 0;
        this.superviviente = new Superviviente();
        this.zombie = new Zombie();
        Combate combate = new Combate(null, this, superviviente, zombie);
        historico = new Historico();
        if(superviviente.getVida() <= 0) {
            dispose();
        }

        setTitle("Mansión Zombie");
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

        Juego esteJuego = this; // Guardar una referencia al objeto Juego
        
        fightButton = new JButton("Combatir contra un zombie");
        fightButton.setBounds(20, 209, 207, 23);
        fightButton.setEnabled(true);
        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(superviviente.getVida() <= 0) {
                    dispose();
                }
                // Aquí se crea una nueva instancia de Combate pasando los parámetros necesarios
            	Combate combate = new Combate(juego, esteJuego, superviviente, zombie);                
            	combate.mostrar(superviviente.getVida(), superviviente.getArma(), zombies, 0);
                buscarButton.setEnabled(true);
                avanzarButton.setEnabled(true);
                curarButton.setEnabled(true);
                fightButton.setEnabled(false);
                
            }
        });
        panel.add(fightButton);

        buscarButton = new JButton("Buscar en la habitación");
        buscarButton.setBounds(20, 140, 207, 23);
        buscarButton.setEnabled(false);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RealizadasBusquedas < 3) {
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
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Juego.class.getResource("/imagenes/fondo1.jpeg")));
        lblNewLabel.setBounds(0, 0, 654, 361);
        panel.add(lblNewLabel);
        setVisible(true);

        actualizarInfoFields(); // Actualizar los campos de texto con la información inicial
    }

    public void iniciarJuego() {
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
                    int cura = JOptionPane.showConfirmDialog(null, "Superviviente quiere usar el botiquín?");
                    if (cura == JOptionPane.YES_OPTION) {
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

    public void pasarHabitacion() {
        HabitacionesPasadas++; // Incrementar el número de habitación
        RealizadasBusquedas = 0; // Reiniciar el contador de búsquedas
        actualizarInfoFields(); // Actualizar la información en la interfaz
        if (HabitacionesPasadas == CantidadDeHabitaciones) {
            JOptionPane.showMessageDialog(null, "¡Felicitaciones! Has escapado.");
            setResultado(true);
            guardarEnHistorico(obtenerDificultad(), HabitacionesPasadas, RealizadasBusquedas, zombies, superviviente.getVida(), superviviente.getArma(), superviviente.getBotiquines(), superviviente.getProteccion());
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
        textFieldArmaPB.setText(infoExtra);
    }
    
    public void onSupervivienteMuerto() {
        // Cerrar la ventana de juego cuando el superviviente muere en Combate
        dispose();
    }
    
    public void actualizarVidaSuperviviente(int nuevaVida) {
        superviviente.setVida(nuevaVida); // Actualizar la vida del superviviente
        actualizarInfoFields(); // Actualizar los campos de texto en la interfaz
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
    
    public Superviviente getSuperviviente() {
        return superviviente;
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
    
    public void actualizarZombies(int zombies) {
        this.zombies = zombies;
    }

	public void verHistorico() {
		
		String nombreArchivo = "historico.txt";
		historico.cargarPartidasDesdeArchivo();
        // Verificar si el archivo existe
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
        	
            JOptionPane.showMessageDialog(null, "El archivo de historial aún no existe.");
            return;
        }

        // Si el archivo existe, puedes abrirlo para mostrar su contenido
        Historico historicoDialog = new Historico();
        historicoDialog.setVisible(true);
		
	}
	
	public void guardarEnHistorico(String dificultad, int habitacion, int busquedas, int zombies, int vida, int arma, boolean botiquin, int proteccion) {
        historico.guardarEnHistorico(getResultado(), dificultad, habitacion, vida, botiquin, arma, proteccion);
    }

	private String getResultado() {
		return resultadoJuego;
	}
	
	public void setResultado(boolean resultado) {
	    if (resultado) {
	        resultadoJuego = "Ganaste";
	    } else {
	        resultadoJuego = "Perdiste";
	        dispose();
	    }
	    // Guardar la partida en el historial
	    guardarEnHistorico(obtenerDificultad(), HabitacionesPasadas, RealizadasBusquedas, zombies, superviviente.getVida(), superviviente.getArma(), superviviente.getBotiquines(), superviviente.getProteccion());
	}


	String obtenerDificultad() {
		String dificultad = null;
	    if (CantidadDeHabitaciones == 5) {
	    	dificultad = "Facil";
	    } else if (CantidadDeHabitaciones == 7) {
	    	dificultad = "Medio";
	    } else {
	    	dificultad = "Dificil";
	    }
		return dificultad;
		
	}
}
