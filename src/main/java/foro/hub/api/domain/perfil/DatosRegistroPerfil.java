package foro.hub.api.domain.perfil;

import jakarta.validation.constraints.NotNull;

public record DatosRegistroPerfil(
        @NotNull String nombre
) {
}
