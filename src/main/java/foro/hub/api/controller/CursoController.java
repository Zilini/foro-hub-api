package foro.hub.api.controller;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.curso.DatosRegistroCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    @PostMapping
    public void registrarCurso(@RequestBody @Valid DatosRegistroCurso datos) {
        cursoRepository.save(new Curso(datos));
    }
}
