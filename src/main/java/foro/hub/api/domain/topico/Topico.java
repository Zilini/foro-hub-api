package foro.hub.api.domain.topico;

import foro.hub.api.domain.curso.Curso;
import foro.hub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "topicos")
@Entity(name = "Topico")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cursos_nombre")
    private Curso curso;

    private String nombreCurso;

    public Topico(DatosRegistroTopico datos) {
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.nombreCurso = datos.nombreCurso();
    }
}
