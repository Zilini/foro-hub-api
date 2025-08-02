package foro.hub.api.domain.topico.validaciones;

import foro.hub.api.domain.ValidacionException;
import foro.hub.api.domain.topico.DatosRegistroTopico;
import foro.hub.api.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaSiElMensajeYaExiste implements ValidadorDeTopicos{
    @Autowired
    private TopicoRepository topicoRepository;

    public void validar(DatosRegistroTopico datos) {
        if (topicoRepository.findByMensaje(datos.mensaje()).isPresent()) {
            throw new ValidacionException("Ya existe un tópico con el mensaje que escribiste.");
        }
    }

}
