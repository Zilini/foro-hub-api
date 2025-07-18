package foro.hub.api.domain.topico;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotNull Long idUsuario,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull String nombreCurso
        ) {
}
