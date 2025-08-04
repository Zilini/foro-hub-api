package foro.hub.api.controller;

import foro.hub.api.domain.perfil.Perfil;
import foro.hub.api.domain.perfil.PerfilRepository;
import foro.hub.api.domain.usuario.*;
import foro.hub.api.domain.usuario.actualizaperfil.DatosActualizacionPerfil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriComponentsBuilder) {

        var contrasena = passwordEncoder.encode(datos.contrasena());
        var perfilPorDefecto = perfilRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new EntityNotFoundException("Perfil por defecto no encontrado."));
        var usuario = new Usuario(datos, contrasena, List.of(perfilPorDefecto));

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
    @PutMapping("/{id}/perfiles") // Nuevo endpoint más RESTful y claro
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity actualizarPerfiles(@PathVariable Long id, @RequestBody @Valid DatosActualizacionPerfil datos) {
        // 1. Encontrar el usuario por el ID del path variable
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        // 2. Usar un flujo de stream para buscar los perfiles y ser explícito en los tipos
        List<Perfil> perfilesEncontrados = datos.nuevosPerfiles().stream()
                .map(perfilEnum -> perfilRepository.findByNombre(perfilEnum.getNombre())
                        .orElseThrow(() -> new EntityNotFoundException("Perfil '" + perfilEnum.getNombre() + "' no encontrado.")))
                .collect(Collectors.toList()); // Usar .collect(Collectors.toList()) es más seguro en versiones anteriores a Java 16

        // 3. Limpiar los perfiles existentes y agregar los nuevos
        usuario.getPerfiles().clear();
        usuario.getPerfiles().addAll(perfilesEncontrados);

        // 4. Guardar los cambios
        usuarioRepository.save(usuario);

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
