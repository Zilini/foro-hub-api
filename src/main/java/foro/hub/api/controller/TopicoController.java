package foro.hub.api.controller;

import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.topico.*;
import foro.hub.api.domain.usuario.Usuario;
import foro.hub.api.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public void registroTopico(@RequestBody @Valid DatosRegistroTopico datos) {

        var autor = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Autor no encontrado."));
        var curso = cursoRepository.findByNombre(datos.nombreCurso())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado."));

        topicoRepository.save(new Topico(datos, autor, curso));
    }

    @GetMapping
    public Page<DatosListaTopico> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        return topicoRepository.findAll(paginacion)
                .map(DatosListaTopico::new);
    }

    @Transactional
    @PutMapping
    public void actualizarTopico(@RequestBody @Valid DatosActualizacionTopico datos){
        var topico = topicoRepository.getReferenceById(datos.id());
        topico.actualizarInformacion(datos);
    }
}
