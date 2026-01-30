package modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa una cita médica en el sistema.
 * Basado en HU-001: Agendar Cita del Lab 1
 * 
 * Como paciente, quiero agendar una cita médica 
 * para que pueda recibir atención médica en el horario deseado.
 */
public class Cita {
    private String id;
    private String pacienteId;
    private String profesionalId;
    private LocalDateTime fechaHora;
    private String tipo;
    private String motivo;
    private EstadoCita estado;
    
    public enum EstadoCita {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA,
        COMPLETADA
    }
    
    // Constructor
    public Cita(String id, String pacienteId, String profesionalId, 
                LocalDateTime fechaHora, String tipo) {
        this.id = validarId(id);
        this.pacienteId = validarId(pacienteId);
        this.profesionalId = validarId(profesionalId);
        this.fechaHora = validarFechaHora(fechaHora);
        this.tipo = validarTipo(tipo);
        this.estado = EstadoCita.PENDIENTE;
    }
    
    // Métodos de validación
    private String validarId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
        }
        return id.trim();
    }
    
    private LocalDateTime validarFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no puede ser nula");
        }
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede agendar una cita en el pasado");
        }
        return fechaHora;
    }
    
    private String validarTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cita no puede ser nulo o vacío");
        }
        String tipoNormalizado = tipo.trim().toLowerCase();
        if (!tipoNormalizado.equals("consulta general") && 
            !tipoNormalizado.equals("especialista") &&
            !tipoNormalizado.equals("examenes") &&
            !tipoNormalizado.equals("seguimiento")) {
            throw new IllegalArgumentException("Tipo de cita no válido: " + tipo);
        }
        return tipo.trim();
    }
    
    // Métodos de negocio
    public void confirmar() {
        if (estado == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede confirmar una cita cancelada");
        }
        this.estado = EstadoCita.CONFIRMADA;
    }
    
    public void cancelar() {
        if (estado == EstadoCita.COMPLETADA) {
            throw new IllegalStateException("No se puede cancelar una cita completada");
        }
        this.estado = EstadoCita.CANCELADA;
    }
    
    public void completar() {
        if (estado != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se pueden completar citas confirmadas");
        }
        this.estado = EstadoCita.COMPLETADA;
    }
    
    public boolean esEnFecha(LocalDateTime fecha) {
        return fechaHora.toLocalDate().equals(fecha.toLocalDate());
    }
    
    public boolean tieneConflicto(LocalDateTime otraFechaHora) {
        // Considera conflicto si está en la misma hora (ventana de 30 minutos)
        return Math.abs(ChronoUnit.MINUTES.between(fechaHora, otraFechaHora)) < 30;
    }
    
    // Getters y Setters
    public String getId() { return id; }
    
    public String getPacienteId() { return pacienteId; }
    
    public String getProfesionalId() { return profesionalId; }
    
    public LocalDateTime getFechaHora() { return fechaHora; }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = validarFechaHora(fechaHora);
    }
    
    public String getTipo() { return tipo; }
    
    public void setTipo(String tipo) {
        this.tipo = validarTipo(tipo);
    }
    
    public String getMotivo() { return motivo; }
    
    public void setMotivo(String motivo) { 
        this.motivo = motivo; 
    }
    
    public EstadoCita getEstado() { return estado; }
    
    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(id, cita.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Cita{id='%s', paciente='%s', profesional='%s', " +
                           "fecha=%s, tipo='%s', estado=%s}",
                           id, pacienteId, profesionalId, fechaHora, tipo, estado);
    }
}