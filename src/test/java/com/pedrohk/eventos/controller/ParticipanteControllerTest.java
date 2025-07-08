package com.pedrohk.eventos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrohk.eventos.dto.ParticipanteDTO;
import com.pedrohk.eventos.model.CategoriaParticipante;
import com.pedrohk.eventos.service.ParticipanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ParticipanteController.class)
class ParticipanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParticipanteService participanteService;

    private ParticipanteDTO participanteDTO;

    @BeforeEach
    void setUp() {
        participanteDTO = ParticipanteDTO.builder()
                .id(1L)
                .nome("Carlos Pereira")
                .email("carlos.p@example.com")
                .categoria(CategoriaParticipante.FUNCIONARIO_EMPRESA)
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenCreateParticipante_thenReturnCreated() throws Exception {
        given(participanteService.create(any(ParticipanteDTO.class))).willReturn(participanteDTO);

        mockMvc.perform(post("/api/v1/participantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(participanteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(participanteDTO.getEmail()));
    }

    @Test
    void whenGetParticipanteById_thenReturnOk() throws Exception {
        given(participanteService.findById(1L)).willReturn(participanteDTO);

        mockMvc.perform(get("/api/v1/participantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(participanteDTO.getNome()));
    }
}