package foro.hub.api.controller;

import foro.hub.api.domain.curso.CursoRepository;
import foro.hub.api.domain.topico.DatosRegistroTopico;
import foro.hub.api.domain.topico.Topico;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        var autor = usuarioRepository.findById(datos.idUsuario());
        var curso = cursoRepository.findByNombre(datos.nombreCurso())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado."));

        topicoRepository.save(new Topico(datos));
    }
}
