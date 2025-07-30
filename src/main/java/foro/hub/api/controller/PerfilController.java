package foro.hub.api.controller;

import foro.hub.api.domain.perfil.*;
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
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrarPerfil(@RequestBody @Valid DatosRegistroPerfil datos, UriComponentsBuilder uriComponentsBuilder) {
        var perfil = new Perfil(datos);
        perfilRepository.save(perfil);

        var uri = uriComponentsBuilder.path("/perfiles/{id}").buildAndExpand(perfil.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosDetallePerfil(perfil));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaPerfil>> listarPerfiles(@PageableDefault(size = 5)Pageable paginacion) {
        var page = perfilRepository.findAll(paginacion)
                .map(DatosListaPerfil::new);

        return ResponseEntity.ok(page);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPerfil(@PathVariable Long id) {
        perfilRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarPerfil(@PathVariable Long id) {
        var perfil = perfilRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetallePerfil(perfil));
    }
}
