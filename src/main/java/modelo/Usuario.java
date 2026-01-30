package modelo;

import java.util.Objects;

public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private TipoUsuario tipo;
    private boolean activo;
    
    public enum TipoUsuario {
        PACIENTE("Paciente"),
        PROFESIONAL_MEDICO("Profesional Médico"),
        RECEPCIONISTA("Recepcionista"),
        ADMINISTRADOR("Administrador");
        
        private final String descripcion;
        
        TipoUsuario(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
    }
    
    // Constructor
    public Usuario(String id, String nombre, String apellido, String email, TipoUsuario tipo) {
        this.id = validarId(id);
        this.nombre = validarNombre(nombre);
        this.apellido = validarNombre(apellido);
        this.email = validarEmail(email);
        this.tipo = validarTipo(tipo);
        this.activo = true;
    }
    
    private String validarId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío");
        }
        return id.trim();
    }
    
    private String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (nombre.trim().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
        return nombre.trim();
    }
    
    private String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        String emailLimpio = email.trim().toLowerCase();
        if (!emailLimpio.contains("@") || !emailLimpio.contains(".")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        return emailLimpio;
    }
    
    private TipoUsuario validarTipo(TipoUsuario tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de usuario no puede ser nulo");
        }
        return tipo;
    }
    
    public void activar() {
        this.activo = true;
    }
    
    public void desactivar() {
        if (tipo == TipoUsuario.ADMINISTRADOR) {
            throw new IllegalStateException("No se puede desactivar un administrador");
        }
        this.activo = false;
    }
    
    public boolean puedeAgendarCitas() {
        return activo && (tipo == TipoUsuario.PACIENTE || tipo == TipoUsuario.RECEPCIONISTA);
    }
    
    public boolean puedeGestionarHorarios() {
        return activo && (tipo == TipoUsuario.PROFESIONAL_MEDICO || tipo == TipoUsuario.ADMINISTRADOR);
    }
    
    public boolean puedeAdministrar() {
        return activo && tipo == TipoUsuario.ADMINISTRADOR;
    }
    
    public String getNombreCompleto() {
        return String.format("%s %s", nombre, apellido);
    }
    
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = validarNombre(apellido);
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = validarEmail(email);
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public TipoUsuario getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoUsuario tipo) {
        this.tipo = validarTipo(tipo);
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Usuario{id='%s', nombre='%s %s', email='%s', tipo=%s, activo=%s}",
                           id, nombre, apellido, email, tipo.getDescripcion(), activo);
    }
}