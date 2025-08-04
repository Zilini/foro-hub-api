package foro.hub.api.domain.usuario.actualizaperfil;

public enum Perfiles {
    ADMIN("ADMIN"),
    DOCENTE("DOCENTE"),
    ESTUDIANTE("ESTUDIANTE"),
    MODERADOR("MODERADOR"),
    USUARIO("USUARIO");

    private String nombre;

    Perfiles(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}
