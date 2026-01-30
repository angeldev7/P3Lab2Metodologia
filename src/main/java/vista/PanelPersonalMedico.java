package vista;

import javax.swing.*;
import java.awt.*;

public class PanelPersonalMedico extends JPanel {
    
    private final JComboBox<String> comboProfesional;
    private final JTable tablaHorarios;
    private final JButton btnConfigurarHorarios;
    private final JButton btnVerCitas;
    private final JTextArea textAreaEstadisticas;
    
    public PanelPersonalMedico() {
        comboProfesional = new JComboBox<>();
        
        String[] columnas = {"Dia", "Hora Inicio", "Hora Fin", "Estado"};
        tablaHorarios = new JTable(new String[0][0], columnas);
        
        btnConfigurarHorarios = new JButton("Configurar Horarios");
        btnVerCitas = new JButton("Ver Mis Citas");
        
        textAreaEstadisticas = new JTextArea(5, 20);
        textAreaEstadisticas.setEditable(false);
        
        configurarLayout();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Panel Personal Medico");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(lblTitulo);
        panelSuperior.add(new JLabel("Profesional:"));
        panelSuperior.add(comboProfesional);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(new JScrollPane(tablaHorarios), BorderLayout.CENTER);
        panelCentral.add(new JScrollPane(textAreaEstadisticas), BorderLayout.SOUTH);
        
        add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnConfigurarHorarios);
        panelBotones.add(btnVerCitas);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public JComboBox<String> getComboProfesional() {
        return comboProfesional;
    }
    
    public JTable getTablaHorarios() {
        return tablaHorarios;
    }
    
    public JButton getBtnConfigurarHorarios() {
        return btnConfigurarHorarios;
    }
    
    public JButton getBtnVerCitas() {
        return btnVerCitas;
    }
    
    public JTextArea getTextAreaEstadisticas() {
        return textAreaEstadisticas;
    }
}