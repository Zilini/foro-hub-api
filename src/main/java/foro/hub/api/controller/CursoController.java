package foro.hub.api.controller;

import foro.hub.api.domain.ValidacionException;
import foro.hub.api.domain.curso.*;
import foro.hub.api.domain.curso.validaciones.ValidadorDeCursos;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<ValidadorDeCursos> validadores;

    @Transactional
    @PostMapping
    public ResponseEntity registrarCurso(@RequestBody @Valid DatosRegistroCurso datos, UriComponentsBuilder uriComponentsBuilder) {
        var curso = new Curso(datos);

        validadores.forEach(v -> v.validar(datos));

        cursoRepository.save(curso);

        var uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaCurso>> listarCursos(@PageableDefault(size = 10, sort = {"nombre"})Pageable paginacion) {
        var page = cursoRepository.findAll(paginacion)
                .map(DatosListaCurso::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarCurso(@PathVariable Long id) {
        cursoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public  ResponseEntity detallarCurso(@PathVariable Long id) {
        var curso = cursoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetalleCurso(curso));
    }
}
