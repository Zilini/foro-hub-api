package foro.hub.api.controller;

import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.curso.DatosDetalleCurso;
import foro.hub.api.domain.topico.*;
import foro.hub.api.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registroTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {

        var autor = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado."));
        var curso = cursoRepository.findByNombre(datos.nombreCurso())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado."));
        var topico = new Topico(datos, autor, curso);
        topicoRepository.save(topico);

        var uri = uriComponentsBuilder.path("/cursos/i{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        var page = topicoRepository.findAll(paginacion)
                .map(DatosListaTopico::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos){
        var topico = topicoRepository.getReferenceById(id);
        topico.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void eliminarTopico(@PathVariable Long id) {
        topicoRepository.deleteById(id);
    }
}
