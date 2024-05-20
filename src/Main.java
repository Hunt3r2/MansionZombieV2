import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

/**
 * Clase principal que representa el menú principal de la Mansion Zombie.
 */
public class Main extends JFrame {
    private int CantidadDeHabitaciones;
    private int HabitacionesPasadas;
    private int zombies;
    private int RealizadasBusquedas;
    private Superviviente superviviente;
    private JButton fightButton;
    JButton cargarButton;

    /**
     * Constructor de la clase Main.
     * @param cantidadDeHabitaciones Numero de habitaciones por defecto.
     */
    public Main(int cantidadDeHabitaciones) {
        setResizable(false);
        this.CantidadDeHabitaciones = 5;
        this.HabitacionesPasadas = 1;
        this.zombies = 1;
        this.RealizadasBusquedas = 0;
        this.superviviente = new Superviviente();


        setTitle("Menú Mansión Zombie");
        setSize(543, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JButton historicoButton = new JButton("Ver histórico");
        historicoButton.setFocusable(false);
        historicoButton.setBackground(new Color(102, 153, 204));
        historicoButton.setBorder(new LineBorder(new Color(0, 51, 153), 2, true));
        historicoButton.setBounds(24, 198, 147, 23);
        getContentPane().add(historicoButton);

        cargarButton = new JButton("Cargar partida");
        cargarButton.setFocusable(false);
        cargarButton.setBackground(new Color(102, 153, 204));
        cargarButton.setBorder(new LineBorder(new Color(0, 51, 153), 2, true));
        cargarButton.setBounds(24, 164, 147, 23);
        getContentPane().add(cargarButton);

        JButton jugarButton = new JButton("Jugar");
        jugarButton.setFocusable(false);
        jugarButton.setBackground(new Color(102, 153, 204));
        jugarButton.setBorder(new LineBorder(new Color(0, 51, 153), 2, true));
        jugarButton.setBounds(24, 130, 147, 23);
        getContentPane().add(jugarButton);

        JComboBox comboBox = new JComboBox();
        comboBox.setFocusable(false);
        comboBox.setBackground(new Color(102, 153, 204));
        comboBox.setBorder(new LineBorder(new Color(0, 51, 153), 2, true));
        comboBox.setModel(new DefaultComboBoxModel(new String[] { "FACIL", "MEDIO", "DIFICIL" }));
        comboBox.setBounds(181, 130, 84, 22);
        getContentPane().add(comboBox);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 153, 153));
        panel.setForeground(new Color(255, 153, 153));
        panel.setBorder(new LineBorder(new Color(255, 215, 0), 3, true));
        panel.setBounds(79, 249, 377, 66);
        getContentPane().add(panel);

        JLabel lblNewLabel_1 = new JLabel("MANSIÓN ZOMBIE");
        panel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.BOLD, 37));
        lblNewLabel_1.setForeground(new Color(128, 0, 0));

        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(Main.class.getResource("/imagenes/25531648-6470-4ef6-b27f-03a68b972078.jpg")));
        lblFondo.setBounds(0, 0, 533, 346);
        getContentPane().add(lblFondo);

        jugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Juego nuevoJuego = new Juego(null, CantidadDeHabitaciones);
                nuevoJuego.setVisible(true);
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String dificultad = (String) cb.getSelectedItem();
                if (dificultad.equals("FACIL")) {
                    CantidadDeHabitaciones = 5;
                } else if (dificultad.equals("DIFICIL")) {
                    CantidadDeHabitaciones = 10;
                } else if (dificultad.equals("MEDIO")) {
                    CantidadDeHabitaciones = 7;
                }
            }
        });

        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File archivo = new File("partida.dat");
                if (archivo.exists()) {
                    Juego juego = new Juego(Main.this, CantidadDeHabitaciones);
                    Juego juegoCargado = Juego.cargarPartida(juego);
                    if (juegoCargado != null) {
                        juegoCargado.actualizarInfoMostrada();
                        juegoCargado.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al cargar la partida.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un archivo de guardado.");
                }
            }
        });



        historicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File archivo = new File("historico.txt");
                if (!archivo.exists()) {
                    JOptionPane.showMessageDialog(null, "No hay historial guardado.");
                } else {
                    Historico historico = new Historico();
                    historico.verHistorico();
                }
            }
        });

        setVisible(true);
    }

    /**
     * Método principal.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	Main main = new Main(5);
                main.setVisible(true);
            }
        });
    }
}
