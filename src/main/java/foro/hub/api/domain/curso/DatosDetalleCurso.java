package foro.hub.api.domain.curso;

public record DatosDetalleCurso(
        Long id,
        String nombre,
        Categoria categoria

) {
    public DatosDetalleCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
    }
}
