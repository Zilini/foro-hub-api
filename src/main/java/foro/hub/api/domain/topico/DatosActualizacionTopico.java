package foro.hub.api.domain.topico;

import foro.hub.api.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

public record DatosActualizacionTopico(
        @NotBlank Long id,
        String titulo,
        String mensaje,
        Status status,
        Usuario autor
) {
}
