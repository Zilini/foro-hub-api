package foro.hub.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuario(
        String nombre,
        Perfil perfil
) {
}
