package foro.hub.api.domain.perfil;

import foro.hub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Table(name = "perfiles")
@Entity(name = "Perfil")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "perfiles", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios;

    public Perfil(DatosRegistroPerfil datos) {
        this.nombre = datos.nombre();
        this.usuarios = new HashSet<>();
    }
}
