package foro.hub.api.domain.usuario;

import foro.hub.api.domain.perfil.Perfil;

import java.util.List;
import java.util.stream.Collectors;

public record DatosDetalleUsuario(
        Long id,
        String nombre,
        String correo,
        List<String> perfiles

) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getPerfiles().stream()
                        .map(Perfil::getNombre)
                        .collect(Collectors.toList())
        );
    }
}
