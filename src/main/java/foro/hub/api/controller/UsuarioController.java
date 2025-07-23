package foro.hub.api.controller;

import foro.hub.api.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @PostMapping
    public void registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos) {
        usuarioRepository.save(new Usuario(datos));
    }

    @GetMapping
    public Page<DatosListaUsuario> listarUsuarios(@PageableDefault(size = 10, sort = {"nombre"})Pageable paginacion) {
        return usuarioRepository.findByActivoTrue(paginacion)
                .map(DatosListaUsuario::new);
    }

    @Transactional
    @PutMapping("/{id}")
    public void actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizacionUsuario datos){
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizarUsuario(datos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.eliminarUsuario();
    }
}
