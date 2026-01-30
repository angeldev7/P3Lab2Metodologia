package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel para agendar nuevas citas
 * Equivalente al formulario de agendamiento del prototipo HTML
 */
public class PanelAgendarCita extends JPanel {
    
    private JComboBox<String> comboTipoCita;
    private JComboBox<String> comboProfesional;
    private JSpinner spinnerFecha;
    private JComboBox<String> comboHorario;
    private JTextArea textAreaMotivo;
    private JButton btnConsultarDisponibilidad;
    private JButton btnAgendarCita;
    private JButton btnLimpiar;
    
    public PanelAgendarCita() {
        inicializarComponentes();
        configurarLayout();
    }
    
    private void inicializarComponentes() {
        // Tipo de cita (equivalente al select del HTML)
        String[] tiposCita = {"Consulta General", "Especialista", "Examenes", "Seguimiento"};
        comboTipoCita = new JComboBox<>(tiposCita);
        
        // Profesional (se llena dinamicamente)
        comboProfesional = new JComboBox<>();
        
        // Fecha (equivalente al calendario del HTML)
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spinnerFecha = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(dateEditor);
        
        // Horario (se llena tras consultar disponibilidad)
        comboHorario = new JComboBox<>();
        comboHorario.setEnabled(false);
        
        // Motivo
        textAreaMotivo = new JTextArea(3, 20);
        textAreaMotivo.setLineWrap(true);
        textAreaMotivo.setWrapStyleWord(true);
        
        // Botones
        btnConsultarDisponibilidad = new JButton("Consultar Disponibilidad");
        btnAgendarCita = new JButton("Agendar Cita");
        btnLimpiar = new JButton("Limpiar");
        
        btnAgendarCita.setEnabled(false);
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Titulo
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Agendar Nueva Cita");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelFormulario.add(lblTitulo, gbc);
        
        // Tipo de cita
        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("Tipo de Cita:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboTipoCita, gbc);
        
        // Profesional
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Profesional:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboProfesional, gbc);
        
        // Fecha
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(spinnerFecha, gbc);
        
        // Boton consultar disponibilidad
        gbc.gridy++; gbc.gridx = 1;
        panelFormulario.add(btnConsultarDisponibilidad, gbc);
        
        // Horario
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Horario:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(comboHorario, gbc);
        
        // Motivo
        gbc.gridy++; gbc.gridx = 0;
        panelFormulario.add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(new JScrollPane(textAreaMotivo), gbc);
        
        // Botones de accion
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnAgendarCita);
        panelBotones.add(btnLimpiar);
        panelFormulario.add(panelBotones, gbc);
        
        add(panelFormulario, BorderLayout.NORTH);
    }
    
    // Getters para el controlador
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