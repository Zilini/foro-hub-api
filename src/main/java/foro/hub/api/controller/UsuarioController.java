package foro.hub.api.controller;

import foro.hub.api.domain.perfil.PerfilRepository;
import foro.hub.api.domain.topico.validaciones.ValidadorDeTopicos;
import foro.hub.api.domain.usuario.*;
import foro.hub.api.domain.usuario.validaciones.ValidadorDeUsuarios;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private List<ValidadorDeUsuarios> validadores;

    @Transactional
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {

        var perfiles = perfilRepository.findAllById(datos.idPerfiles());
        var usuario = new Usuario(datos, perfiles);

        usuarioRepository.save(usuario);

        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    public ResponseEntity<Page<DatosListaUsuario>> listarUsuarios(@PageableDefault(size = 10, sort = {"nombre"})Pageable paginacion) {
        var page = usuarioRepository.findByActivoTrue(paginacion)
                .map(DatosListaUsuario::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizacionUsuario datos){
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizarUsuario(datos);

        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.eliminarUsuario();

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity activarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.activarUsuario();

        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    public ResponseEntity detallarUsuario (@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }
}
