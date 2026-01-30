package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

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
    
    @Test
    @DisplayName("Setear motivo valido")
    void testSetearMotivo() {
        cita.setMotivo("Dolor de cabeza persistente");
        assertEquals("Dolor de cabeza persistente", cita.getMotivo());
    }
    
    @Test
    @DisplayName("Estados multiples en secuencia")
    void testEstadosMultiples() {
        assertEquals(Cita.EstadoCita.PENDIENTE, cita.getEstado());
        cita.confirmar();
        assertEquals(Cita.EstadoCita.CONFIRMADA, cita.getEstado());
        cita.completar();
        assertEquals(Cita.EstadoCita.COMPLETADA, cita.getEstado());
    }
    
    @Test
    @DisplayName("Paciente ID null lanza excepcion")
    void testPacienteIdNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita("CIT-003", null, "DOC-001", fechaHora, "Consulta General");
        });
    }
    
    @Test
    @DisplayName("Profesional ID null lanza excepcion")
    void testProfesionalIdNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita("CIT-004", "PAC-001", null, fechaHora, "Consulta General");
        });
    }
    
    @Test
    @DisplayName("Fecha null lanza excepcion")
    void testFechaNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cita("CIT-005", "PAC-001", "DOC-001", null, "Consulta General");
        });
    }
    
    @Test
    @DisplayName("Cita con tipo Especialista")
    void testCitaEspecialista() {
        Cita citaEsp = new Cita("CIT-006", "PAC-001", "DOC-002", fechaHora, "Especialista");
        assertEquals("Especialista", citaEsp.getTipo());
    }
    
    @Test
    @DisplayName("Cita con tipo Examenes")
    void testCitaExamenes() {
        Cita citaExam = new Cita("CIT-007", "PAC-001", "DOC-003", fechaHora, "Examenes");
        assertEquals("Examenes", citaExam.getTipo());
    }
    
    @Test
    @DisplayName("Cita con tipo Seguimiento")
    void testCitaSeguimiento() {
        Cita citaSeg = new Cita("CIT-008", "PAC-001", "DOC-004", fechaHora, "Seguimiento");
        assertEquals("Seguimiento", citaSeg.getTipo());
    }
    
    @Test
    @DisplayName("Confirmar cita cancelada lanza excepcion")
    void testConfirmarCitaCanceladaLanzaExcepcion() {
        cita.cancelar();
        assertThrows(IllegalStateException.class, () -> {
            cita.confirmar();
        });
    }
    
    @Test
    @DisplayName("Cancelar cita completada lanza excepcion")
    void testCancelarCitaCompletadaLanzaExcepcion() {
        cita.confirmar();
        cita.completar();
        assertThrows(IllegalStateException.class, () -> {
            cita.cancelar();
        });
    }
    
    @Test
    @DisplayName("Completar cita no confirmada lanza excepcion")
    void testCompletarCitaNoConfirmadaLanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> {
            cita.completar();
        });
    }
    
    @Test
    @DisplayName("Verificar si cita es en fecha")
    void testEsEnFecha() {
        assertTrue(cita.esEnFecha(fechaHora));
        assertFalse(cita.esEnFecha(fechaHora.plusDays(1)));
    }
    
    @Test
    @DisplayName("Verificar conflicto de citas por tiempo")
    void testTieneConflicto() {
        LocalDateTime horarioCercano = fechaHora.plusMinutes(15);
        assertTrue(cita.tieneConflicto(horarioCercano));
        
        LocalDateTime horarioLejano = fechaHora.plusMinutes(60);
        assertFalse(cita.tieneConflicto(horarioLejano));
    }
    
    @Test
    @DisplayName("Setear fecha hora valida")
    void testSetearFechaHora() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(2);
        cita.setFechaHora(nuevaFecha);
        assertEquals(nuevaFecha, cita.getFechaHora());
    }
    
    @Test
    @DisplayName("Setear tipo valido")
    void testSetearTipo() {
        cita.setTipo("Especialista");
        assertEquals("Especialista", cita.getTipo());
    }
    
    @Test
    @DisplayName("Equals con misma cita")
    void testEqualsMismaCita() {
        assertTrue(cita.equals(cita));
    }
    
    @Test
    @DisplayName("Equals con null")
    void testEqualsNull() {
        assertFalse(cita.equals(null));
    }
    
    @Test
    @DisplayName("Equals con objeto diferente")
    void testEqualsObjetoDiferente() {
        assertFalse(cita.equals("no es una cita"));
    }
    
    @Test
    @DisplayName("Equals con cita mismo ID")
    void testEqualsMismoId() {
        Cita otraCita = new Cita("CIT-001", "PAC-002", "DOC-002", fechaHora, "Consulta General");
        assertTrue(cita.equals(otraCita));
    }
    
    @Test
    @DisplayName("HashCode consistente")
    void testHashCode() {
        Cita otraCita = new Cita("CIT-001", "PAC-002", "DOC-002", fechaHora, "Consulta General");
        assertEquals(cita.hashCode(), otraCita.hashCode());
    }
}