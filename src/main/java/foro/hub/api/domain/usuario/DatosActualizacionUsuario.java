package foro.hub.api.domain.usuario;

import jakarta.validation.constraints.Email;

public record DatosActualizacionUsuario(
        String nombre,
        @Email String correo,
        String contrasena
) {
}
