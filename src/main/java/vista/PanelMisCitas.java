package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel para mostrar las citas del usuario
 * Equivalente a la seccion "Mis Citas" del prototipo HTML
 */
public class PanelMisCitas extends JPanel {
    
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnCancelarCita;
    private JButton btnReprogramarCita;
    private JTextField txtFiltro;
    private JComboBox<String> comboFiltroEstado;
    
    private static final String[] COLUMNAS = {
        "Codigo", "Fecha", "Hora", "Profesional", "Tipo", "Motivo", "Estado"
    };
    
    public PanelMisCitas() {
        inicializarComponentes();
        configurarLayout();
    }
    
    private void inicializarComponentes() {
        // Modelo de tabla
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Tabla
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.setRowHeight(25);
        
        // Botones
        btnActualizar = new JButton("Actualizar");
        btnCancelarCita = new JButton("Cancelar Cita");
        btnReprogramarCita = new JButton("Reprogramar");
        
        btnCancelarCita.setEnabled(false);
        btnReprogramarCita.setEnabled(false);
        
        // Filtros
        txtFiltro = new JTextField(15);
        String[] estados = {"Todos", "Pendiente", "Confirmada", "Cancelada", "Completada"};
        comboFiltroEstado = new JComboBox<>(estados);
        
        // Listener para habilitar botones cuando se selecciona una fila
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tablaCitas.getSelectedRow() != -1;
            btnCancelarCita.setEnabled(haySeleccion);
            btnReprogramarCita.setEnabled(haySeleccion);
        });
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con titulo y filtros
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Mis Citas Agendadas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout());
        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(txtFiltro);
        panelFiltros.add(new JLabel("Estado:"));
        panelFiltros.add(comboFiltroEstado);
        panelFiltros.add(btnActualizar);
        
        panelSuperior.add(panelFiltros, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla con scroll
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones de accion
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnReprogramarCita);
        panelBotones.add(btnCancelarCita);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    // Getters para el controlador
    public JTable getTablaCitas() {
        return tablaCitas;
    }
    
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    
    public JButton getBtnCancelarCita() {
        return btnCancelarCita;
    }
    
    public JButton getBtnReprogramarCita() {
        return btnReprogramarCita;
    }
    
    public JTextField getTxtFiltro() {
        return txtFiltro;
    }
    
    public JComboBox<String> getComboFiltroEstado() {
        return comboFiltroEstado;
    }
    
    public void agregarCita(Object[] datosCita) {
        modeloTabla.addRow(datosCita);
    }
    
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }
    
    public String getCitaSeleccionada() {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada != -1) {
            return (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        }
        return null;
    }
}