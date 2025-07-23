package foro.hub.api.controller;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.curso.DatosListaCurso;
import foro.hub.api.domain.curso.DatosRegistroCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Page<DatosListaCurso> listarCursos(@PageableDefault(size = 10, sort = {"categoria"})Pageable paginacion) {
        return cursoRepository.findAll(paginacion)
                .map(DatosListaCurso::new);
    }
}
