import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

/**
 * La clase Juego representa el juego Mansión Zombie, esta clase gestiona la interfaz gráfica del juego y la lógica del juego en sí.
 */
public class Juego extends JDialog {
    private int CantidadDeHabitaciones;
    private int HabitacionesPasadas;
    private int zombies;
    private int RealizadasBusquedas;
    private Superviviente superviviente;
    private Zombie zombie;
    private JButton btnPelea;
    private JButton btnBuscar;
    private JButton btnAvanzar;
    private JButton btnCurar;
    private JTextField vidaTextField;
    private JTextField habitacionesTextField;
    private JTextField busquedasTextField;
    private JTextField zombiesTextField;
    private JTextField textFieldArmaPB;
    private Historico historico;
    String resultadoJuego;
    

    /**
     * Constructor de la clase Juego.
     * @param juego El JFrame principal del juego.
     * @param cantidadDeHabitaciones La cantidad de habitaciones en el juego.
     */
    public Juego(JFrame juego, int cantidadDeHabitaciones)  {
    	super(juego, true);
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

        JLabel lblNewLabel = new JLabel("Codigo de desarrollador");
        lblNewLabel.setForeground(new Color(255, 215, 0));
        lblNewLabel.setBounds(378, 11, 139, 14);
        getContentPane().add(lblNewLabel);
        
        
        JPanel panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);

        vidaTextField = new JTextField();
        vidaTextField.setBackground(new Color(224, 255, 255));
        vidaTextField.setBorder(new LineBorder(new Color(135, 206, 235), 2, true));
        vidaTextField.setEditable(false);
        vidaTextField.setBounds(10, 30, 200, 20);
        panel.add(vidaTextField);

        habitacionesTextField = new JTextField();
        habitacionesTextField.setBackground(new Color(224, 255, 255));
        habitacionesTextField.setBorder(new LineBorder(new Color(135, 206, 235), 2, true));
        habitacionesTextField.setEditable(false);
        habitacionesTextField.setBounds(220, 30, 200, 20);
        panel.add(habitacionesTextField);

        busquedasTextField = new JTextField();
        busquedasTextField.setBackground(new Color(224, 255, 255));
        busquedasTextField.setBorder(new LineBorder(new Color(135, 206, 235), 2, true));
        busquedasTextField.setEditable(false);
        busquedasTextField.setBounds(10, 58, 200, 20);
        panel.add(busquedasTextField);

        zombiesTextField = new JTextField();
        zombiesTextField.setBackground(new Color(224, 255, 255));
        zombiesTextField.setBorder(new LineBorder(new Color(135, 206, 235), 2, true));
        zombiesTextField.setEditable(false);
        zombiesTextField.setBounds(220, 58, 200, 20);
        panel.add(zombiesTextField);

