package foro.hub.api.domain.usuario;

import foro.hub.api.domain.topico.DatosRegistroTopico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity(name = "Usuario")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario(DatosRegistroUsuario datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.correo = datos.correo();
        this.contrasena = datos.contrasena();
        this.perfil = datos.perfil();
    }

    public void actualizarAutor(Usuario autor) {
        if (autor.nombre != null) {
            this.nombre = autor.nombre;
        }
    }
}
