package com.pedrohk.eventos.dto;

import com.pedrohk.eventos.model.CategoriaParticipante;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InscricaoResponseDTOTest {

    @Test
    void whenDtoIsBuilt_thenGettersReturnCorrectValues() {
        EventoDTO evento = EventoDTO.builder()
                .id(1L)
                .nome("Evento Teste")
                .data(LocalDate.now().plusDays(5))
                .local("Local Teste")
                .build();

        ParticipanteDTO participante = ParticipanteDTO.builder()
                .id(2L)
                .nome("Participante Teste")
                .email("participante@teste.com")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        LocalDateTime dataInscricao = LocalDateTime.now();

        InscricaoResponseDTO inscricaoResponseDTO = InscricaoResponseDTO.builder()
                .id(100L)
                .evento(evento)
                .participante(participante)
                .dataInscricao(dataInscricao)
                .build();

        assertThat(inscricaoResponseDTO.getId()).isEqualTo(100L);
        assertThat(inscricaoResponseDTO.getDataInscricao()).isEqualTo(dataInscricao);
        assertThat(inscricaoResponseDTO.getEvento()).isEqualTo(evento);
        assertThat(inscricaoResponseDTO.getParticipante()).isEqualTo(participante);
        assertThat(inscricaoResponseDTO.getEvento().getNome()).isEqualTo("Evento Teste");
        assertThat(inscricaoResponseDTO.getParticipante().getEmail()).isEqualTo("participante@teste.com");
    }
}