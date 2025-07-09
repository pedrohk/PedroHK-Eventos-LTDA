package com.pedrohk.eventos.controller;

import com.pedrohk.eventos.dto.InscricaoRequestDTO;
import com.pedrohk.eventos.dto.InscricaoResponseDTO;
import com.pedrohk.eventos.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/v1/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> createInscricao(@Valid @RequestBody InscricaoRequestDTO inscricaoRequestDTO) {
        InscricaoResponseDTO createdInscricao = inscricaoService.create(inscricaoRequestDTO);
        return new ResponseEntity<>(createdInscricao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InscricaoResponseDTO>> getAllInscricoes() {
        List<InscricaoResponseDTO> inscricoes = inscricaoService.findAll();
        return ResponseEntity.ok(inscricoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoResponseDTO> getInscricaoById(@PathVariable Long id) {
        InscricaoResponseDTO inscricao = inscricaoService.findById(id);
        return ResponseEntity.ok(inscricao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscricao(@PathVariable Long id) {
        inscricaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}