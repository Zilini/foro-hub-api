package foro.hub.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity gestionarAccesoDenegado(AccessDeniedException ex) {
        var mensaje = "No estás autorizado para realizar esta acción.";
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mensaje);
    }

    public record DatosErrorValidacion (String campo, String mensaje){
        public DatosErrorValidacion (FieldError error) {
            this(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }
    }
}
