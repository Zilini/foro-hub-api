package foro.hub.api.controller;

import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.topico.*;
import foro.hub.api.domain.topico.validaciones.ValidadorDeTopicos;
import foro.hub.api.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidadorDeTopicos> validadores;

    @Transactional
    @PostMapping
    public ResponseEntity registroTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {

        var autor = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado."));
        var curso = cursoRepository.findByNombre(datos.nombreCurso())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado."));
        var topico = new Topico(datos, autor, curso);

        validadores.forEach(v -> v.validar(datos));

        topicoRepository.save(topico);

        var uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        var page = topicoRepository.findAll(paginacion)
                .map(DatosListaTopico::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("""
            hasAnyRole('MODERADOR', 'DOCENTE')
            or (#topicoRepository.findById(#id).isPresent()
            and #topicoRepository.findById(#id).get().getAutor().getId() == authentication.principal.id)
            """)
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));
        topico.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("""
            hasAnyRole('MODERADOR', 'ADMIN')
            or (#topicoRepository.findById(#id).isPresent()
            and #topicoRepository.findById(#id).get().getAutor().getId() == authentication.principal.id)
            """)
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Tópico no encontrado con el ID:" + id);
        }
    }
}
