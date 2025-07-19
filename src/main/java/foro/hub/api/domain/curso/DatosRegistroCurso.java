package foro.hub.api.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroCurso(
        @NotNull Long id,
        @NotBlank String nombre,
        @NotNull Categoria categoria
) {
}
