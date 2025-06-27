package com.pedrohk.eventos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoResponseDTO {

    private Long id;

    private EventoDTO evento;

    private ParticipanteDTO participante;

    private LocalDateTime dataInscricao;
}