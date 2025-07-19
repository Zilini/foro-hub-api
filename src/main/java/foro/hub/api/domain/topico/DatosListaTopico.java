package foro.hub.api.domain.topico;

import java.time.LocalDateTime;

public record DatosListaTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Status satatus,
        String nombreAutor,
        String nombreCurso
) {
    public DatosListaTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}
