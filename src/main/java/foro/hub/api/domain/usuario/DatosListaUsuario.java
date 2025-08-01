package foro.hub.api.domain.usuario;

import foro.hub.api.domain.perfil.Perfil;

import java.util.List;
import java.util.stream.Collectors;

public record DatosListaUsuario(
        Long id,
        String nombre,
        String correo,
        List<String> perfiles
) {
    public DatosListaUsuario(Usuario autor) {
        this(
                autor.getId(),
                autor.getNombre(),
                autor.getCorreo(),
                autor.getPerfiles().stream()
                        .map(Perfil::getNombre)
                        .collect(Collectors.toList())
        );
    }
}
