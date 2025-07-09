package com.pedrohk.eventos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pedrohk.eventos.dto.InscricaoRequestDTO;
import com.pedrohk.eventos.dto.InscricaoResponseDTO;
import com.pedrohk.eventos.service.InscricaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InscricaoController.class)
class InscricaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InscricaoService inscricaoService;

    private InscricaoRequestDTO requestDTO;
    private InscricaoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new InscricaoRequestDTO(1L, 2L);
        responseDTO = InscricaoResponseDTO.builder()
                .id(10L)
                .dataInscricao(LocalDateTime.now())
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenCreateInscricao_withValidData_thenReturnCreated() throws Exception {
        given(inscricaoService.create(any(InscricaoRequestDTO.class))).willReturn(responseDTO);

        mockMvc.perform(post("/api/v1/inscricoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    void whenGetInscricaoById_thenReturnOk() throws Exception {
        given(inscricaoService.findById(10L)).willReturn(responseDTO);

        mockMvc.perform(get("/api/v1/inscricoes/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    void whenDeleteInscricao_withValidId_thenReturnNoContent() throws Exception {
        doNothing().when(inscricaoService).delete(10L);

        mockMvc.perform(delete("/api/v1/inscricoes/10"))
                .andExpect(status().isNoContent());
    }
}