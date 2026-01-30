package controlador;

import modelo.Cita;
import modelo.Usuario;
import servicio.GestorDisponibilidad;
import vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ControladorPrincipal {
    
    private VentanaPrincipal ventanaPrincipal;
    private GestorDisponibilidad gestorDisponibilidad;
    private Map<String, Usuario> usuarios;
    private Map<String, Cita> citas;
    private int contadorCitas;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public ControladorPrincipal() {
        inicializarModelo();
        inicializarVista();
        configurarEventos();
        cargarDatosIniciales();
    }
    
    private void inicializarModelo() {
        gestorDisponibilidad = new GestorDisponibilidad();
        usuarios = new HashMap<>();
        citas = new HashMap<>();
        contadorCitas = 1;
        
        crearUsuariosPrueba();
        configurarHorariosMedicos();
    }
    
    private void inicializarVista() {
        ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
    
    private void configurarEventos() {
        PanelAgendarCita panelAgendar = ventanaPrincipal.getPanelAgendarCita();
        panelAgendar.getBtnConsultarDisponibilidad().addActionListener(this::consultarDisponibilidad);
        panelAgendar.getBtnAgendarCita().addActionListener(this::agendarCita);
        panelAgendar.getBtnLimpiar().addActionListener(this::limpiarFormulario);
        
        PanelMisCitas panelMisCitas = ventanaPrincipal.getPanelMisCitas();
        panelMisCitas.getBtnActualizar().addActionListener(this::actualizarMisCitas);
        panelMisCitas.getBtnCancelarCita().addActionListener(this::cancelarCita);
        panelMisCitas.getBtnReprogramarCita().addActionListener(this::reprogramarCita);
        
        PanelAdministracion panelAdmin = ventanaPrincipal.getPanelAdministracion();
        panelAdmin.getBtnGenerarReporte().addActionListener(this::generarReporte);
        panelAdmin.getBtnExportarDatos().addActionListener(this::exportarDatos);
        
        PanelPersonalMedico panelPersonal = ventanaPrincipal.getPanelPersonalMedico();
        panelPersonal.getBtnVerCitas().addActionListener(this::verCitasPersonalMedico);
    }
    
    private void cargarDatosIniciales() {
        cargarProfesionalesEnCombo();
        actualizarMisCitas(null);
        actualizarEstadisticasAdmin();
        cargarUsuariosEnTabla();
    }
    
    private void crearUsuariosPrueba() {
        usuarios.put("PAC-001", new Usuario("PAC-001", "Juan", "Perez", "juan@email.com", Usuario.TipoUsuario.PACIENTE));
        usuarios.put("PAC-002", new Usuario("PAC-002", "Maria", "Garcia", "maria@email.com", Usuario.TipoUsuario.PACIENTE));
        usuarios.put("DOC-001", new Usuario("DOC-001", "Dr. Ana", "Martinez", "ana@hospital.com", Usuario.TipoUsuario.PROFESIONAL_MEDICO));
        usuarios.put("DOC-002", new Usuario("DOC-002", "Dr. Carlos", "Lopez", "carlos@hospital.com", Usuario.TipoUsuario.PROFESIONAL_MEDICO));
        usuarios.put("ADM-001", new Usuario("ADM-001", "Admin", "Sistema", "admin@hospital.com", Usuario.TipoUsuario.ADMINISTRADOR));
    }
    
    private void configurarHorariosMedicos() {
        Set<LocalDateTime> horarios1 = new HashSet<>();
        Set<LocalDateTime> horarios2 = new HashSet<>();
        
        LocalDateTime fechaBase = LocalDateTime.now().plusDays(1);
        
        for (int dia = 0; dia < 7; dia++) {
            LocalDateTime fecha = fechaBase.plusDays(dia);
            String[] horas = {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", 
                            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"};
            for (String hora : horas) {
                String[] partes = hora.split(":");
                LocalDateTime horarioCompleto = fecha
                    .withHour(Integer.parseInt(partes[0]))
                    .withMinute(Integer.parseInt(partes[1]))
                    .withSecond(0).withNano(0);
                horarios1.add(horarioCompleto);
                horarios2.add(horarioCompleto);
            }
        }
        
        gestorDisponibilidad.configurarHorarios("DOC-001", horarios1);
        gestorDisponibilidad.configurarHorarios("DOC-002", horarios2);
    }
    
    private void cargarProfesionalesEnCombo() {
        JComboBox<String> combo = ventanaPrincipal.getPanelAgendarCita().getComboProfesional();
        combo.removeAllItems();
        
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getTipo() == Usuario.TipoUsuario.PROFESIONAL_MEDICO) {
                combo.addItem(usuario.getId() + " - " + usuario.getNombreCompleto());
            }
        }
    }
    
    private void consultarDisponibilidad(ActionEvent e) {
        PanelAgendarCita panel = ventanaPrincipal.getPanelAgendarCita();
        
        try {
            String profesionalSeleccionado = (String) panel.getComboProfesional().getSelectedItem();
            if (profesionalSeleccionado == null) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "Seleccione un profesional");
                return;
            }
            
            String profesionalId = profesionalSeleccionado.split(" - ")[0];
            Date fechaSeleccionada = (Date) panel.getSpinnerFecha().getValue();
            LocalDateTime fecha = new java.sql.Timestamp(fechaSeleccionada.getTime()).toLocalDateTime();
            
            List<LocalDateTime> horariosDisponibles = gestorDisponibilidad.consultarDisponibilidad(profesionalId, fecha);
            
            JComboBox<String> comboHorario = panel.getComboHorario();
            comboHorario.removeAllItems();
            
            for (LocalDateTime horario : horariosDisponibles) {
                comboHorario.addItem(horario.format(timeFormatter));
            }
            
            if (horariosDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "No hay horarios disponibles para esta fecha");
                panel.habilitarAgendamiento(false);
            } else {
                panel.habilitarAgendamiento(true);
                JOptionPane.showMessageDialog(ventanaPrincipal, 
                    "Se encontraron " + horariosDisponibles.size() + " horarios disponibles");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Error al consultar disponibilidad: " + ex.getMessage());
        }
    }
    
    private void agendarCita(ActionEvent e) {
        PanelAgendarCita panel = ventanaPrincipal.getPanelAgendarCita();
        
        try {
            String tipoCita = (String) panel.getComboTipoCita().getSelectedItem();
            String profesionalInfo = (String) panel.getComboProfesional().getSelectedItem();
            String profesionalId = profesionalInfo.split(" - ")[0];
            
            Date fechaSeleccionada = (Date) panel.getSpinnerFecha().getValue();
            String horarioSeleccionado = (String) panel.getComboHorario().getSelectedItem();
            
            if (horarioSeleccionado == null) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "Seleccione un horario");
                return;
            }
            
            LocalDateTime fechaBase = new java.sql.Timestamp(fechaSeleccionada.getTime()).toLocalDateTime();
            String[] partesHora = horarioSeleccionado.split(":");
            LocalDateTime fechaHoraCompleta = fechaBase
                .withHour(Integer.parseInt(partesHora[0]))
                .withMinute(Integer.parseInt(partesHora[1]))
                .withSecond(0).withNano(0);
            
            String motivo = panel.getTextAreaMotivo().getText().trim();
            if (motivo.isEmpty()) {
                motivo = tipoCita;
            }
            
            String citaId = "CIT-" + String.format("%03d", contadorCitas++);
            Cita nuevaCita = new Cita(citaId, "PAC-001", profesionalId, fechaHoraCompleta, motivo);
            
            if (gestorDisponibilidad.agendarCita(nuevaCita)) {
                nuevaCita.confirmar();
                citas.put(citaId, nuevaCita);
                
                JOptionPane.showMessageDialog(ventanaPrincipal, 
                    "Cita agendada exitosamente!\nCodigo: " + citaId);
                
                panel.limpiarFormulario();
                actualizarMisCitas(null);
                actualizarEstadisticasAdmin();
            } else {
                JOptionPane.showMessageDialog(ventanaPrincipal, "Error: No se pudo agendar la cita");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ventanaPrincipal, "Error al agendar cita: " + ex.getMessage());
        }
    }
    
    private void limpiarFormulario(ActionEvent e) {
        ventanaPrincipal.getPanelAgendarCita().limpiarFormulario();
    }
    
    private void actualizarMisCitas(ActionEvent e) {
        PanelMisCitas panel = ventanaPrincipal.getPanelMisCitas();
        panel.limpiarTabla();
        
        for (Cita cita : citas.values()) {
            Usuario paciente = usuarios.get(cita.getPacienteId());
            Usuario profesional = usuarios.get(cita.getProfesionalId());
            
            Object[] fila = {
                cita.getId(),
                cita.getFechaHora().format(formatter),
                cita.getFechaHora().format(timeFormatter),
                profesional.getNombreCompleto(),
                "Consulta",
                cita.getMotivo(),
                cita.getEstado().toString()
            };
            
            panel.agregarCita(fila);
        }
    }
    
    private void cancelarCita(ActionEvent e) {
        PanelMisCitas panel = ventanaPrincipal.getPanelMisCitas();
        String citaId = panel.getCitaSeleccionada();
        
        if (citaId != null) {
            int confirmacion = JOptionPane.showConfirmDialog(ventanaPrincipal, 
                "¿Esta seguro que desea cancelar la cita " + citaId + "?",
                "Confirmar Cancelacion", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                Cita cita = citas.get(citaId);
                if (cita != null) {
                    cita.cancelar();
                    gestorDisponibilidad.cancelarCita(citaId);
                    actualizarMisCitas(null);
                    actualizarEstadisticasAdmin();
                    JOptionPane.showMessageDialog(ventanaPrincipal, "Cita cancelada exitosamente");
                }
            }
        }
    }
    
    private void reprogramarCita(ActionEvent e) {
        JOptionPane.showMessageDialog(ventanaPrincipal, "Funcionalidad de reprogramacion disponible");
    }
    
    private void generarReporte(ActionEvent e) {
        PanelAdministracion panel = ventanaPrincipal.getPanelAdministracion();
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("REPORTE DEL SISTEMA DE GESTION DE CITAS\n");
        reporte.append("=========================================\n\n");
        
        reporte.append("RESUMEN EJECUTIVO:\n");
        reporte.append("- Total de citas en el sistema: ").append(citas.size()).append("\n");
        reporte.append("- Total de usuarios registrados: ").append(usuarios.size()).append("\n");
        
        long doctoresActivos = usuarios.values().stream()
            .filter(u -> u.getTipo() == Usuario.TipoUsuario.PROFESIONAL_MEDICO && u.isActivo())
            .count();
        reporte.append("- Doctores activos: ").append(doctoresActivos).append("\n\n");
        
        reporte.append("DETALLE DE CITAS POR ESTADO:\n");
        for (Cita.EstadoCita estado : Cita.EstadoCita.values()) {
            long count = citas.values().stream()
                .filter(c -> c.getEstado() == estado)
                .count();
            reporte.append("- ").append(estado).append(": ").append(count).append("\n");
        }
        
        panel.getTextAreaReporte().setText(reporte.toString());
    }
    
    private void exportarDatos(ActionEvent e) {
        JOptionPane.showMessageDialog(ventanaPrincipal, "Funcionalidad de exportacion disponible");
    }
    
    private void verCitasPersonalMedico(ActionEvent e) {
        JOptionPane.showMessageDialog(ventanaPrincipal, "Vista de citas para personal medico disponible");
    }
    
    private void cargarUsuariosEnTabla() {
        PanelUsuarios panel = ventanaPrincipal.getPanelUsuarios();
        panel.getModeloTabla().setRowCount(0);
        
        for (Usuario usuario : usuarios.values()) {
            Object[] fila = {
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTipo().getDescripcion(),
                usuario.isActivo() ? "Activo" : "Inactivo"
            };
            panel.getModeloTabla().addRow(fila);
        }
    }
    
    private void actualizarEstadisticasAdmin() {
        PanelAdministracion panel = ventanaPrincipal.getPanelAdministracion();
        
        int totalCitas = citas.size();
        long confirmadas = citas.values().stream().filter(c -> c.getEstado() == Cita.EstadoCita.CONFIRMADA).count();
        long pendientes = citas.values().stream().filter(c -> c.getEstado() == Cita.EstadoCita.PENDIENTE).count();
        long canceladas = citas.values().stream().filter(c -> c.getEstado() == Cita.EstadoCita.CANCELADA).count();
        
        int totalUsuarios = usuarios.size();
        int doctoresActivos = (int) usuarios.values().stream()
            .filter(u -> u.getTipo() == Usuario.TipoUsuario.PROFESIONAL_MEDICO && u.isActivo())
            .count();
        
        double eficiencia = totalCitas > 0 ? (double) confirmadas / totalCitas * 100 : 0;
        
        panel.actualizarEstadisticas(totalCitas, (int)confirmadas, (int)pendientes, 
                                   (int)canceladas, totalUsuarios, doctoresActivos, eficiencia);
    }
    

}