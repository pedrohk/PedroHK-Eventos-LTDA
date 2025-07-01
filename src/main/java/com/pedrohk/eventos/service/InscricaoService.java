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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final ModelMapper modelMapper;

    public InscricaoService(InscricaoRepository inscricaoRepository, EventoRepository eventoRepository, ParticipanteRepository participanteRepository, ModelMapper modelMapper) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
        this.modelMapper = modelMapper;
    }

    public InscricaoResponseDTO create(InscricaoRequestDTO requestDTO) {
        Evento evento = eventoRepository.findById(requestDTO.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com id: " + requestDTO.getEventoId()));
        Participante participante = participanteRepository.findById(requestDTO.getParticipanteId())
                .orElseThrow(() -> new ResourceNotFoundException("Participante não encontrado com id: " + requestDTO.getParticipanteId()));

        Inscricao inscricao = Inscricao.builder()
                .evento(evento)
                .participante(participante)
                .build();

        Inscricao savedInscricao = inscricaoRepository.save(inscricao);
        return modelMapper.map(savedInscricao, InscricaoResponseDTO.class);
    }

    public List<InscricaoResponseDTO> findAll() {
        return inscricaoRepository.findAll().stream()
                .map(i -> modelMapper.map(i, InscricaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public InscricaoResponseDTO findById(Long id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada com id: " + id));
        return modelMapper.map(inscricao, InscricaoResponseDTO.class);
    }

    public void delete(Long id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada para exclusão com id: " + id));
        inscricaoRepository.delete(inscricao);
    }
}