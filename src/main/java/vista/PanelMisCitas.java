package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelMisCitas extends JPanel {
    
    private static final String[] COLUMNAS = {
        "Codigo", "Fecha", "Hora", "Profesional", "Tipo", "Motivo", "Estado"
    };
    
    private final JTable tablaCitas;
    private final DefaultTableModel modeloTabla;
    private final JButton btnActualizar;
    private final JButton btnCancelarCita;
    private final JButton btnReprogramarCita;
    private final JTextField txtFiltro;
    private final JComboBox<String> comboFiltroEstado;
    
    public PanelMisCitas() {
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.setRowHeight(25);
        
        btnActualizar = new JButton("Actualizar");
        btnCancelarCita = new JButton("Cancelar Cita");
        btnReprogramarCita = new JButton("Reprogramar");
        btnCancelarCita.setEnabled(false);
        btnReprogramarCita.setEnabled(false);
        
        txtFiltro = new JTextField(15);
        String[] estados = {"Todos", "Pendiente", "Confirmada", "Cancelada", "Completada"};
        comboFiltroEstado = new JComboBox<>(estados);
        
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tablaCitas.getSelectedRow() != -1;
            btnCancelarCita.setEnabled(haySeleccion);
            btnReprogramarCita.setEnabled(haySeleccion);
        });
        
        configurarLayout();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Mis Citas Agendadas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        
        JPanel panelFiltros = new JPanel(new FlowLayout());
        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(txtFiltro);
        panelFiltros.add(new JLabel("Estado:"));
        panelFiltros.add(comboFiltroEstado);
        panelFiltros.add(btnActualizar);
        panelSuperior.add(panelFiltros, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);
        
        add(new JScrollPane(tablaCitas), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnReprogramarCita);
        panelBotones.add(btnCancelarCita);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
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