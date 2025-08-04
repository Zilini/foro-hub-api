package foro.hub.api.domain.usuario.actualizaperfil;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DatosActualizacionPerfil(
        @NotEmpty List<Perfiles> nuevosPerfiles
) {
}
