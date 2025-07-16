package com.pedrohk.eventos.repository;

import com.pedrohk.eventos.model.CategoriaParticipante;
import com.pedrohk.eventos.model.Evento;
import com.pedrohk.eventos.model.Inscricao;
import com.pedrohk.eventos.model.Participante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class InscricaoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    private Evento evento;
    private Participante participante;

    @BeforeEach
    void setUp() {

        evento = Evento.builder()
                .nome("Hackathon de Java")
                .data(LocalDate.now().plusDays(30))
                .local("Online")
                .build();

        participante = Participante.builder()
                .nome("Maria Clara")
                .email("maria.clara@gmail.org.br")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        testEntityManager.persist(evento);
        testEntityManager.persist(participante);
    }

    @Test
    void whenSaveInscricao_thenSuccess() {

        Inscricao inscricao = Inscricao.builder()
                .evento(evento)
                .participante(participante)
                .build();

        Inscricao savedInscricao = inscricaoRepository.save(inscricao);

        assertThat(savedInscricao).isNotNull();
        assertThat(savedInscricao.getId()).isNotNull();
        assertThat(savedInscricao.getEvento().getNome()).isEqualTo("Hackathon de Java");
    }

    @Test
    void whenSaveDuplicateInscricao_thenThrowException() {
        Inscricao inscricao1 = Inscricao.builder().evento(evento).participante(participante).build();
        testEntityManager.persistAndFlush(inscricao1);

        Inscricao inscricao2 = Inscricao.builder().evento(evento).participante(participante).build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            inscricaoRepository.save(inscricao2);
        });
    }
}