package foro.hub.api.domain.usuario;

public record DatosListaUsuario(
        Long id,
        String nombre,
        String correo
) {
    public DatosListaUsuario(Usuario autor) {
        this(
                autor.getId(),
                autor.getNombre(),
                autor.getCorreo()
        );
    }
}
