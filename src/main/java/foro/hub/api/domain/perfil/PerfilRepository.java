package foro.hub.api.domain.perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("""
            select p from Perfil p
            where
            p.nombre = :nombre
            """)
    Optional<Perfil> findByNombre(String nombre);
}
