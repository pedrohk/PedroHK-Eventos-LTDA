package com.pedrohk.eventos.dto;

import com.pedrohk.eventos.model.CategoriaParticipante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteDTO {

    private Long id;

    @NotBlank(message = "O nome do participante não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O email do participante não pode ser vazio.")
    @Email(message = "O email deve ser válido.")
    private String email;

    @NotNull(message = "A categoria do participante não pode ser nula.")
    private CategoriaParticipante categoria;
}