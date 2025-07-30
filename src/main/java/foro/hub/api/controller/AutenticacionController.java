package foro.hub.api.controller;

import foro.hub.api.domain.usuario.DatosAutenticacion;
import foro.hub.api.domain.usuario.Usuario;
import foro.hub.api.infra.security.TokenService;
import foro.hub.api.infra.security.datosTokenJWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

 @PostMapping
 public ResponseEntity iniciarSecion (@RequestBody @Valid DatosAutenticacion datos) {
     var atenticationToken = new UsernamePasswordAuthenticationToken(datos.correo(),datos.contrasena());
     var autenticacion = manager.authenticate(atenticationToken);

     var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());

     return ResponseEntity.ok(new datosTokenJWT(tokenJWT));
 }
}