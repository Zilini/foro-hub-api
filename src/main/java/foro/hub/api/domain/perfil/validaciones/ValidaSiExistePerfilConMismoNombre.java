package foro.hub.api.domain.perfil.validaciones;

import foro.hub.api.domain.ValidacionException;
import foro.hub.api.domain.perfil.DatosRegistroPerfil;
import foro.hub.api.domain.perfil.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaSiExistePerfilConMismoNombre implements ValidadorDePerfiles{

    @Autowired
    private PerfilRepository perfilRepository;

    public void validar(DatosRegistroPerfil datos) {
        if (perfilRepository.findByNombre(datos.nombre()).isPresent()) {
            throw new ValidacionException("Ya existe un perfil con ese nombre.");
        }
    }
}
