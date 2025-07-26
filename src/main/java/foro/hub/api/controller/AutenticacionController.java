package foro.hub.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import foro.hub.api.domain.usuario.DatosAutenticacion;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;

    public ResponseEntity iniciarSecion(@RequestBody @Valid DatosAutenticacion datos) {
        var token = new UsernamePasswordAuthenticationToken(datos.correo(), datos.contrasena());
        var  autenticacion = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
