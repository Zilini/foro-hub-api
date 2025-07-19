package foro.hub.api.domain.usuario;

public record DatosDetalleUsuario(
        Long id,
        String nombre,
        String correo,
        Perfil perfil

) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getPerfil()
        );
    }
}
