package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    
    private final JTabbedPane tabbedPane;
    private final PanelAgendarCita panelAgendarCita;
    private final PanelMisCitas panelMisCitas;
    private final PanelUsuarios panelUsuarios;
    private final PanelPersonalMedico panelPersonalMedico;
    private final PanelAdministracion panelAdministracion;
    
    public VentanaPrincipal() {
        setTitle("Sistema de Gestion de Citas Medicas - Lab 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        panelAgendarCita = new PanelAgendarCita();
        panelMisCitas = new PanelMisCitas();
        panelUsuarios = new PanelUsuarios();
        panelPersonalMedico = new PanelPersonalMedico();
        panelAdministracion = new PanelAdministracion();
        
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