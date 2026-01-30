package servicio;

import modelo.Cita;
import modelo.Usuario;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GestorDisponibilidad {
    private final Map<String, List<Cita>> citasPorProfesional;
    private final Map<String, Set<LocalDateTime>> horariosDisponibles;
    
    public GestorDisponibilidad() {
        this.citasPorProfesional = new HashMap<>();
        this.horariosDisponibles = new HashMap<>();
    }
    
    public void configurarHorarios(String profesionalId, Set<LocalDateTime> horarios) {
        if (profesionalId == null || profesionalId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del profesional no puede ser nulo o vacío");
        }
        if (horarios == null || horarios.isEmpty()) {
            throw new IllegalArgumentException("Los horarios no pueden ser nulos o vacíos");
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        for (LocalDateTime horario : horarios) {
            if (horario.isBefore(ahora)) {
                throw new IllegalArgumentException("No se pueden configurar horarios en el pasado");
            }
        }
        
        horariosDisponibles.put(profesionalId, new HashSet<>(horarios));
    }
    
    public List<LocalDateTime> consultarDisponibilidad(String profesionalId, LocalDateTime fecha) {
        if (profesionalId == null || profesionalId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del profesional no puede ser nulo o vacío");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        
        Set<LocalDateTime> horariosDelProfesional = horariosDisponibles.get(profesionalId);
        if (horariosDelProfesional == null) {
            return new ArrayList<>();
        }
        
        List<Cita> citasDelProfesional = citasPorProfesional.getOrDefault(profesionalId, new ArrayList<>());
        Set<LocalDateTime> horariosOcupados = citasDelProfesional.stream()
            .filter(cita -> cita.getEstado() != Cita.EstadoCita.CANCELADA)
            .filter(cita -> cita.esEnFecha(fecha))
            .map(Cita::getFechaHora)
            .collect(Collectors.toSet());
        
        return horariosDelProfesional.stream()
            .filter(horario -> horario.toLocalDate().equals(fecha.toLocalDate()))
            .filter(horario -> !horariosOcupados.contains(horario))
            .filter(horario -> horario.isAfter(LocalDateTime.now()))
            .sorted()
            .collect(Collectors.toList());
    }
    
    public boolean estaDisponible(String profesionalId, LocalDateTime fechaHora) {
        if (profesionalId == null || fechaHora == null) {
            return false;
        }
        
        Set<LocalDateTime> horariosDelProfesional = horariosDisponibles.get(profesionalId);
        if (horariosDelProfesional == null || !horariosDelProfesional.contains(fechaHora)) {
            return false;
        }
        
        List<Cita> citasDelProfesional = citasPorProfesional.getOrDefault(profesionalId, new ArrayList<>());
        return citasDelProfesional.stream()
            .filter(cita -> cita.getEstado() != Cita.EstadoCita.CANCELADA)
            .noneMatch(cita -> cita.getFechaHora().equals(fechaHora));
    }
    
    public boolean agendarCita(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }
        
        String profesionalId = cita.getProfesionalId();
        LocalDateTime fechaHora = cita.getFechaHora();
        
        if (!estaDisponible(profesionalId, fechaHora)) {
            return false;
        }
        
        List<Cita> citasDelProfesional = citasPorProfesional.getOrDefault(profesionalId, new ArrayList<>());
        boolean hayConflicto = citasDelProfesional.stream()
            .filter(citaExistente -> citaExistente.getEstado() != Cita.EstadoCita.CANCELADA)
            .anyMatch(citaExistente -> citaExistente.tieneConflicto(fechaHora));
        
        if (hayConflicto) {
            return false;
        }
        
        citasPorProfesional.computeIfAbsent(profesionalId, k -> new ArrayList<>()).add(cita);
        return true;
    }
    
    public boolean cancelarCita(String citaId) {
        if (citaId == null || citaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de la cita no puede ser nulo o vacío");
        }
        
        for (List<Cita> citas : citasPorProfesional.values()) {
            for (Cita cita : citas) {
                if (cita.getId().equals(citaId)) {
                    cita.cancelar();
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Cita> obtenerCitasDeProfesional(String profesionalId) {
        if (profesionalId == null || profesionalId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del profesional no puede ser nulo o vacío");
        }
        
        List<Cita> citas = citasPorProfesional.get(profesionalId);
        return citas != null ? new ArrayList<>(citas) : new ArrayList<>();
    }
    
    public List<Cita> obtenerCitasActivasDeProfesional(String profesionalId) {
        return obtenerCitasDeProfesional(profesionalId).stream()
            .filter(cita -> cita.getEstado() != Cita.EstadoCita.CANCELADA)
            .collect(Collectors.toList());
    }
    
    public Map<String, Integer> obtenerEstadisticasDisponibilidad(String profesionalId) {
        Map<String, Integer> estadisticas = new HashMap<>();
        
        Set<LocalDateTime> horariosTotal = horariosDisponibles.get(profesionalId);
        List<Cita> citasActivas = obtenerCitasActivasDeProfesional(profesionalId);
        
        int horariosConfigurados = horariosTotal != null ? horariosTotal.size() : 0;
        int horariosOcupados = citasActivas.size();
        int horariosLibres = horariosConfigurados - horariosOcupados;
        
        estadisticas.put("horariosConfigurados", horariosConfigurados);
        estadisticas.put("horariosOcupados", horariosOcupados);
        estadisticas.put("horariosLibres", Math.max(0, horariosLibres));
        
        return estadisticas;
    }
}