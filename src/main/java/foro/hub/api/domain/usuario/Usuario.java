package foro.hub.api.domain.usuario;

import foro.hub.api.domain.perfil.Perfil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "usuarios")
@Entity(name = "Usuario")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean activo;
    private String nombre;
    private String correo;
    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"), //Para la clave Foranea de Usuario
            inverseJoinColumns = @JoinColumn(name = "perfil_id") //Para la clave Foranea de Perfil
    )
    private List<Perfil> perfiles;


    public Usuario(DatosRegistroUsuario datos, String contrasena, List<Perfil> perfiles) {
        this.id = null;
        this.activo = true;
        this.nombre = datos.nombre();
        this.correo = datos.correo();
        this.contrasena = contrasena;
        this.perfiles = perfiles;
    }

    public void actualizarUsuario(DatosActualizacionUsuario datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
    }

    public void eliminarUsuario() {
        this.activo = false;
    }

    public void activarUsuario() {
        this.activo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfiles.stream()
                .map(perfil -> new SimpleGrantedAuthority("ROLE_" + perfil.getNombre().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
