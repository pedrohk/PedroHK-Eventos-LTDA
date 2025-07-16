package com.pedrohk.eventos.repository;

import com.pedrohk.eventos.model.Evento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EventoRepository eventoRepository;

    @Test
    void whenFindById_thenReturnEvento() {
        Evento evento = Evento.builder()
                .nome("Convenção de Programadores Etílicos")
                .local("Centro de Convenções")
                .data(LocalDate.now().plusMonths(2))
                .build();
        testEntityManager.persistAndFlush(evento);

        Optional<Evento> found = eventoRepository.findById(evento.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getNome()).isEqualTo(evento.getNome());
    }
}