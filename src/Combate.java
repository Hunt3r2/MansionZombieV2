import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.LineBorder;

/**
 * Esta clase representa el combate entre el superviviente y el zombies.
 */
public class Combate extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldVidaSuperviviente;
    private JTextField textFieldZombies;
    private JTextField textFieldDatosSuperviviente;
    private JTextField textFieldVidaZombie;
    private JTextArea textArea;
    private Juego juego;
    private int HabitacionesPasadas;
    private int zombies;
    private int vidaZombie;
    private int vidaSuperviviente;
    private int armas;
    private Superviviente superviviente;
    private Zombie zombie;
    private int proteccion;
    private JLabel lblFondo;
    private JButton btnTerminar;
    private JButton btnPelea;
    private JLabel lblGato;
    private JLabel lblNewLabel;
    private JPanel panelGameOver;
    private int ronda = 1;


    /**
     * Muestra el dialogo de combate con los parametros especificados.
     * @param vidaSuperviviente La vida actual del superviviente.
     * @param armas La cantidad de armas que tiene el superviviente.
     * @param zombies El numero de zombies presentes en la habitación.
     * @param vidaZombie La vida del zombie con el que se esta combatiendo.
     */
    public void mostrar(int vidaSuperviviente, int armas, int zombies, int vidaZombie) {
        try {
            this.vidaSuperviviente = superviviente.getVida();
            this.armas = superviviente.getArma();
            this.proteccion = superviviente.getProteccion();
            Combate dialog = new Combate(null, juego, superviviente, zombie);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo dialogo de combate.
     * @param combate El JFrame padre.
     * @param juego La instancia del juego.
     * @param superviviente El superviviente involucrado en el combate.
     * @param zombie El zombie contra el que se está combatiendo.
     */
    public Combate(JFrame combate, Juego juego, Superviviente superviviente, Zombie zombie) {
        super(combate, true);
        setResizable(false);
        setTitle("combate");
        this.vidaSuperviviente = superviviente.getVida();
        this.armas = armas;
        this.zombies = 1;
        this.zombie = zombie;
        this.vidaZombie = vidaZombie;
        this.juego = juego;
        this.vidaZombie = new Zombie().getVida();
        this.superviviente = superviviente;
        this.proteccion = superviviente.getProteccion();

        if (superviviente.getVida() <= 0) {
            dispose();
        }

        setBounds(100, 100, 530, 350);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);
        
        panelGameOver = new JPanel();
        panelGameOver.setBorder(new LineBorder(new Color(128, 0, 0), 4, true));
        panelGameOver.setBackground(new Color(0, 0, 0));
        panelGameOver.setBounds(10, 11, 494, 263);
        contentPanel.add(panelGameOver);
        panelGameOver.setLayout(null);
        panelGameOver.setVisible(false);
        
        lblNewLabel = new JLabel("GAME OVER");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lblNewLabel.setForeground(new Color(139, 0, 0));
        lblNewLabel.setBounds(15, 104, 211, 47);
        panelGameOver.add(lblNewLabel);
        
        lblGato = new JLabel("");
        lblGato.setBounds(236, 11, 224, 238);
        panelGameOver.add(lblGato);
        lblGato.setBorder(new LineBorder(new Color(255, 204, 0), 3, true));
        lblGato.setIcon(new ImageIcon(Combate.class.getResource("/imagenes/gato.jpg")));

        textFieldVidaSuperviviente = new JTextField();
        textFieldVidaSuperviviente.setBackground(new Color(143, 188, 143));
        textFieldVidaSuperviviente.setBorder(new LineBorder(new Color(0, 100, 0), 2, true));
        textFieldVidaSuperviviente.setText("Vida: " + vidaSuperviviente);
        textFieldVidaSuperviviente.setEditable(false);
        textFieldVidaSuperviviente.setBounds(10, 11, 200, 20);
        contentPanel.add(textFieldVidaSuperviviente);

        textFieldZombies = new JTextField();
        textFieldZombies.setBackground(new Color(143, 188, 143));
        textFieldZombies.setBorder(new LineBorder(new Color(0, 100, 0), 2, true));
        textFieldZombies.setText("Zombies: " + zombies);
        textFieldZombies.setEditable(false);
        textFieldZombies.setBounds(304, 11, 200, 20);
        contentPanel.add(textFieldZombies);

        textFieldVidaZombie = new JTextField();
        textFieldVidaZombie.setBackground(new Color(143, 188, 143));
        textFieldVidaZombie.setBorder(new LineBorder(new Color(0, 100, 0), 2, true));
        textFieldVidaZombie.setText("Vida del Zombie: " + vidaZombie);
        textFieldVidaZombie.setEditable(false);
        textFieldVidaZombie.setBounds(325, 42, 179, 20);
        contentPanel.add(textFieldVidaZombie);

        actualizarInfoMostrada();

        textFieldDatosSuperviviente = new JTextField();
        textFieldDatosSuperviviente.setBackground(new Color(143, 188, 143));
        textFieldDatosSuperviviente.setBorder(new LineBorder(new Color(0, 100, 0), 2, true));
        textFieldDatosSuperviviente.setText(" | Arma: " + superviviente.getArma() + " | Protección: " + superviviente.getProteccion() + " | Botiquín: No");
        textFieldDatosSuperviviente.setEditable(false);
        textFieldDatosSuperviviente.setBounds(10, 42, 225, 20);
        contentPanel.add(textFieldDatosSuperviviente);

        btnPelea = new JButton("Combatir contra un zombie");
        btnPelea.setBackground(new Color(176, 224, 230));
        btnPelea.setBorder(new LineBorder(new Color(65, 105, 225), 2, true));
        btnPelea.setBounds(10, 277, 207, 23);
        btnPelea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                luchar();
            }
        });
        contentPanel.add(btnPelea);
       
        textArea = new JTextArea();
        textArea.setBackground(new Color(143, 188, 143));
        textArea.setBorder(new LineBorder(new Color(47, 79, 79), 2, true));
        textArea.setBounds(10, 164, 494, 104);
        contentPanel.add(textArea);
        
        btnTerminar = new JButton("Terminar");
        btnTerminar.setBackground(new Color(176, 224, 230));
        btnTerminar.setBorder(new LineBorder(new Color(65, 105, 225), 2, true));
        btnTerminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnTerminar.setBounds(297, 277, 207, 23);
        contentPanel.add(btnTerminar);
        btnTerminar.setEnabled(false);


        lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(Combate.class.getResource("/imagenes/fondoCombate3.jpeg")));
        lblFondo.setBounds(0, 0, 514, 311);
        contentPanel.add(lblFondo);
    }

    /**
     * Realiza una accion de combate contra un zombie.
     */
    public void luchar() {
        Dado dado = new Dado();
        textArea.append(">>> RONDA: " + ronda + "\n");
        ronda++;
        int ataqueSuperviviente = dado.lanzarDado(1, 4);
        int golpeSuperviviente = ataqueSuperviviente;
        golpeSuperviviente += armas;
        golpeSuperviviente = Math.max(0, golpeSuperviviente);
        vidaZombie -= golpeSuperviviente;
        textArea.append("Atacaste al zombie y le hiciste " + golpeSuperviviente + " de daño.\n");
        if (vidaZombie <= 0) {
            JOptionPane.showMessageDialog(null, "¡Has eliminado al zombie!");
            zombies--;
            textFieldVidaZombie.setText("Vida del Zombie: 0");
            btnTerminar.setEnabled(true);
            btnPelea.setEnabled(false);
            return;
        }
        int golpeZombie = dado.lanzarDado(1, 2) + 2 + (HabitacionesPasadas - 1) ;
        int ataqueZombie = golpeZombie - proteccion;
        textArea.append("El zombie te ha quitado " + golpeZombie + " puntos de vida.\n");
        juego.actualizarZombies(0);
        vidaSuperviviente -= ataqueZombie;
        if (vidaSuperviviente <= 0) {
            JOptionPane.showMessageDialog(null, "¡El zombie te ha derrotado!");
            panelGameOver.setVisible(true);
            juego.setResultado(false);
            btnTerminar.setEnabled(true);
            btnPelea.setEnabled(false);
            textFieldZombies.setVisible(false);
            textFieldDatosSuperviviente.setVisible(false);
            textFieldVidaZombie.setVisible(false);
            textFieldVidaSuperviviente.setVisible(false);
        }
        
        juego.actualizarVidaSuperviviente(vidaSuperviviente);
        superviviente.setVida(vidaSuperviviente);
        actualizarInfoMostrada();
    }

    /**
     * Actualiza los campos de informacion en la ventana de combate.
     */
    private void actualizarInfoMostrada() {
        textFieldVidaSuperviviente.setText("Vida: " + vidaSuperviviente);
        textFieldZombies.setText("Zombies: " + zombies);
        textFieldVidaZombie.setText("Vida del zombie: " + zombie.getVida());
    }
}
