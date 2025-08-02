package foro.hub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("""
            select t from Topico t
            where
            t.mensaje = :mensaje
            """)
    Optional<Topico> findByMensaje(String mensaje);

    @Query("""
            select t from Topico t
            where
            t.titulo = :titulo
            """)
    Optional<Topico> findByTitulo(String titulo);
}
