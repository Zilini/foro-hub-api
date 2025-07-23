package foro.hub.api.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    private Boolean activo;
    private String nombre;
    private String correo;
    private String contrasena;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario(DatosRegistroUsuario datos) {
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.correo = datos.correo();
        this.contrasena = datos.contrasena();
        this.perfil = datos.perfil();
    }

    public void actualizarUsuario(DatosActualizacionUsuario datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.perfil() != null) {
            this.perfil = datos.perfil();
        }
    }

    public void eliminarUsuario() {
        this.activo = false;
    }
}
