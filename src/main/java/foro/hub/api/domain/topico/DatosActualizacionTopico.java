package foro.hub.api.domain.topico;

public record DatosActualizacionTopico(
        String titulo,
        String mensaje,
        Status status
) {
}
