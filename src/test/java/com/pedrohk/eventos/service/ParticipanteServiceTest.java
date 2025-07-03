package com.pedrohk.eventos.service;

import com.pedrohk.eventos.dto.ParticipanteDTO;
import com.pedrohk.eventos.exception.ResourceNotFoundException;
import com.pedrohk.eventos.model.CategoriaParticipante;
import com.pedrohk.eventos.model.Participante;
import com.pedrohk.eventos.repository.ParticipanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ParticipanteServiceTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ParticipanteService participanteService;

    private Participante participante;
    private ParticipanteDTO participanteDTO;

    @BeforeEach
    void setUp() {
        participante = Participante.builder()
                .id(1L)
                .nome("Pedro Kuhn")
                .email("pedro.kuhn@yahoo.com")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();

        participanteDTO = ParticipanteDTO.builder()
                .id(1L)
                .nome("Pedro Kuhn")
                .email("pedro.kuhn@yahoo.com")
                .categoria(CategoriaParticipante.CLIENTE)
                .build();
    }

    @Test
    void whenFindAll_thenReturnParticipanteDTOList() {
        when(participanteRepository.findAll()).thenReturn(List.of(participante));
        when(modelMapper.map(any(Participante.class), eq(ParticipanteDTO.class))).thenReturn(participanteDTO);

        List<ParticipanteDTO> result = participanteService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(participanteRepository).findAll();
    }

    @Test
    void whenFindById_withValidId_thenReturnParticipanteDTO() {
        when(participanteRepository.findById(1L)).thenReturn(Optional.of(participante));
        when(modelMapper.map(participante, ParticipanteDTO.class)).thenReturn(participanteDTO);

        ParticipanteDTO result = participanteService.findById(1L);

        assertNotNull(result);
        assertEquals(participanteDTO.getId(), result.getId());
        verify(participanteRepository).findById(1L);
    }

    @Test
    void whenFindById_withInvalidId_thenThrowResourceNotFoundException() {
        when(participanteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> participanteService.findById(99L));
        verify(participanteRepository).findById(99L);
    }

    @Test
    void whenCreate_thenReturnSavedParticipanteDTO() {
        when(modelMapper.map(any(ParticipanteDTO.class), eq(Participante.class))).thenReturn(participante);
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);
        when(modelMapper.map(any(Participante.class), eq(ParticipanteDTO.class))).thenReturn(participanteDTO);

        ParticipanteDTO result = participanteService.create(new ParticipanteDTO());

        assertNotNull(result);
        assertEquals(participanteDTO.getNome(), result.getNome());
        verify(participanteRepository).save(any(Participante.class));
    }

    @Test
    void whenUpdate_withValidId_thenReturnUpdatedParticipanteDTO() {
        when(participanteRepository.findById(1L)).thenReturn(Optional.of(participante));
        when(modelMapper.map(any(ParticipanteDTO.class), eq(Participante.class))).thenReturn(participante);
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);
        when(modelMapper.map(any(Participante.class), eq(ParticipanteDTO.class))).thenReturn(participanteDTO);

        ParticipanteDTO result = participanteService.update(1L, participanteDTO);

        assertNotNull(result);
        assertEquals(participanteDTO.getEmail(), result.getEmail());
        verify(participanteRepository).findById(1L);
        verify(participanteRepository).save(any(Participante.class));
    }

    @Test
    void whenDelete_withValidId_thenSucceed() {
        when(participanteRepository.findById(1L)).thenReturn(Optional.of(participante));
        doNothing().when(participanteRepository).delete(participante);

        participanteService.delete(1L);

        verify(participanteRepository).findById(1L);
        verify(participanteRepository).delete(participante);
    }
}