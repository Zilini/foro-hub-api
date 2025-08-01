package foro.hub.api.domain.curso.validaciones;

import foro.hub.api.domain.ValidacionException;
import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.curso.DatosRegistroCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaSiYaExisteCursoConMismoNombre implements ValidadorDeCursos{

    @Autowired
    private CursoRepository cursoRepository;

    public void validar(DatosRegistroCurso datos) {
        if (cursoRepository.findByNombre(datos.nombre()).isPresent()) {
            throw new ValidacionException("El nombre del curso que intenta registrar ya existe.");
        }
    }
}
