package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario("USR-001", "Juan", "Perez", "juan@email.com", Usuario.TipoUsuario.PACIENTE);
    }
    
    @Test
    @DisplayName("Crear usuario con datos validos")
    void testCrearUsuarioValido() {
        assertNotNull(usuario);
        assertEquals("USR-001", usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Perez", usuario.getApellido());
        assertEquals("juan@email.com", usuario.getEmail());
        assertEquals(Usuario.TipoUsuario.PACIENTE, usuario.getTipo());
        assertTrue(usuario.isActivo());
    }
    
    @Test
    @DisplayName("Nombre completo se forma correctamente")
    void testNombreCompleto() {
        assertEquals("Juan Perez", usuario.getNombreCompleto());
    }
    
    @Test
    @DisplayName("Usuario profesional medico")
    void testUsuarioProfesionalMedico() {
        Usuario doctor = new Usuario("DOC-001", "Ana", "Martinez", "ana@hospital.com", Usuario.TipoUsuario.PROFESIONAL_MEDICO);
        assertEquals(Usuario.TipoUsuario.PROFESIONAL_MEDICO, doctor.getTipo());
        assertEquals("Profesional Médico", doctor.getTipo().getDescripcion());
    }
    
    @Test
    @DisplayName("Usuario administrador")
    void testUsuarioAdministrador() {
        Usuario admin = new Usuario("ADM-001", "Admin", "Sistema", "admin@hospital.com", Usuario.TipoUsuario.ADMINISTRADOR);
        assertEquals(Usuario.TipoUsuario.ADMINISTRADOR, admin.getTipo());
        assertEquals("Administrador", admin.getTipo().getDescripcion());
    }
    
    @Test
    @DisplayName("Desactivar usuario")
    void testDesactivarUsuario() {
        usuario.desactivar();
        assertFalse(usuario.isActivo());
    }
    
    @Test
    @DisplayName("Activar usuario")
    void testActivarUsuario() {
        usuario.desactivar();
        usuario.activar();
        assertTrue(usuario.isActivo());
    }
    
    @Test
    @DisplayName("ID null lanza excepcion")
    void testIdNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(null, "Juan", "Perez", "juan@email.com", Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Email invalido lanza excepcion")
    void testEmailInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-002", "Juan", "Perez", "email-invalido", Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Nombre null lanza excepcion")
    void testNombreNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-003", null, "Perez", "test@email.com", Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Apellido null lanza excepcion")
    void testApellidoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-004", "Juan", null, "test@email.com", Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Email null lanza excepcion")
    void testEmailNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-005", "Juan", "Perez", null, Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Tipo null lanza excepcion")
    void testTipoNullLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-006", "Juan", "Perez", "test@email.com", null);
        });
    }
    
    @Test
    @DisplayName("Nombre vacio lanza excepcion")
    void testNombreVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-007", "", "Perez", "test@email.com", Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Apellido vacio lanza excepcion")
    void testApellidoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("USR-008", "Juan", "", "test@email.com", Usuario.TipoUsuario.PACIENTE);
        });
    }
    
    @Test
    @DisplayName("Descripcion de tipo PACIENTE")
    void testDescripcionTipoPaciente() {
        assertEquals("Paciente", Usuario.TipoUsuario.PACIENTE.getDescripcion());
    }
    
    @Test
    @DisplayName("Activar usuario ya activo")
    void testActivarUsuarioYaActivo() {
        assertTrue(usuario.isActivo());
        usuario.activar();
        assertTrue(usuario.isActivo());
    }
    
    @Test
    @DisplayName("Desactivar usuario ya inactivo")
    void testDesactivarUsuarioYaInactivo() {
        usuario.desactivar();
        assertFalse(usuario.isActivo());
        usuario.desactivar();
        assertFalse(usuario.isActivo());
    }
    
    @Test
    @DisplayName("Desactivar administrador lanza excepcion")
    void testDesactivarAdministradorLanzaExcepcion() {
        Usuario admin = new Usuario("ADM-001", "Admin", "Sistema", "admin@test.com", Usuario.TipoUsuario.ADMINISTRADOR);
        assertThrows(IllegalStateException.class, () -> {
            admin.desactivar();
        });
    }
    
    @Test
    @DisplayName("Paciente puede agendar citas")
    void testPacientePuedeAgendarCitas() {
        assertTrue(usuario.puedeAgendarCitas());
    }
    
    @Test
    @DisplayName("Recepcionista puede agendar citas")
    void testRecepcionistaPuedeAgendarCitas() {
        Usuario recep = new Usuario("REC-001", "Juan", "Perez", "recep@test.com", Usuario.TipoUsuario.RECEPCIONISTA);
        assertTrue(recep.puedeAgendarCitas());
    }
    
    @Test
    @DisplayName("Profesional medico no puede agendar citas")
    void testProfesionalNoAgendaCitas() {
        Usuario doc = new Usuario("DOC-001", "Ana", "Martinez", "doc@test.com", Usuario.TipoUsuario.PROFESIONAL_MEDICO);
        assertFalse(doc.puedeAgendarCitas());
    }
    
    @Test
    @DisplayName("Usuario inactivo no puede agendar citas")
    void testUsuarioInactivoNoAgendaCitas() {
        usuario.desactivar();
        assertFalse(usuario.puedeAgendarCitas());
    }
    
    @Test
    @DisplayName("Profesional medico puede gestionar horarios")
    void testProfesionalPuedeGestionarHorarios() {
        Usuario doc = new Usuario("DOC-001", "Ana", "Martinez", "doc@test.com", Usuario.TipoUsuario.PROFESIONAL_MEDICO);
        assertTrue(doc.puedeGestionarHorarios());
    }
    
    @Test
    @DisplayName("Administrador puede gestionar horarios")
    void testAdministradorPuedeGestionarHorarios() {
        Usuario admin = new Usuario("ADM-001", "Admin", "Sistema", "admin@test.com", Usuario.TipoUsuario.ADMINISTRADOR);
        assertTrue(admin.puedeGestionarHorarios());
    }
    
    @Test
    @DisplayName("Paciente no puede gestionar horarios")
    void testPacienteNoGestionaHorarios() {
        assertFalse(usuario.puedeGestionarHorarios());
    }
    
    @Test
    @DisplayName("Solo administrador puede administrar")
    void testSoloAdministradorPuedeAdministrar() {
        assertFalse(usuario.puedeAdministrar());
        Usuario admin = new Usuario("ADM-001", "Admin", "Sistema", "admin@test.com", Usuario.TipoUsuario.ADMINISTRADOR);
        assertTrue(admin.puedeAdministrar());
    }
    
    @Test
    @DisplayName("Setear nombre valido")
    void testSetearNombre() {
        usuario.setNombre("Carlos");
        assertEquals("Carlos", usuario.getNombre());
    }
    
    @Test
    @DisplayName("Setear apellido valido")
    void testSetearApellido() {
        usuario.setApellido("Garcia");
        assertEquals("Garcia", usuario.getApellido());
    }
    
    @Test
    @DisplayName("Setear email valido")
    void testSetearEmail() {
        usuario.setEmail("nuevo@email.com");
        assertEquals("nuevo@email.com", usuario.getEmail());
    }
    
    @Test
    @DisplayName("Setear telefono")
    void testSetearTelefono() {
        usuario.setTelefono("0999999999");
        assertEquals("0999999999", usuario.getTelefono());
    }
    
    @Test
    @DisplayName("Setear tipo valido")
    void testSetearTipo() {
        usuario.setTipo(Usuario.TipoUsuario.RECEPCIONISTA);
        assertEquals(Usuario.TipoUsuario.RECEPCIONISTA, usuario.getTipo());
    }
    
    @Test
    @DisplayName("Equals con mismo usuario")
    void testEqualsMismoUsuario() {
        assertTrue(usuario.equals(usuario));
    }
    
    @Test
    @DisplayName("Equals con null")
    void testEqualsNull() {
        assertFalse(usuario.equals(null));
    }
    
    @Test
    @DisplayName("Equals con objeto diferente")
    void testEqualsObjetoDiferente() {
        assertFalse(usuario.equals("no es usuario"));
    }
    
    @Test
    @DisplayName("Equals con usuario mismo ID")
    void testEqualsMismoId() {
        Usuario otroUsuario = new Usuario("USR-001", "Pedro", "Lopez", "otro@email.com", Usuario.TipoUsuario.ADMINISTRADOR);
        assertTrue(usuario.equals(otroUsuario));
    }
    
    @Test
    @DisplayName("HashCode consistente")
    void testHashCode() {
        Usuario otroUsuario = new Usuario("USR-001", "Pedro", "Lopez", "otro@email.com", Usuario.TipoUsuario.ADMINISTRADOR);
        assertEquals(usuario.hashCode(), otroUsuario.hashCode());
    }
    
    @Test
    @DisplayName("ToString contiene informacion basica")
    void testToString() {
        String usuarioStr = usuario.toString();
        assertTrue(usuarioStr.contains("USR-001"));
        assertTrue(usuarioStr.contains("Juan Perez"));
        assertTrue(usuarioStr.contains("Paciente"));
    }
    
    @Test
    @DisplayName("Descripcion tipo RECEPCIONISTA")
    void testDescripcionTipoRecepcionista() {
        assertEquals("Recepcionista", Usuario.TipoUsuario.RECEPCIONISTA.getDescripcion());
    }
    
    @Test
    @DisplayName("Descripcion tipo PROFESIONAL_MEDICO")
    void testDescripcionTipoProfesionalMedico() {
        assertEquals("Profesional Médico", Usuario.TipoUsuario.PROFESIONAL_MEDICO.getDescripcion());
    }
    
    @Test
    @DisplayName("Descripcion tipo ADMINISTRADOR")
    void testDescripcionTipoAdministrador() {
        assertEquals("Administrador", Usuario.TipoUsuario.ADMINISTRADOR.getDescripcion());
    }
}