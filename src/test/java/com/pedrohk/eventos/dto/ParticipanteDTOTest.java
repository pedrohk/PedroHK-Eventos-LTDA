package com.pedrohk.eventos.dto;

import com.pedrohk.eventos.model.CategoriaParticipante;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipanteDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        ParticipanteDTO dto = ParticipanteDTO.builder()
                .nome("Carlos Andrade")
                .email("carlos.andrade@gmail.org")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        Set<ConstraintViolation<ParticipanteDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenNomeIsBlank_thenValidationFails() {
        ParticipanteDTO dto = ParticipanteDTO.builder()
                .nome(" ")
                .email("carlos.andrade@gmail.org")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        Set<ConstraintViolation<ParticipanteDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome do participante não pode ser vazio.");
    }

    @Test
    void whenEmailIsInvalid_thenValidationFails() {
        ParticipanteDTO dto = ParticipanteDTO.builder()
                .nome("Carlos Andrade")
                .email("email-invalido")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        Set<ConstraintViolation<ParticipanteDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O email deve ser válido.");
    }

    @Test
    void whenEmailIsBlank_thenValidationFails() {
        ParticipanteDTO dto = ParticipanteDTO.builder()
                .nome("Carlos Andrade")
                .email("")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        Set<ConstraintViolation<ParticipanteDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O email do participante não pode ser vazio.");
    }

    @Test
    void whenCategoriaIsNull_thenValidationFails() {
        ParticipanteDTO dto = ParticipanteDTO.builder()
                .nome("Carlos Andrade")
                .email("carlos.andrade@gmail.org")
                .categoria(null)
                .build();

        Set<ConstraintViolation<ParticipanteDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A categoria do participante não pode ser nula.");
    }
}