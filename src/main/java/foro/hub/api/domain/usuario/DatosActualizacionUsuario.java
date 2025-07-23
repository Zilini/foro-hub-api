package foro.hub.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuario(
        //@NotNull Long id,
        String nombre,
        Perfil perfil
) {
}
