package com.pedrohk.eventos.service;

import com.pedrohk.eventos.dto.InscricaoRequestDTO;
import com.pedrohk.eventos.dto.InscricaoResponseDTO;
import com.pedrohk.eventos.exception.ResourceNotFoundException;
import com.pedrohk.eventos.model.Evento;
import com.pedrohk.eventos.model.Inscricao;
import com.pedrohk.eventos.model.Participante;
import com.pedrohk.eventos.repository.EventoRepository;
import com.pedrohk.eventos.repository.InscricaoRepository;
import com.pedrohk.eventos.repository.ParticipanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @Mock
    private InscricaoRepository inscricaoRepository;
    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private ParticipanteRepository participanteRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InscricaoService inscricaoService;

    private Evento evento;
    private Participante participante;
    private Inscricao inscricao;
    private InscricaoRequestDTO requestDTO;
    private InscricaoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        evento = Evento.builder().id(1L).nome("Evento Teste").build();
        participante = Participante.builder().id(2L).nome("Participante Teste").build();

        inscricao = Inscricao.builder()
                .id(10L)
                .evento(evento)
                .participante(participante)
                .dataInscricao(LocalDateTime.now())
                .build();

        requestDTO = new InscricaoRequestDTO(1L, 2L);

        responseDTO = InscricaoResponseDTO.builder()
                .id(10L)
                .dataInscricao(inscricao.getDataInscricao())
                .build();
    }

    @Test
    void whenCreate_withValidIds_thenReturnInscricaoResponseDTO() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(participanteRepository.findById(2L)).thenReturn(Optional.of(participante));
        when(inscricaoRepository.save(any(Inscricao.class))).thenReturn(inscricao);
        when(modelMapper.map(any(Inscricao.class), eq(InscricaoResponseDTO.class))).thenReturn(responseDTO);

        InscricaoResponseDTO result = inscricaoService.create(requestDTO);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(eventoRepository).findById(1L);
        verify(participanteRepository).findById(2L);
        verify(inscricaoRepository).save(any(Inscricao.class));
    }


    @Test
    void whenCreate_withInvalidEventoId_thenThrowResourceNotFoundException() {
        when(eventoRepository.findById(99L)).thenReturn(Optional.empty());

        InscricaoRequestDTO invalidRequest = new InscricaoRequestDTO(99L, 2L);
        assertThrows(ResourceNotFoundException.class, () -> inscricaoService.create(invalidRequest));
        verify(participanteRepository, never()).findById(anyLong());
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void whenCreate_withInvalidParticipanteId_thenThrowResourceNotFoundException() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(participanteRepository.findById(99L)).thenReturn(Optional.empty());

        InscricaoRequestDTO invalidRequest = new InscricaoRequestDTO(1L, 99L);
        assertThrows(ResourceNotFoundException.class, () -> inscricaoService.create(invalidRequest));
        verify(inscricaoRepository, never()).save(any(Inscricao.class));
    }

    @Test
    void whenFindById_withValidId_thenReturnInscricaoResponseDTO() {
        when(inscricaoRepository.findById(10L)).thenReturn(Optional.of(inscricao));
        when(modelMapper.map(inscricao, InscricaoResponseDTO.class)).thenReturn(responseDTO);

        InscricaoResponseDTO result = inscricaoService.findById(10L);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        verify(inscricaoRepository).findById(10L);
    }

    @Test
    void whenDelete_withValidId_thenSucceed() {
        when(inscricaoRepository.findById(10L)).thenReturn(Optional.of(inscricao));
        doNothing().when(inscricaoRepository).delete(inscricao);

        inscricaoService.delete(10L);

        verify(inscricaoRepository).findById(10L);
        verify(inscricaoRepository).delete(inscricao);
    }
}