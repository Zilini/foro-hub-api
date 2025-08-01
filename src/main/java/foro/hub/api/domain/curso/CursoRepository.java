package foro.hub.api.domain.curso;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("""
            select c from Curso c
            where
            c.nombre = :nombre
            """)
    Optional<Curso> findByNombre(String nombre);
}
