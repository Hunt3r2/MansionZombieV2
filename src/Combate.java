import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Combate extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    private int HabitacionesPasadas;
    private int zombies;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            Combate dialog = new Combate();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public Combate() {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        textField = new JTextField();
        textField.setText("Vida: 20");
        textField.setEditable(false);
        textField.setBounds(10, 11, 200, 20);
        contentPanel.add(textField);

        textField_1 = new JTextField();
        textField_1.setText("Habitaciones: 0/0");
        textField_1.setEditable(false);
        textField_1.setBounds(220, 11, 200, 20);
        contentPanel.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setText("Zombies: 1");
        textField_2.setEditable(false);
        textField_2.setBounds(220, 39, 200, 20);
        contentPanel.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setText("Busquedas: 0");
        textField_3.setEditable(false);
        textField_3.setBounds(10, 39, 200, 20);
        contentPanel.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setText(" | Arma: 0 | Protección: 0 | Botiquín: No");
        textField_4.setEditable(false);
        textField_4.setBounds(10, 70, 225, 20);
        contentPanel.add(textField_4);

        JButton fightButton = new JButton("Combatir contra un zombie");
        fightButton.setBounds(10, 227, 207, 23);
        fightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                luchar();
            }
        });
        contentPanel.add(fightButton);
    }

    public void luchar() {
        // Generar atributos aleatorios para el zombie
        int vidaZombie = (int) (Math.random() * 2) + 2 + (HabitacionesPasadas - 1);
        int ataqueZombie = (int) (Math.random() * 3) + 3 + (HabitacionesPasadas - 1);

        // Turno del superviviente
        int ataqueSuperviviente = 4; // Obtener el ataque del superviviente de alguna manera
        vidaZombie -= ataqueSuperviviente;

        // Verificar si el zombie ha sido derrotado
        if (vidaZombie <= 0) {
            JOptionPane.showMessageDialog(null, "¡Has eliminado al zombie!");
            zombies--; // Reducir el número de zombies en la habitación
        } else {
            // Turno del zombie solo si el superviviente sigue vivo
            int ataqueZombieFinal = (int) (Math.random() * ataqueZombie) + 1;
            // Calcular el daño del zombie al superviviente
            int danio = ataqueZombieFinal; // Cambiar esto por la lógica adecuada
            // Mostrar el daño hecho por el zombie
            JOptionPane.showMessageDialog(null, "El zombie te ha quitado " + danio + " puntos de vida.");
        }

        // Actualizar la información en la interfaz
        actualizarInfoFields();
    }

    // Método para actualizar los campos de texto con la información actualizada
    private void actualizarInfoFields() {
        // Implementa la lógica para actualizar los campos de texto con la información actualizada
    }
}
