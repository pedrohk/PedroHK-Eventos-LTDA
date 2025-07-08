package com.pedrohk.eventos.controller;

import com.pedrohk.eventos.dto.ParticipanteDTO;
import com.pedrohk.eventos.service.ParticipanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participantes")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    public ParticipanteController(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> getAllParticipantes() {
        List<ParticipanteDTO> participantes = participanteService.findAll();
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> getParticipanteById(@PathVariable Long id) {
        ParticipanteDTO participante = participanteService.findById(id);
        return ResponseEntity.ok(participante);
    }

    @PostMapping
    public ResponseEntity<ParticipanteDTO> createParticipante(@Valid @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO createdParticipante = participanteService.create(participanteDTO);
        return new ResponseEntity<>(createdParticipante, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> updateParticipante(@PathVariable Long id, @Valid @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO updatedParticipante = participanteService.update(id, participanteDTO);
        return ResponseEntity.ok(updatedParticipante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable Long id) {
        participanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}