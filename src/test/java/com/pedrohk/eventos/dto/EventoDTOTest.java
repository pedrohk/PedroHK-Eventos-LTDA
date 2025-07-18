package com.pedrohk.eventos.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EventoDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        EventoDTO eventoDTO = EventoDTO.builder()
                .nome("Conferência de Tecnologia")
                .data(LocalDate.now().plusDays(1))
                .local("Centro de Convenções")
                .build();

        Set<ConstraintViolation<EventoDTO>> violations = validator.validate(eventoDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenNomeIsBlank_thenValidationFails() {
        EventoDTO eventoDTO = EventoDTO.builder()
                .nome(" ")
                .data(LocalDate.now().plusDays(1))
                .local("Centro de Convenções")
                .build();

        Set<ConstraintViolation<EventoDTO>> violations = validator.validate(eventoDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome do evento não pode ser vazio.");
    }

    @Test
    void whenDataIsNull_thenValidationFails() {
        EventoDTO eventoDTO = EventoDTO.builder()
                .nome("Conferência de Tecnologia")
                .data(null)
                .local("Centro de Convenções")
                .build();

        Set<ConstraintViolation<EventoDTO>> violations = validator.validate(eventoDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A data do evento não pode ser nula.");
    }

    @Test
    void whenDataIsInThePast_thenValidationFails() {
        EventoDTO eventoDTO = EventoDTO.builder()
                .nome("Conferência de Tecnologia")
                .data(LocalDate.now().minusDays(1))
                .local("Centro de Convenções")
                .build();

        Set<ConstraintViolation<EventoDTO>> violations = validator.validate(eventoDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("A data do evento deve ser no futuro.");
    }

    @Test
    void whenLocalIsBlank_thenValidationFails() {
        EventoDTO eventoDTO = EventoDTO.builder()
                .nome("Conferência de Tecnologia")
                .data(LocalDate.now().plusDays(1))
                .local("")
                .build();

        Set<ConstraintViolation<EventoDTO>> violations = validator.validate(eventoDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O local do evento não pode ser vazio.");
    }

    @Test
    void whenMultipleFieldsAreInvalid_thenAllViolationsAreFound() {
        EventoDTO eventoDTO = EventoDTO.builder()
                .nome(null)
                .data(LocalDate.now())
                .local(null)
                .build();

        Set<ConstraintViolation<EventoDTO>> violations = validator.validate(eventoDTO);

        assertThat(violations).hasSize(3);
    }
}