package foro.hub.api.domain.usuario;

import jakarta.validation.constraints.Email;

import java.util.List;

public record DatosActualizacionUsuario(
        String nombre,
        @Email String correo,
        String contrasena,
        List<Long> idPerfiles
) {
}
