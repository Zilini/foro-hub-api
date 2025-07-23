package foro.hub.api.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotNull Long id,
        String titulo,
        String mensaje,
        Status status
) {
}
