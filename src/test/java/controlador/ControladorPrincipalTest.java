package controlador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ControladorPrincipalTest {
    
    @Test
    @DisplayName("Test de integracion - Verificar arquitectura MVC")
    void testArquitecturaMVC() {
        // Verificamos que las clases necesarias existen y son accesibles
        assertDoesNotThrow(() -> {
            Class.forName("controlador.ControladorPrincipal");
            Class.forName("vista.VentanaPrincipal");
            Class.forName("modelo.Cita");
            Class.forName("modelo.Usuario");
        });
    }
    
    @Test
    @DisplayName("Verificar que el paquete controlador esta correctamente estructurado")
    void testEstructuraControlador() {
        // Prueba de reflexion para verificar estructura
        try {
            Class<?> clazz = Class.forName("controlador.ControladorPrincipal");
            assertNotNull(clazz);
            assertTrue(clazz.getDeclaredMethods().length > 0);
        } catch (ClassNotFoundException e) {
            fail("Controlador principal no encontrado");
        }
    }
    
    @Test
    @DisplayName("Verificar disponibilidad de componentes MVC")
    void testComponentesMVCDisponibles() {
        assertDoesNotThrow(() -> {
            Class.forName("controlador.ControladorPrincipal");
            Class.forName("vista.PanelAgendarCita");
            Class.forName("vista.PanelMisCitas");
            Class.forName("servicio.GestorDisponibilidad");
        });
    }
}