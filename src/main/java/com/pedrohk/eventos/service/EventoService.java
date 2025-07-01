package com.pedrohk.eventos.service;

import com.pedrohk.eventos.dto.EventoDTO;
import com.pedrohk.eventos.exception.ResourceNotFoundException;
import com.pedrohk.eventos.model.Evento;
import com.pedrohk.eventos.repository.EventoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ModelMapper modelMapper;

    public EventoService(EventoRepository eventoRepository, ModelMapper modelMapper) {
        this.eventoRepository = eventoRepository;
        this.modelMapper = modelMapper;
    }

    public List<EventoDTO> findAll() {
        return eventoRepository.findAll().stream()
                .map(evento -> modelMapper.map(evento, EventoDTO.class))
                .collect(Collectors.toList());
    }

    public EventoDTO findById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com id: " + id));
        return modelMapper.map(evento, EventoDTO.class);
    }

    public EventoDTO create(EventoDTO eventoDTO) {
        Evento evento = modelMapper.map(eventoDTO, Evento.class);
        Evento savedEvento = eventoRepository.save(evento);
        return modelMapper.map(savedEvento, EventoDTO.class);
    }

    public EventoDTO update(Long id, EventoDTO eventoDTO) {
        eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado para atualização com id: " + id));
        Evento evento = modelMapper.map(eventoDTO, Evento.class);
        evento.setId(id);
        Evento updatedEvento = eventoRepository.save(evento);
        return modelMapper.map(updatedEvento, EventoDTO.class);
    }

    public void delete(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado para exclusão com id: " + id));
        eventoRepository.delete(evento);
    }
}