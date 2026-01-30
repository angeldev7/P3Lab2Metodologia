package servicio;

import modelo.Cita;
import modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests para GestorDisponibilidad del Lab 2
 */
class GestorDisponibilidadTest {
    
    private GestorDisponibilidad gestor;
    private LocalDateTime fechaPrueba;
    
    @BeforeEach
    void setUp() {
        gestor = new GestorDisponibilidad();
        fechaPrueba = LocalDateTime.now().plusDays(1);
    }
    
    @Test
    @DisplayName("Configurar horarios para profesional")
    void testConfigurarHorarios() {
        Set<LocalDateTime> horarios = new HashSet<>();
        horarios.add(fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0));
        horarios.add(fechaPrueba.withHour(10).withMinute(0).withSecond(0).withNano(0));
        
        assertDoesNotThrow(() -> {
            gestor.configurarHorarios("DOC-001", horarios);
        });
    }
    
    @Test
    @DisplayName("Consultar disponibilidad sin horarios configurados")
    void testConsultarDisponibilidadSinHorarios() {
        List<LocalDateTime> disponibles = gestor.consultarDisponibilidad("DOC-999", fechaPrueba);
        assertTrue(disponibles.isEmpty());
    }
    
    @Test
    @DisplayName("Consultar disponibilidad con horarios configurados")
    void testConsultarDisponibilidadConHorarios() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario1 = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime horario2 = fechaPrueba.withHour(10).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario1);
        horarios.add(horario2);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        List<LocalDateTime> disponibles = gestor.consultarDisponibilidad("DOC-001", fechaPrueba);
        assertEquals(2, disponibles.size());
        assertTrue(disponibles.contains(horario1));
        assertTrue(disponibles.contains(horario2));
    }
    
    @Test
    @DisplayName("Agendar cita en horario disponible")
    void testAgendarCitaHorarioDisponible() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita = new Cita("CIT-001", "PAC-001", "DOC-001", horario, "Consulta general");
        assertTrue(gestor.agendarCita(cita));
    }
    
    @Test
    @DisplayName("Agendar cita en horario no disponible")
    void testAgendarCitaHorarioNoDisponible() {
        LocalDateTime horarioNoDisponible = fechaPrueba.withHour(15).withMinute(0).withSecond(0).withNano(0);
        
        Cita cita = new Cita("CIT-002", "PAC-001", "DOC-001", horarioNoDisponible, "Consulta general");
        assertFalse(gestor.agendarCita(cita));
    }
    
    @Test
    @DisplayName("Cancelar cita libera horario")
    void testCancelarCitaLiberaHorario() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita = new Cita("CIT-001", "PAC-001", "DOC-001", horario, "Consulta general");
        gestor.agendarCita(cita);
        
        List<LocalDateTime> disponiblesAntes = gestor.consultarDisponibilidad("DOC-001", fechaPrueba);
        assertEquals(0, disponiblesAntes.size());
        
        gestor.cancelarCita("CIT-001");
        
        List<LocalDateTime> disponiblesDespues = gestor.consultarDisponibilidad("DOC-001", fechaPrueba);
        assertEquals(1, disponiblesDespues.size());
        assertTrue(disponiblesDespues.contains(horario));
    }
    
    @Test
    @DisplayName("Verificar que constructor inicializa correctamente")
    void testConstructor() {
        GestorDisponibilidad nuevoGestor = new GestorDisponibilidad();
        assertNotNull(nuevoGestor);
        
        // Verificar que retorna lista vacia para profesional sin horarios
        List<LocalDateTime> disponibles = nuevoGestor.consultarDisponibilidad("DOC-999", fechaPrueba);
        assertNotNull(disponibles);
        assertTrue(disponibles.isEmpty());
    }
}