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
import java.util.Map;
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
    
    @Test
    @DisplayName("Agendar multiples citas mismo profesional")
    void testAgendarMultiplesCitas() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario1 = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime horario2 = fechaPrueba.withHour(10).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario1);
        horarios.add(horario2);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita1 = new Cita("CIT-001", "PAC-001", "DOC-001", horario1, "Consulta general");
        Cita cita2 = new Cita("CIT-002", "PAC-002", "DOC-001", horario2, "Consulta general");
        
        assertTrue(gestor.agendarCita(cita1));
        assertTrue(gestor.agendarCita(cita2));
        
        List<LocalDateTime> disponibles = gestor.consultarDisponibilidad("DOC-001", fechaPrueba);
        assertEquals(0, disponibles.size());
    }
    
    @Test
    @DisplayName("Cancelar cita que no existe")
    void testCancelarCitaInexistente() {
        assertDoesNotThrow(() -> {
            gestor.cancelarCita("CIT-999");
        });
    }
    
    @Test
    @DisplayName("Consultar disponibilidad fecha diferente")
    void testConsultarDisponibilidadFechaDiferente() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horarioHoy = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horarioHoy);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        LocalDateTime fechaDiferente = fechaPrueba.plusDays(1);
        List<LocalDateTime> disponibles = gestor.consultarDisponibilidad("DOC-001", fechaDiferente);
        
        assertTrue(disponibles.isEmpty());
    }
    
    @Test
    @DisplayName("Configurar horarios set vacio lanza excepcion")
    void testConfigurarHorariosVacio() {
        Set<LocalDateTime> horariosVacios = new HashSet<>();
        
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.configurarHorarios("DOC-002", horariosVacios);
        });
    }
    
    @Test
    @DisplayName("Configurar horarios en el pasado lanza excepcion")
    void testConfigurarHorariosPasadoLanzaExcepcion() {
        Set<LocalDateTime> horariosPasados = new HashSet<>();
        horariosPasados.add(LocalDateTime.now().minusDays(1));
        
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.configurarHorarios("DOC-003", horariosPasados);
        });
    }
    
    @Test
    @DisplayName("Consultar disponibilidad con profesional ID null lanza excepcion")
    void testConsultarDisponibilidadProfesionalNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.consultarDisponibilidad(null, fechaPrueba);
        });
    }
    
    @Test
    @DisplayName("Consultar disponibilidad con fecha null lanza excepcion")
    void testConsultarDisponibilidadFechaNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.consultarDisponibilidad("DOC-001", null);
        });
    }
    
    @Test
    @DisplayName("Esta disponible retorna false con profesional null")
    void testEstaDisponibleProfesionalNull() {
        assertFalse(gestor.estaDisponible(null, fechaPrueba));
    }
    
    @Test
    @DisplayName("Esta disponible retorna false con fecha null")
    void testEstaDisponibleFechaNull() {
        assertFalse(gestor.estaDisponible("DOC-001", null));
    }
    
    @Test
    @DisplayName("Esta disponible retorna false si horario no configurado")
    void testEstaDisponibleHorarioNoConfigurado() {
        LocalDateTime horarioNoConfigurado = fechaPrueba.withHour(15).withMinute(0).withSecond(0).withNano(0);
        assertFalse(gestor.estaDisponible("DOC-001", horarioNoConfigurado));
    }
    
    @Test
    @DisplayName("Agendar cita null lanza excepcion")
    void testAgendarCitaNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.agendarCita(null);
        });
    }
    
    @Test
    @DisplayName("Agendar cita con conflicto de tiempo")
    void testAgendarCitaConConflicto() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita1 = new Cita("CIT-001", "PAC-001", "DOC-001", horario, "Consulta general");
        assertTrue(gestor.agendarCita(cita1));
        
        // Intentar agendar otra cita 15 minutos después (conflicto)
        LocalDateTime horarioCercano = horario.plusMinutes(15);
        Cita cita2 = new Cita("CIT-002", "PAC-002", "DOC-001", horarioCercano, "Consulta general");
        assertFalse(gestor.agendarCita(cita2));
    }
    
    @Test
    @DisplayName("Cancelar cita con ID null lanza excepcion")
    void testCancelarCitaIdNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.cancelarCita(null);
        });
    }
    
    @Test
    @DisplayName("Cancelar cita con ID vacio lanza excepcion")
    void testCancelarCitaIdVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.cancelarCita("   ");
        });
    }
    
    @Test
    @DisplayName("Cancelar cita existente")
    void testCancelarCitaExistente() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita = new Cita("CIT-001", "PAC-001", "DOC-001", horario, "Consulta general");
        gestor.agendarCita(cita);
        
        assertTrue(gestor.cancelarCita("CIT-001"));
        assertEquals(Cita.EstadoCita.CANCELADA, cita.getEstado());
    }
    
    @Test
    @DisplayName("Obtener citas de profesional ID null lanza excepcion")
    void testObtenerCitasProfesionalNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.obtenerCitasDeProfesional(null);
        });
    }
    
    @Test
    @DisplayName("Obtener citas de profesional ID vacio lanza excepcion")
    void testObtenerCitasProfesionalVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.obtenerCitasDeProfesional("   ");
        });
    }
    
    @Test
    @DisplayName("Obtener citas de profesional sin citas")
    void testObtenerCitasProfesionalSinCitas() {
        List<Cita> citas = gestor.obtenerCitasDeProfesional("DOC-999");
        assertTrue(citas.isEmpty());
    }
    
    @Test
    @DisplayName("Obtener citas activas de profesional")
    void testObtenerCitasActivasDeProfesional() {
        Set<LocalDateTime> horarios = new HashSet<>();
        LocalDateTime horario1 = fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime horario2 = fechaPrueba.withHour(10).withMinute(0).withSecond(0).withNano(0);
        horarios.add(horario1);
        horarios.add(horario2);
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita1 = new Cita("CIT-001", "PAC-001", "DOC-001", horario1, "Consulta general");
        Cita cita2 = new Cita("CIT-002", "PAC-002", "DOC-001", horario2, "Consulta general");
        
        gestor.agendarCita(cita1);
        gestor.agendarCita(cita2);
        gestor.cancelarCita("CIT-001");
        
        List<Cita> citasActivas = gestor.obtenerCitasActivasDeProfesional("DOC-001");
        assertEquals(1, citasActivas.size());
        assertEquals("CIT-002", citasActivas.get(0).getId());
    }
    
    @Test
    @DisplayName("Obtener estadisticas de disponibilidad")
    void testObtenerEstadisticasDisponibilidad() {
        Set<LocalDateTime> horarios = new HashSet<>();
        horarios.add(fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0));
        horarios.add(fechaPrueba.withHour(10).withMinute(0).withSecond(0).withNano(0));
        horarios.add(fechaPrueba.withHour(11).withMinute(0).withSecond(0).withNano(0));
        
        gestor.configurarHorarios("DOC-001", horarios);
        
        Cita cita = new Cita("CIT-001", "PAC-001", "DOC-001", 
            fechaPrueba.withHour(9).withMinute(0).withSecond(0).withNano(0), "Consulta general");
        gestor.agendarCita(cita);
        
        Map<String, Integer> estadisticas = gestor.obtenerEstadisticasDisponibilidad("DOC-001");
        
        assertEquals(3, estadisticas.get("horariosConfigurados"));
        assertEquals(1, estadisticas.get("horariosOcupados"));
        assertEquals(2, estadisticas.get("horariosLibres"));
    }
    
    @Test
    @DisplayName("Obtener estadisticas de profesional sin horarios")
    void testObtenerEstadisticasSinHorarios() {
        Map<String, Integer> estadisticas = gestor.obtenerEstadisticasDisponibilidad("DOC-999");
        
        assertEquals(0, estadisticas.get("horariosConfigurados"));
        assertEquals(0, estadisticas.get("horariosOcupados"));
        assertEquals(0, estadisticas.get("horariosLibres"));
    }
}