package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para la clase Usuario del Lab 2
 */
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
}