        Juego esteJuego = this; // Guardar una referencia al objeto Juego
        
        
        btnPelea = new JButton("Combatir contra un zombie");
        btnPelea.setBackground(new Color(135, 206, 250));
        btnPelea.setBorder(new LineBorder(new Color(0, 0, 128), 3, true));
        btnPelea.setBounds(20, 209, 207, 23);
        btnPelea.setEnabled(true);
        btnPelea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(superviviente.getVida() <= 0) {
                    dispose();
                }
            	Combate combate = new Combate(juego, esteJuego, superviviente, zombie);                
            	combate.mostrar(superviviente.getVida(), superviviente.getArma(), zombies, 0);
                btnBuscar.setEnabled(true);
                btnAvanzar.setEnabled(true);
                btnCurar.setEnabled(true);
                btnPelea.setEnabled(false);
                
            }
        });
        panel.add(btnPelea);

        btnBuscar = new JButton("Buscar en la habitación");
        btnBuscar.setBackground(new Color(135, 206, 250));
        btnBuscar.setBorder(new LineBorder(new Color(0, 0, 128), 3, true));
        btnBuscar.setBounds(20, 140, 207, 23);
        btnBuscar.setEnabled(false);
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (RealizadasBusquedas < 3) {
                    Busquedashabitaciones();
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes buscar más en esta habitación.");
                }
            }
        });
        panel.add(btnBuscar);


        btnAvanzar = new JButton("Avanzar a la siguiente habitación");
        btnAvanzar.setBackground(new Color(135, 206, 250));
        btnAvanzar.setBorder(new LineBorder(new Color(0, 0, 128), 3, true));
        btnAvanzar.setBounds(20, 243, 207, 23);
        btnAvanzar.setEnabled(false);
        btnAvanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasarHabitacion();
            }
        });
        panel.add(btnAvanzar);

        btnCurar = new JButton("Usar botiquín");
        btnCurar.setBackground(new Color(135, 206, 250));
        btnCurar.setBorder(new LineBorder(new Color(0, 0, 128), 3, true));
        btnCurar.setBounds(20, 175, 207, 23);
        btnCurar.setEnabled(false);
        btnCurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usarBotiquin();
            }
        });
        panel.add(btnCurar);
        
        textFieldArmaPB = new JTextField();
        textFieldArmaPB.setBackground(new Color(224, 255, 255));
        textFieldArmaPB.setBorder(new LineBorder(new Color(135, 206, 235), 2, true));
        textFieldArmaPB.setEditable(false);
        textFieldArmaPB.setBounds(10, 89, 225, 20);
        panel.add(textFieldArmaPB);
        
        actualizarInfoMostrada();
        
        JButton btnGuardarPartida = new JButton("Guardar Partida");
        btnGuardarPartida.setBackground(new Color(135, 206, 250));
        btnGuardarPartida.setBorder(new LineBorder(new Color(0, 0, 128), 3, true));
        btnGuardarPartida.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		guardarPartida();
        	}
        });
        btnGuardarPartida.setBounds(483, 327, 161, 23);
        panel.add(btnGuardarPartida);
        
        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(Juego.class.getResource("/imagenes/fondo1.jpeg")));
        lblFondo.setBounds(0, 0, 654, 361);
        panel.add(lblFondo);
        setVisible(true);

        actualizarInfoMostrada(); // Actualizar los campos de texto con la información inicial
    }


    /**
     * Realiza una acción específica según la opción seleccionada.
     * @param Accion La acción a realizar.
     */
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

    /**
     * Reinicia el juego
     */
    public void reiniciarJuego() {
        superviviente.reiniciarStats();
        this.HabitacionesPasadas = 0; 
        this.zombies = 0; 
        this.RealizadasBusquedas = 0;
        actualizarInfoMostrada(); 
    }

    /**
     * Usa un botiquín para aumentar la vida del superviviente.
     */
    public void usarBotiquin() {
        if (superviviente.getBotiquines()) {
            superviviente.usarBotiquin();
            JOptionPane.showMessageDialog(null, "Has usado un botiquín. Vida actual: " + superviviente.getVida());
        } else {
            JOptionPane.showMessageDialog(null, "No tienes botiquines para usar.");
        }
    }

    /**
     * Realiza una búsqueda en la habitación actual.
     */
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
            JOptionPane.showMessageDialog(null, "Cantidad de busquedas realizadas son: " + RealizadasBusquedas);
        } else {
            JOptionPane.showMessageDialog(null, "Has realizado 3 busquedas no se puede realizar mas busquedas");
        }
        actualizarInfoMostrada();
    }

    /**
     * Atrae a los zombies según la probabilidad y muestra un mensaje de resultado.
     */
    public void hacerRuido() {
        int suerte2 = Dado.ProbablidadAparicionZombie();
        if (suerte2 == 1) {
            JOptionPane.showMessageDialog(null, "Qué suerte jugador, no has atraído ningún zombie");
        } else if (suerte2 == 2) {
            JOptionPane.showMessageDialog(null, "Mala suerte jugador, has atraído a un zombie");
            zombies++;
            btnPelea.setEnabled(true);
            btnBuscar.setEnabled(false);
            btnAvanzar.setEnabled(false);
            btnCurar.setEnabled(false);
        } else if (suerte2 == 3) {
            JOptionPane.showMessageDialog(null, "Mala suerte jugador, has atraído a ¡DOS ZOMBIES!");
            zombies += 2;
            btnPelea.setEnabled(true);
            btnBuscar.setEnabled(false);
            btnAvanzar.setEnabled(false);
            btnCurar.setEnabled(false);
        }
        actualizarInfoMostrada();
    }

    /**
     * Pasa a la siguiente habitación del juego.
     */
    public void pasarHabitacion() {
        HabitacionesPasadas++;
        RealizadasBusquedas = 0;
        actualizarInfoMostrada(); 
        if (HabitacionesPasadas == CantidadDeHabitaciones) {
            JOptionPane.showMessageDialog(null, "¡Felicitaciones! Has escapado.");
            setResultado(true);
            dispose();
        } else {
            zombies = 1; 
            btnBuscar.setEnabled(false);
            btnAvanzar.setEnabled(false);
            btnCurar.setEnabled(false);
            btnPelea.setEnabled(true); 
            JOptionPane.showMessageDialog(null, "Has avanzado a la siguiente habitación.");
        }
        actualizarInfoMostrada(); 
    }


    /**
     * Actualiza la información mostrada.
     */
    public void actualizarInfoMostrada() {
        vidaTextField.setText("Vida: " + superviviente.getVida());
        habitacionesTextField.setText("Habitaciones: " + HabitacionesPasadas + "/" + CantidadDeHabitaciones);
        busquedasTextField.setText("Busquedas: " + RealizadasBusquedas);
        zombiesTextField.setText("Zombies: " + zombies);
        
        String infoExtra = " | Arma: " + superviviente.getArma() 
                        + " | Protección: " + superviviente.getProteccion() 
                        + " | Botiquín: " + (superviviente.getBotiquines() ? "Sí" : "No");
        textFieldArmaPB.setText(infoExtra);
    }
    
    /**
     * Actualiza la vida del superviviente.
     * @param nuevaVida La nueva vida del superviviente.
     */
    public void actualizarVidaSuperviviente(int nuevaVida) {
        superviviente.setVida(nuevaVida); 
        actualizarInfoMostrada(); 
    }
    
    /**
     * Guarda la partida actual en un archivo.
     */
    public void guardarPartida() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("partida.dat"))) {
            outputStream.writeInt(CantidadDeHabitaciones);
            outputStream.writeInt(HabitacionesPasadas);
            outputStream.writeInt(zombies);
            outputStream.writeInt(RealizadasBusquedas);
            outputStream.writeObject(superviviente); 
            JOptionPane.showMessageDialog(null, "Partida guardada correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar la partida: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene el objeto Superviviente asociado con este juego.
     * @return El objeto Superviviente asociado con este juego.
     */
    public Superviviente getSuperviviente() {
        return superviviente;
    }


    /**
     * Carga una partida guardada previamente desde un archivo.
     * @param juegoExistente La instancia del juego existente.
     * @return La instancia del juego con la partida cargada.
     */
    public static Juego cargarPartida(Juego juegoExistente) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("partida.dat"))) {
            int cantidadDeHabitaciones = inputStream.readInt();
            int habitacionesPasadas = inputStream.readInt();
            int zombies = inputStream.readInt();
            int realizadasBusquedas = inputStream.readInt();
            
            Superviviente superviviente = (Superviviente) inputStream.readObject(); 

            juegoExistente.setHabitacionesPasadas(habitacionesPasadas);
            juegoExistente.zombies = zombies;
            juegoExistente.RealizadasBusquedas = realizadasBusquedas;
            juegoExistente.superviviente = superviviente;

            JOptionPane.showMessageDialog(null, "Partida cargada correctamente.");
            return juegoExistente; 
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la partida: " + e.getMessage());
            e.printStackTrace();
            return null; 
        }
    }


    /**
     * Establece la cantidad de habitaciones pasadas en el juego.
     * @param habitacionesPasadas La cantidad de habitaciones pasadas.
     */
    public void setHabitacionesPasadas(int habitacionesPasadas) {
        this.HabitacionesPasadas = habitacionesPasadas;
    }
    
    /**
     * Actualiza la cantidad de zombies en el juego.
     * @param zombies La nueva cantidad de zombies.
     */
    public void actualizarZombies(int zombies) {
        this.zombies = zombies;
    }

    /**
     * Muestra el historial de partidas guardadas.
     */
	public void verHistorico() {
		
		String nombreArchivo = "historico.txt";
		historico.cargarPartidasDesdeArchivo();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {       	
            JOptionPane.showMessageDialog(null, "El archivo de historial aún no existe.");
            return;
        }
        Historico historicoDialog = new Historico();
        historicoDialog.setVisible(true);
		
	}
	
	/**
     * Guarda la información de una partida en el historial.
     * @param dificultad La dificultad de la partida.
     * @param habitacion La habitación en la que se terminó la partida.
     * @param busquedas El número de búsquedas realizadas en la partida.
     * @param zombies El número de zombies en la partida.
     * @param vida La vida del superviviente al final de la partida.
     * @param arma El arma del superviviente al final de la partida.
     * @param botiquin Si se tenía un botiquín al final de la partida.
     * @param proteccion La protección del superviviente al final de la partida.
     */
	public void guardarEnHistorico(String dificultad, int habitacion, int busquedas, int zombies, int vida, int arma, boolean botiquin, int proteccion) {
        historico.guardarEnHistorico(getResultado(), dificultad, habitacion, vida, botiquin, arma, proteccion);
    }

	/**
     * Obtiene el resultado final del juego.
     * @return El resultado final del juego.
     */
	private String getResultado() {
		return resultadoJuego;
	}
	
	/**
     * Establece el resultado final del juego.
     * @param resultado El resultado final del juego.
     */
	public void setResultado(boolean resultado) {
	    if (resultado) {
	        resultadoJuego = "Ganaste";
	    } else {
	        resultadoJuego = "Perdiste";
	        dispose();
	    }
	    guardarEnHistorico(obtenerDificultad(), HabitacionesPasadas, RealizadasBusquedas, zombies, superviviente.getVida(), superviviente.getArma(), superviviente.getBotiquines(), superviviente.getProteccion());
	}



	/**
     * Obtiene la dificultad del juego en función de la cantidad de habitaciones.
     * @return La dificultad del juego.
     */
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
