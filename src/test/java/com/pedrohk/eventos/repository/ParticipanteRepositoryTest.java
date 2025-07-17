package com.pedrohk.eventos.repository;

import com.pedrohk.eventos.model.CategoriaParticipante;
import com.pedrohk.eventos.model.Participante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class ParticipanteRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Test
    void whenSaveAndFindById_thenReturnsCorrectParticipante() {
        Participante participante = Participante.builder()
                .nome("João da Silva")
                .email("joao.silva@gmail.org")
                .categoria(CategoriaParticipante.FUNCIONARIO_EMPRESA)
                .build();
        testEntityManager.persistAndFlush(participante);

        Optional<Participante> found = participanteRepository.findById(participante.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(participante.getEmail());
    }

    @Test
    void whenSaveWithDuplicateEmail_thenThrowsException() {
        Participante participante1 = Participante.builder()
                .nome("Ana Souza")
                .email("ana.souza@gmail.org")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();
        testEntityManager.persistAndFlush(participante1);

        Participante participante2 = Participante.builder()
                .nome("Ana Maria")
                .email("ana.souza@gmail.org")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            participanteRepository.saveAndFlush(participante2);
        });
    }
}