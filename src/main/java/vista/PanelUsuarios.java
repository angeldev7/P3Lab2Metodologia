package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel para gestion de usuarios del sistema
 * Equivalente a la seccion de administracion de usuarios del HTML
 */
public class PanelUsuarios extends JPanel {
    
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregarUsuario;
    private JButton btnEditarUsuario;
    private JButton btnEliminarUsuario;
    private JTextField txtBuscar;
    
    private static final String[] COLUMNAS = {
        "ID", "Nombre", "Apellido", "Email", "Tipo", "Estado"
    };
    
    public PanelUsuarios() {
        inicializarComponentes();
        configurarLayout();
    }
    
    private void inicializarComponentes() {
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnAgregarUsuario = new JButton("Agregar Usuario");
        btnEditarUsuario = new JButton("Editar");
        btnEliminarUsuario = new JButton("Eliminar");
        
        txtBuscar = new JTextField(20);
        
        btnEditarUsuario.setEnabled(false);
        btnEliminarUsuario.setEnabled(false);
        
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tablaUsuarios.getSelectedRow() != -1;
            btnEditarUsuario.setEnabled(haySeleccion);
            btnEliminarUsuario.setEnabled(haySeleccion);
        });
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Gestion de Usuarios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelSuperior.add(panelBusqueda, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgregarUsuario);
        panelBotones.add(btnEditarUsuario);
        panelBotones.add(btnEliminarUsuario);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public JTable getTablaUsuarios() { return tablaUsuarios; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JButton getBtnAgregarUsuario() { return btnAgregarUsuario; }
    public JButton getBtnEditarUsuario() { return btnEditarUsuario; }
    public JButton getBtnEliminarUsuario() { return btnEliminarUsuario; }
    public JTextField getTxtBuscar() { return txtBuscar; }
}