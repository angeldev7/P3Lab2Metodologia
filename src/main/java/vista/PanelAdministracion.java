package vista;

import javax.swing.*;
import java.awt.*;

public class PanelAdministracion extends JPanel {
    
    private final JLabel lblTotalCitas;
    private final JLabel lblCitasConfirmadas;
    private final JLabel lblCitasPendientes;
    private final JLabel lblCitasCanceladas;
    private final JLabel lblTotalUsuarios;
    private final JLabel lblDoctoresActivos;
    private final JLabel lblEficienciaSistema;
    private final JButton btnGenerarReporte;
    private final JButton btnExportarDatos;
    private final JButton btnConfiguracion;
    private final JTextArea textAreaReporte;
    
    public PanelAdministracion() {
        lblTotalCitas = new JLabel("Total Citas: 0");
        lblCitasConfirmadas = new JLabel("Confirmadas: 0");
        lblCitasPendientes = new JLabel("Pendientes: 0");
        lblCitasCanceladas = new JLabel("Canceladas: 0");
        lblTotalUsuarios = new JLabel("Total Usuarios: 0");
        lblDoctoresActivos = new JLabel("Doctores Activos: 0");
        lblEficienciaSistema = new JLabel("Eficiencia: 0%");
        
        btnGenerarReporte = new JButton("Generar Reporte");
        btnExportarDatos = new JButton("Exportar Datos");
        btnConfiguracion = new JButton("Configuracion");
        
        textAreaReporte = new JTextArea(10, 30);
        textAreaReporte.setEditable(false);
        
        configurarLayout();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JLabel lblTitulo = new JLabel("Panel de Administracion");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelEstadisticas = new JPanel(new GridLayout(3, 3, 10, 10));
        panelEstadisticas.setBorder(BorderFactory.createTitledBorder("Estadisticas del Sistema"));
        panelEstadisticas.add(lblTotalCitas);
        panelEstadisticas.add(lblCitasConfirmadas);
        panelEstadisticas.add(lblCitasPendientes);
        panelEstadisticas.add(lblCitasCanceladas);
        panelEstadisticas.add(lblTotalUsuarios);
        panelEstadisticas.add(lblDoctoresActivos);
        panelEstadisticas.add(lblEficienciaSistema);
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelEstadisticas, BorderLayout.NORTH);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGenerarReporte);
        panelBotones.add(btnExportarDatos);
        panelBotones.add(btnConfiguracion);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        JPanel panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBorder(BorderFactory.createTitledBorder("Reporte Detallado"));
        panelReporte.add(new JScrollPane(textAreaReporte), BorderLayout.CENTER);
        
        add(panelReporte, BorderLayout.CENTER);
    }
    
    public JLabel getLblTotalCitas() {
        return lblTotalCitas;
    }
    
    public JLabel getLblCitasConfirmadas() {
        return lblCitasConfirmadas;
    }
    
    public JLabel getLblCitasPendientes() {
        return lblCitasPendientes;
    }
    
    public JLabel getLblCitasCanceladas() {
        return lblCitasCanceladas;
    }
    
    public JLabel getLblTotalUsuarios() {
        return lblTotalUsuarios;
    }
    
    public JLabel getLblDoctoresActivos() {
        return lblDoctoresActivos;
    }
    
    public JLabel getLblEficienciaSistema() {
        return lblEficienciaSistema;
    }
    
    public JButton getBtnGenerarReporte() {
        return btnGenerarReporte;
    }
    
    public JButton getBtnExportarDatos() {
        return btnExportarDatos;
    }
    
    public JButton getBtnConfiguracion() {
        return btnConfiguracion;
    }
    
    public JTextArea getTextAreaReporte() {
        return textAreaReporte;
    }
    
    public void actualizarEstadisticas(int totalCitas, int confirmadas, int pendientes, 
                                     int canceladas, int totalUsuarios, int doctoresActivos, double eficiencia) {
        lblTotalCitas.setText("Total Citas: " + totalCitas);
        lblCitasConfirmadas.setText("Confirmadas: " + confirmadas);
        lblCitasPendientes.setText("Pendientes: " + pendientes);
        lblCitasCanceladas.setText("Canceladas: " + canceladas);
        lblTotalUsuarios.setText("Total Usuarios: " + totalUsuarios);
        lblDoctoresActivos.setText("Doctores Activos: " + doctoresActivos);
        lblEficienciaSistema.setText(String.format("Eficiencia: %.1f%%", eficiencia));
    }
}