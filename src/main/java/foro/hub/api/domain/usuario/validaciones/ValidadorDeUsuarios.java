package foro.hub.api.domain.usuario.validaciones;

import foro.hub.api.domain.usuario.DatosRegistroUsuario;

public interface ValidadorDeUsuarios {
    void validar(DatosRegistroUsuario datos);
}
