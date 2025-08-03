package foro.hub.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GestorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream()
                .map(DatosErrorValidacion::new)
                .toList());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity gestionarError403(AuthorizationDeniedException ex) {
        var mensaje = "Acceso denegado. No estás autorizado para realizar esta acción.";
        return ResponseEntity.status(403).body(mensaje);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity gestionarErrorAccesoDenegado(AccessDeniedException ex) {
        var mensaje = "Acceso denegado. No estás autorizado para realizar esta acción.";
        return ResponseEntity.status(403).body(new DatosErrorAccesoDenegado(mensaje));
    }

    public record DatosErrorValidacion (String campo, String mensaje){
        public DatosErrorValidacion (FieldError error) {
            this(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }
    }

    public record DatosErrorAccesoDenegado(String mennsaje) {

    }
}
