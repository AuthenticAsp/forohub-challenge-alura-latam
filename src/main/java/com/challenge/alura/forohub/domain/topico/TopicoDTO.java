package com.challenge.alura.forohub.domain.topico;

import java.time.LocalDateTime;

public record TopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion,
        Boolean status,
        String autor,
        String curso
) {
}
