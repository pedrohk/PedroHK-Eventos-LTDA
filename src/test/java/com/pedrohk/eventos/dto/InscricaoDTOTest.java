package com.pedrohk.eventos.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class InscricaoRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        InscricaoRequestDTO dto = new InscricaoRequestDTO(1L, 2L);

        Set<ConstraintViolation<InscricaoRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenEventoIdIsNull_thenValidationFails() {
        InscricaoRequestDTO dto = new InscricaoRequestDTO(null, 2L);

        Set<ConstraintViolation<InscricaoRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O ID do evento é obrigatório.");
    }

    @Test
    void whenParticipanteIdIsNull_thenValidationFails() {
        InscricaoRequestDTO dto = new InscricaoRequestDTO(1L, null);

        Set<ConstraintViolation<InscricaoRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O ID do participante é obrigatório.");
    }

    @Test
    void whenAllIdsAreNull_thenValidationFails() {
        InscricaoRequestDTO dto = new InscricaoRequestDTO(null, null);

        Set<ConstraintViolation<InscricaoRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(2);
    }
}