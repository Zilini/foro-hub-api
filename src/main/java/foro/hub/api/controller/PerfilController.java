package foro.hub.api.controller;

import foro.hub.api.domain.perfil.*;
import foro.hub.api.domain.perfil.validaciones.ValidadorDePerfiles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/perfiles")
@SecurityRequirement(name = "bearer-key")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private List<ValidadorDePerfiles> validadores;

    @Transactional
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity registrarPerfil(@RequestBody @Valid DatosRegistroPerfil datos, UriComponentsBuilder uriComponentsBuilder) {
        var perfil = new Perfil(datos);

        validadores.forEach(v -> v.validar(datos));

        perfilRepository.save(perfil);

        var uri = uriComponentsBuilder.path("/perfiles/{id}").buildAndExpand(perfil.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetallePerfil(perfil));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<DatosListaPerfil>> listarPerfiles(@PageableDefault(size = 5)Pageable paginacion) {
        var page = perfilRepository.findAll(paginacion)
                .map(DatosListaPerfil::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity eliminarPerfil(@PathVariable Long id) {
        perfilRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity detallarPerfil(@PathVariable Long id) {
        var perfil = perfilRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetallePerfil(perfil));
    }
}
