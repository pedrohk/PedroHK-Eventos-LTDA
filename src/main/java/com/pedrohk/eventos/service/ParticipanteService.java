package com.pedrohk.eventos.service;

import com.pedrohk.eventos.dto.ParticipanteDTO;
import com.pedrohk.eventos.exception.ResourceNotFoundException;
import com.pedrohk.eventos.model.Participante;
import com.pedrohk.eventos.repository.ParticipanteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final ModelMapper modelMapper;

    public ParticipanteService(ParticipanteRepository participanteRepository, ModelMapper modelMapper) {
        this.participanteRepository = participanteRepository;
        this.modelMapper = modelMapper;
    }

    public List<ParticipanteDTO> findAll() {
        return participanteRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ParticipanteDTO.class))
                .collect(Collectors.toList());
    }

    public ParticipanteDTO findById(Long id) {
        Participante p = participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado com id: " + id));
        return modelMapper.map(p, ParticipanteDTO.class);
    }

    public ParticipanteDTO create(ParticipanteDTO dto) {
        Participante p = modelMapper.map(dto, Participante.class);
        Participante saved = participanteRepository.save(p);
        return modelMapper.map(saved, ParticipanteDTO.class);
    }

    public ParticipanteDTO update(Long id, ParticipanteDTO dto) {
        participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado para atualização com id: " + id));
        Participante p = modelMapper.map(dto, Participante.class);
        p.setId(id);
        Participante updated = participanteRepository.save(p);
        return modelMapper.map(updated, ParticipanteDTO.class);
    }

    public void delete(Long id) {
        Participante p = participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado para exclusão com id: " + id));
        participanteRepository.delete(p);
    }
}