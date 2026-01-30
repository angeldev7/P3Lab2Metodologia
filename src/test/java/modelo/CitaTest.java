package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Tests para la clase Cita del Lab 2
 * Validaciones de funcionalidad basica sin GUI
 */
class CitaTest {
    
    private Cita cita;
    private LocalDateTime fechaHora;
    
    @BeforeEach
    void setUp() {
        fechaHora = LocalDateTime.now().plusDays(1);
        cita = new Cita("CIT-001", "PAC-001", "DOC-001", fechaHora, "Consulta general");
    }
    
    @Test
    @DisplayName("Crear cita con datos validos")
    void testCrearCitaValida() {
        assertNotNull(cita);
        assertEquals("CIT-001", cita.getId());
        assertEquals("PAC-001", cita.getPacienteId());
        assertEquals("DOC-001", cita.getProfesionalId());
        assertEquals(fechaHora, cita.getFechaHora());
        assertEquals("Consulta general", cita.getTipo());
        assertEquals(Cita.EstadoCita.PENDIENTE, cita.getEstado());
        
        // El motivo se puede configurar por separado
        cita.setMotivo("Consulta de control");
        assertEquals("Consulta de control", cita.getMotivo());
    }
    
    @Test
    @DisplayName("Confirmar cita cambia estado")
    void testConfirmarCita() {
        cita.confirmar();
        assertEquals(Cita.EstadoCita.CONFIRMADA, cita.getEstado());
    }
    
    @Test
    @DisplayName("Cancelar cita cambia estado")
    void testCancelarCita() {
        cita.cancelar();
        assertEquals(Cita.EstadoCita.CANCELADA, cita.getEstado());
    }
    
    @Test
    @DisplayName("Completar cita cambia estado")
    void testCompletarCita() {
        cita.confirmar();
        cita.completar();
        assertEquals(Cita.EstadoCita.COMPLETADA, cita.getEstado());
    }
    
    @Test
    @DisplayName("ID null lanza excepcion")
    void testIdNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita(null, "PAC-001", "DOC-001", fechaHora, "Motivo");
        });
    }
    
    @Test
    @DisplayName("ID vacio lanza excepcion")
    void testIdVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita("", "PAC-001", "DOC-001", fechaHora, "Motivo");
        });
    }
    
    @Test
    @DisplayName("Fecha pasada lanza excepcion")
    void testFechaPasadaLanzaExcepcion() {
        LocalDateTime fechaPasada = LocalDateTime.now().minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita("CIT-002", "PAC-001", "DOC-001", fechaPasada, "Motivo");
        });
    }
    
    @Test
    @DisplayName("Tipo de cita invalido lanza excepcion")
    void testTipoCitaInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita("CIT-002", "PAC-001", "DOC-001", fechaHora, "Tipo invalido");
        });
    }
    
    @Test
    @DisplayName("Representacion toString incluye datos principales")
    void testToString() {
        String citaStr = cita.toString();
        assertTrue(citaStr.contains("CIT-001"));
        assertTrue(citaStr.contains("PAC-001"));
        assertTrue(citaStr.contains("DOC-001"));
        assertTrue(citaStr.contains("PENDIENTE"));
    }
}