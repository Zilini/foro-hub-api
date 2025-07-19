package foro.hub.api.controller;

import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.topico.*;
import foro.hub.api.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        topicoRepository.save(new Topico(datos,autor ,curso));
    }

    @GetMapping
    public List<DatosListaTopico> listarTopicos() {
        return topicoRepository.findAll().stream()
                .map(DatosListaTopico::new)
                .toList();
    }
}
