package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Ventana principal del sistema de gestion de citas medicas
 * Implementa la interfaz grafica equivalente al prototipo HTML
 */
public class VentanaPrincipal extends JFrame {
    
    private JTabbedPane tabbedPane;
    private PanelAgendarCita panelAgendarCita;
    private PanelMisCitas panelMisCitas;
    private PanelUsuarios panelUsuarios;
    private PanelPersonalMedico panelPersonalMedico;
    private PanelAdministracion panelAdministracion;
    
    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Gestion de Citas Medicas - Lab 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void inicializarComponentes() {
        tabbedPane = new JTabbedPane();
        
        // Crear los paneles correspondientes a cada tab del HTML
        panelAgendarCita = new PanelAgendarCita();
        panelMisCitas = new PanelMisCitas();
        panelUsuarios = new PanelUsuarios();
        panelPersonalMedico = new PanelPersonalMedico();
        panelAdministracion = new PanelAdministracion();
        
        // Agregar tabs como en el HTML
        tabbedPane.addTab("Agendar Cita", panelAgendarCita);
        tabbedPane.addTab("Mis Citas", panelMisCitas);
        tabbedPane.addTab("Usuarios", panelUsuarios);
        tabbedPane.addTab("Personal Medico", panelPersonalMedico);
        tabbedPane.addTab("Administracion", panelAdministracion);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    public PanelAgendarCita getPanelAgendarCita() {
        return panelAgendarCita;
    }
    
    public PanelMisCitas getPanelMisCitas() {
        return panelMisCitas;
    }
    
    public PanelUsuarios getPanelUsuarios() {
        return panelUsuarios;
    }
    
    public PanelPersonalMedico getPanelPersonalMedico() {
        return panelPersonalMedico;
    }
    
    public PanelAdministracion getPanelAdministracion() {
        return panelAdministracion;
    }
}