package com.pedrohk.eventos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoRequestDTO {

    @NotNull(message = "O ID do evento é obrigatório.")
    private Long eventoId;

    @NotNull(message = "O ID do participante é obrigatório.")
    private Long participanteId;
}