import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.DefaultComboBoxModel;
public class Main extends JFrame{
    private int CantidadDeHabitaciones;
    private int HabitacionesPasadas;
    private int zombies;
    private int RealizadasBusquedas;
    private Superviviente superviviente;
    private JButton fightButton;
    
    
    
    public Main(int cantidadDeHabitaciones) {
        this.CantidadDeHabitaciones = 5;
        this.HabitacionesPasadas = 1;
        this.zombies = 1;
        this.RealizadasBusquedas = 0;
        this.superviviente = new Superviviente();

        setTitle("Menú de Juego");
        setSize(549, 385);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        
        
                JButton historicoButton = new JButton("Ver histórico");
                historicoButton.setBounds(24, 198, 147, 23);
                getContentPane().add(historicoButton);
                
                        JButton cargarButton = new JButton("Cargar partida");
                        cargarButton.setBounds(24, 164, 147, 23);
                        getContentPane().add(cargarButton);
                        
                                JButton jugarButton = new JButton("Jugar");
                                jugarButton.setBounds(24, 130, 147, 23);
                                getContentPane().add(jugarButton);
                                
                                JComboBox comboBox = new JComboBox();
                                comboBox.setModel(new DefaultComboBoxModel(new String[] {"FACIL", "DIFICIL"}));
                                comboBox.setBounds(181, 130, 84, 22);
                                getContentPane().add(comboBox);
                                jugarButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Juego nuevoJuego = new Juego(CantidadDeHabitaciones, null);
                                        nuevoJuego.iniciarJuego();
                                    }
                                });
                                comboBox.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        JComboBox cb = (JComboBox) e.getSource();
                                        String difficulty = (String) cb.getSelectedItem();
                                        if (difficulty.equals("FACIL")) {
                                            CantidadDeHabitaciones = 5;
                                        } else if (difficulty.equals("DIFICIL")) {
                                            CantidadDeHabitaciones = 10;
                                        }
                                    }
                                });
                        cargarButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                            	Juego nuevoJuego = new Juego(cantidadDeHabitaciones, null);
                            	nuevoJuego.cargarPartida();
                            }
                        });
                historicoButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	Juego nuevoJuego = new Juego(cantidadDeHabitaciones, null);
                    	nuevoJuego.verHistorico();
                    }
                });
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main(5); // Inicialización con 5 habitaciones por defecto
            }
        });
    }
}