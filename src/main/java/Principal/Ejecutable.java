package Principal;

import controlador.ControladorPrincipal;

import javax.swing.*;

public class Ejecutable {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ControladorPrincipal();
        });
    }
}
