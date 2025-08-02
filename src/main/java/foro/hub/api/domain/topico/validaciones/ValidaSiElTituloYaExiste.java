package foro.hub.api.domain.topico.validaciones;

import foro.hub.api.domain.ValidacionException;
import foro.hub.api.domain.topico.DatosRegistroTopico;
import foro.hub.api.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaSiElTituloYaExiste implements ValidadorDeTopicos{
    @Autowired
    private TopicoRepository topicoRepository;

    public void validar(DatosRegistroTopico datos) {
        if (topicoRepository.findByTitulo(datos.titulo()).isPresent()) {
            throw new ValidacionException("Ya existe un tópico con el título que escribiste.");

        }
    }
}
