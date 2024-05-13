import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Combate extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTextField textField_2;
    private JTextField textField_4;
    private JTextField textField_3;
    private JTextArea textArea;
    private Juego juego; // Referencia al juego
    private int HabitacionesPasadas;
    private int zombies;
    private int vidaZombie;
    private int vidaSuperviviente;
    private int armas;
    private Superviviente superviviente;
    private Zombie zombie;
    private int proteccion;
    private JLabel lblNewLabel;

    /**
     * Launch the application.
     */
    public void mostrar(int vidaSuperviviente, int armas, int zombies, int vidaZombie) {
        try {
        	this.vidaSuperviviente = superviviente.getVida();
        	this.armas = superviviente.getArma();
        	this.proteccion = superviviente.getProteccion();
            Combate dialog = new Combate(null, juego, superviviente, zombie);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create the dialog.
     */
    public Combate(JFrame combate, Juego juego, Superviviente superviviente, Zombie zombie) {
        super(combate, true);
        setTitle("combate");
        this.vidaSuperviviente = superviviente.getVida(); // Obtiene la vida del superviviente
        this.armas = armas;
        this.zombies = 1;
        this.zombie = zombie;
        this.vidaZombie = vidaZombie;
        this.juego = juego; // Asigna la referencia del juego recibida al campo de la clase
        this.vidaZombie = new Zombie().getVida();
        this.superviviente = superviviente;
        this.proteccion = superviviente.getProteccion();

        if(superviviente.getVida() <= 0) {
            dispose();
        }

        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        setLocationRelativeTo(null);

        textField = new JTextField();
        textField.setText("Vida: " + vidaSuperviviente);
        textField.setEditable(false);
        textField.setBounds(10, 11, 200, 20);
        contentPanel.add(textField);

        textField_2 = new JTextField();
        textField_2.setText("Zombies: " + zombies);
        textField_2.setEditable(false);
        textField_2.setBounds(224, 11, 200, 20);
        contentPanel.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setText("Vida del Zombie: " + vidaZombie);
        textField_3.setEditable(false);
        textField_3.setBounds(245, 42, 179, 20);
        contentPanel.add(textField_3);

        actualizarInfoFields();

        textField_4 = new JTextField();
        textField_4.setText(" | Arma: " + superviviente.getArma() + " | Protección: " + superviviente.getProteccion() +" | Botiquín: No");
        textField_4.setEditable(false);
        textField_4.setBounds(10, 42, 225, 20);
        contentPanel.add(textField_4);

        JButton fightButton = new JButton("Combatir contra un zombie");
        fightButton.setBounds(10, 227, 207, 23);
        fightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                luchar();
            }
        });
        contentPanel.add(fightButton);

        textArea = new JTextArea();
        textArea.setBounds(10, 140, 414, 76);
        contentPanel.add(textArea);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Combate.class.getResource("/imagenes/fondoCombate2.jpeg")));
        lblNewLabel.setBounds(0, 0, 434, 261);
        contentPanel.add(lblNewLabel);
    }

    public void luchar() {
        Dado dado = new Dado();
        // Generar atributos aleatorios para el zombie
        int ataqueZombie = dado.lanzarDado(3 + HabitacionesPasadas - 1, 3 + HabitacionesPasadas + 1);
            
        // Turno del superviviente
        // Ataque del superviviente
        int ataqueSuperviviente = dado.lanzarDado(1, 4);

        // Calcular el daño base del superviviente
        int danioSuperviviente = ataqueSuperviviente;

        danioSuperviviente += armas;

        

        // Asegurar que el daño no sea negativo
        danioSuperviviente = Math.max(0, danioSuperviviente);
        
        vidaZombie -= danioSuperviviente;

        // Mostrar información en el JTextArea
        textArea.append("Atacaste al zombie y le hiciste " + danioSuperviviente + " de daño.\n");

        // Verificar si el zombie ha sido derrotado
        if (vidaZombie <= 0) {
            JOptionPane.showMessageDialog(null, "¡Has eliminado al zombie!");
            zombies--; // Reducir el número de zombies en la habitación
            textField_3.setText("Vida del Zombie: 0");
            dispose();
            return; // Salir del método para evitar procesamiento adicional
        }

        // Turno del zombie solo si el superviviente sigue vivo
        // Calcular el daño del zombie al superviviente
        int danioZombie = (int) (Math.random() * ataqueZombie) + 1;
        danioZombie -= proteccion;
        // Mostrar el daño hecho por el zombie
        textArea.append("El zombie te ha quitado " + danioZombie + " puntos de vida.\n");

        // Depuración
        System.out.println("Vida superviviente antes del ataque del zombie: " + vidaSuperviviente);
        System.out.println("Daño del zombie: " + danioZombie);
        juego.actualizarZombies(0);

        // Restar el daño del zombie a la vida del superviviente
        vidaSuperviviente -= danioZombie;
        // Verificar si la vida del superviviente llega a 0 o menos
        if (vidaSuperviviente <= 0) {
            // Mostrar un mensaje de que el superviviente ha sido derrotado
            JOptionPane.showMessageDialog(null, "¡El zombie te ha derrotado!");
            juego.setResultado(false);
            // Cerrar el diálogo de combate
            dispose();
        }

        // Depuración
        System.out.println("Vida superviviente después del ataque del zombie: " + vidaSuperviviente);

        // Actualizar la vida del superviviente en el juego
        juego.actualizarVidaSuperviviente(vidaSuperviviente);
        // Actualizar la información en la interfaz
        
        superviviente.setVida(vidaSuperviviente);
        actualizarInfoFields();
    }


    public interface CombateListener {
        void onSupervivienteMuerto();
    }

    private void actualizarInfoFields() {
        textField.setText("Vida: " + vidaSuperviviente);
        textField_2.setText("Zombies: " + zombies);
        textField_3.setText("Vida del zombie: " + zombie.getVida());
        
    }
}
