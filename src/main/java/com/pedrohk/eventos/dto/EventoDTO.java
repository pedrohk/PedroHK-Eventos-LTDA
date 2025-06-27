package com.pedrohk.eventos.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Long id;

    @NotBlank(message = "O nome do evento não pode ser vazio.")
    private String nome;

    @NotNull(message = "A data do evento não pode ser nula.")
    @Future(message = "A data do evento deve ser no futuro.")
    private LocalDate data;

    @NotBlank(message = "O local do evento não pode ser vazio.")
    private String local;
}