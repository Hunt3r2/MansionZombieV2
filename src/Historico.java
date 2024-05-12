import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.FlowLayout;

public class Historico extends JDialog {
    private JTable table;
    private JComboBox filtroComboBox;

    public Historico() {
        setTitle("Historial de Partidas");
        setSize(861, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel para el filtro
        JPanel filtroPanel = new JPanel();
        filtroPanel.setBounds(0, 0, 845, 30);
        filtroPanel.setLayout(null);
        JLabel lblFiltrarPorResultado = new JLabel("Filtrar por resultado: ");
        lblFiltrarPorResultado.setBounds(23, 8, 123, 14);
        filtroPanel.add(lblFiltrarPorResultado);

        // Crear el ComboBox de filtro
        String[] opcionesFiltro = {"Todas", "Victorias", "Derrotas"};
        filtroComboBox = new JComboBox(opcionesFiltro);
        filtroComboBox.setBounds(156, 5, 114, 20);
        filtroPanel.add(filtroComboBox);

        // Evento de acción para el ComboBox de filtro
        filtroComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPartidasDesdeArchivo();
            }
        });

        // Crear el modelo de tabla
        String[] columnNames = {"Resultado", "Dificultad", "Habitación", "Vidas", "Botiquín", "Armas", "Protecciones"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Crear la tabla y establecer el modelo
        table = new JTable(model);

        // Agregar la tabla a un JScrollPane y agregarlo al contenido del diálogo
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 42, 803, 396);
        getContentPane().setLayout(null);
        getContentPane().add(filtroPanel);
        getContentPane().add(scrollPane);

        // Cargar las partidas desde el archivo
        cargarPartidasDesdeArchivo();
    }

    public void cargarPartidasDesdeArchivo() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar nuevas partidas

        String filtroSeleccionado = (String) filtroComboBox.getSelectedItem();

        try (BufferedReader br = new BufferedReader(new FileReader("historico.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String resultado = parts[0].substring(11).trim(); // Extraer el resultado de la partida
                if (filtroSeleccionado.equals("Todas") ||
                        (filtroSeleccionado.equals("Victorias") && resultado.equals("Ganaste")) ||
                        (filtroSeleccionado.equals("Derrotas") && resultado.equals("Perdiste"))) {
                    model.addRow(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarEnHistorico(String resultado, String dificultad, int habitacion, int vidas, boolean botiquin, int arma, int proteccion) {
        try (FileWriter writer = new FileWriter("historico.txt", true)) {
            writer.write("Resultado: " + resultado + "|");
            writer.write("Dificultad: " + dificultad + "|");
            writer.write("Habitación: " + habitacion + "|");
            writer.write("Vidas: " + vidas + "|");
            writer.write("Botiquín: " + (botiquin ? "Sí" : "No") + "|");
            writer.write("Armas: " + arma + "|");
            writer.write("Protecciones: " + proteccion + "|\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verHistorico() {
        setVisible(true);
    }
}
