package Principal;

import controlador.ControladorPrincipal;

import javax.swing.*;

/**
 * Clase principal del sistema de gestion de citas medicas
 * Lab 2 - Continuacion del Lab 1 con interfaz grafica
 * Lanza la aplicacion GUI usando patron MVC
 */
public class Ejecutable {
    
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestion de Citas Medicas - Lab 2");
        System.out.println("Cargando interfaz grafica...");
        
        SwingUtilities.invokeLater(() -> {
            try {
                new ControladorPrincipal();
                System.out.println("Aplicacion GUI iniciada exitosamente.");
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicacion: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
