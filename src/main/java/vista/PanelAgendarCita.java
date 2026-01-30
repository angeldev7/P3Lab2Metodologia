package vista;

import javax.swing.*;
import java.awt.*;

public class PanelAgendarCita extends JPanel {
    
    private final JComboBox<String> comboTipoCita;
    private final JComboBox<String> comboProfesional;
    private final JSpinner spinnerFecha;
    private final JComboBox<String> comboHorario;
    private final JTextArea textAreaMotivo;
    private final JButton btnConsultarDisponibilidad;
    private final JButton btnAgendarCita;
    private final JButton btnLimpiar;
    
    public PanelAgendarCita() {
        String[] tiposCita = {"Consulta General", "Especialista", "Examenes", "Seguimiento"};
        comboTipoCita = new JComboBox<>(tiposCita);
        comboProfesional = new JComboBox<>();
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerFecha = new JSpinner(dateModel);
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        
        comboHorario = new JComboBox<>();
        comboHorario.setEnabled(false);
        
        textAreaMotivo = new JTextArea(3, 20);
        textAreaMotivo.setLineWrap(true);
        textAreaMotivo.setWrapStyleWord(true);
        
        btnConsultarDisponibilidad = new JButton("Consultar Disponibilidad");
        btnAgendarCita = new JButton("Agendar Cita");
        btnLimpiar = new JButton("Limpiar");
        btnAgendarCita.setEnabled(false);
        
        configurarLayout();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Agendar Nueva Cita");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelFormulario.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("Tipo de Cita:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboTipoCita, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Profesional:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboProfesional, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(spinnerFecha, gbc);
        
        gbc.gridy++; gbc.gridx = 1;
        panelFormulario.add(btnConsultarDisponibilidad, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Horario:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboHorario, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JScrollPane(textAreaMotivo), gbc);
        
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgendarCita);
        panelBotones.add(btnLimpiar);
        panelFormulario.add(panelBotones, gbc);
        
        add(panelFormulario, BorderLayout.NORTH);
    }
    
    public JComboBox<String> getComboTipoCita() {
        return comboTipoCita;
    }
    
    public JComboBox<String> getComboProfesional() {
        return comboProfesional;
    }
    
    public JSpinner getSpinnerFecha() {
        return spinnerFecha;
    }
    
    public JComboBox<String> getComboHorario() {
        return comboHorario;
    }
    
    public JTextArea getTextAreaMotivo() {
        return textAreaMotivo;
    }
    
    public JButton getBtnConsultarDisponibilidad() {
        return btnConsultarDisponibilidad;
    }
    
    public JButton getBtnAgendarCita() {
        return btnAgendarCita;
    }
    
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
    
    public void habilitarAgendamiento(boolean habilitar) {
        btnAgendarCita.setEnabled(habilitar);
        comboHorario.setEnabled(habilitar);
    }
    
    public void limpiarFormulario() {
        comboTipoCita.setSelectedIndex(0);
        comboProfesional.setSelectedIndex(0);
        comboHorario.removeAllItems();
        textAreaMotivo.setText("");
        habilitarAgendamiento(false);
    }
}