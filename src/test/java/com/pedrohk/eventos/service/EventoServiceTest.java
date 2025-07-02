package com.pedrohk.eventos.service;

import com.pedrohk.eventos.dto.EventoDTO;
import com.pedrohk.eventos.exception.ResourceNotFoundException;
import com.pedrohk.eventos.model.Evento;
import com.pedrohk.eventos.repository.EventoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EventoService eventoService;

    private Evento evento;
    private EventoDTO eventoDTO;

    @BeforeEach
    void setUp() {
        evento = Evento.builder()
                .id(1L)
                .nome("Conferência de Tecnologia")
                .data(LocalDate.now().plusDays(10))
                .local("Centro de Convenções")
                .build();

        eventoDTO = EventoDTO.builder()
                .id(1L)
                .nome("Conferência de Tecnologia")
                .data(LocalDate.now().plusDays(10))
                .local("Centro de Convenções")
                .build();
    }

    @Test
    void whenFindAll_thenReturnEventoDTOList() {
        when(eventoRepository.findAll()).thenReturn(List.of(evento));
        when(modelMapper.map(any(Evento.class), eq(EventoDTO.class))).thenReturn(eventoDTO);

        List<EventoDTO> result = eventoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Conferência de Tecnologia", result.get(0).getNome());
        verify(eventoRepository, times(1)).findAll();
    }

    @Test
    void whenFindById_withValidId_thenReturnEventoDTO() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(modelMapper.map(evento, EventoDTO.class)).thenReturn(eventoDTO);

        EventoDTO result = eventoService.findById(1L);

        assertNotNull(result);
        assertEquals(evento.getId(), result.getId());
        verify(eventoRepository, times(1)).findById(1L);
    }

    @Test
    void whenFindById_withInvalidId_thenThrowResourceNotFoundException() {
        when(eventoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventoService.findById(99L));
        verify(eventoRepository, times(1)).findById(99L);
    }

    @Test
    void whenCreate_thenReturnSavedEventoDTO() {
        when(modelMapper.map(any(EventoDTO.class), eq(Evento.class))).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(modelMapper.map(any(Evento.class), eq(EventoDTO.class))).thenReturn(eventoDTO);

        EventoDTO result = eventoService.create(new EventoDTO());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void whenUpdate_withValidId_thenReturnUpdatedEventoDTO() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(modelMapper.map(any(EventoDTO.class), eq(Evento.class))).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(modelMapper.map(any(Evento.class), eq(EventoDTO.class))).thenReturn(eventoDTO);

        EventoDTO result = eventoService.update(1L, eventoDTO);

        assertNotNull(result);
        assertEquals("Conferência de Tecnologia", result.getNome());
        verify(eventoRepository, times(1)).findById(1L);
        verify(eventoRepository, times(1)).save(any(Evento.class));
    }

    @Test
    void whenDelete_withValidId_thenSucceed() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        doNothing().when(eventoRepository).delete(evento);

        eventoService.delete(1L);

        verify(eventoRepository, times(1)).findById(1L);
        verify(eventoRepository, times(1)).delete(evento);
    }
}