import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que representa el historial de partidas.
 */
public class Historico extends JDialog {
    private JTable table;
    private JComboBox filtroComboBox;

    /**
     * Constructor de la clase Historico.
     */
    public Historico() {
        setTitle("Historial de Partidas");
        setSize(861, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel filtroPanel = new JPanel();
        filtroPanel.setBounds(0, 0, 845, 30);
        filtroPanel.setLayout(null);
        JLabel lblFiltrarPorResultado = new JLabel("Filtrar por resultado: ");
        lblFiltrarPorResultado.setBounds(23, 8, 123, 14);
        filtroPanel.add(lblFiltrarPorResultado);

        String[] opcionesFiltro = {"Todas", "Victorias", "Derrotas"};
        filtroComboBox = new JComboBox(opcionesFiltro);
        filtroComboBox.setBounds(156, 5, 114, 20);
        filtroPanel.add(filtroComboBox);

        filtroComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPartidasDesdeArchivo();
            }
        });

        String[] columnNames = {"Resultado", "Dificultad", "Habitación", "Vidas", "Botiquín", "Armas", "Protecciones"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 42, 803, 396);
        getContentPane().setLayout(null);
        getContentPane().add(filtroPanel);
        getContentPane().add(scrollPane);

        cargarPartidasDesdeArchivo();
    }

    /**
     * Metodo para cargar las partidas desde el archivo.
     */
    public void cargarPartidasDesdeArchivo() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String filtroSeleccionado = (String) filtroComboBox.getSelectedItem();

        try (BufferedReader lectorBuffered = new BufferedReader(new FileReader("historico.txt"))) {
            String linea;
            while ((linea = lectorBuffered.readLine()) != null) {
                String[] info = linea.split("\\|");
                String resultado = info[0].substring(11).trim();
                if (filtroSeleccionado.equals("Todas") ||
                        (filtroSeleccionado.equals("Victorias") && resultado.equals("Ganaste")) ||
                        (filtroSeleccionado.equals("Derrotas") && resultado.equals("Perdiste"))) {
                    model.addRow(info);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para guardar una partida en el historial.
     * @param resultado Resultado de la partida.
     * @param dificultad Dificultad de la partida.
     * @param habitacion Habitacion en la que se encontraba el jugador.
     * @param vidas Vidas del jugador.
     * @param botiquin Si el jugador tenia botiquin.
     * @param arma Numero de armas del jugador.
     * @param proteccion Número de protecciones del jugador.
     */
    public void guardarEnHistorico(String resultado, String dificultad, int habitacion, int vidas, boolean botiquin, int arma, int proteccion) {
        try (FileWriter escritor = new FileWriter("historico.txt", true)) {
            escritor.write("Resultado: " + resultado + "|");
            escritor.write("Dificultad: " + dificultad + "|");
            escritor.write("Habitación: " + habitacion + "|");
            escritor.write("Vidas: " + vidas + "|");
            escritor.write("Botiquín: " + (botiquin ? "Sí" : "No") + "|");
            escritor.write("Armas: " + arma + "|");
            escritor.write("Protecciones: " + proteccion + "|\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para visualizar el historial de partidas.
     */
    public void verHistorico() {
        setVisible(true);
    }
}
