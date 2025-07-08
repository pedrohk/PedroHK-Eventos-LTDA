package com.pedrohk.eventos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pedrohk.eventos.dto.EventoDTO;
import com.pedrohk.eventos.exception.ResourceNotFoundException;
import com.pedrohk.eventos.service.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EventoController.class)
class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventoService eventoService;

    private EventoDTO eventoDTO;

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        eventoDTO = EventoDTO.builder()
                .id(1L)
                .nome("Conferência de Java")
                .data(LocalDate.now().plusMonths(1))
                .local("Online")
                .build();
    }

    @Test
    void whenGetAllEventos_thenReturnJsonArray() throws Exception {
        given(eventoService.findAll()).willReturn(List.of(eventoDTO));

        mockMvc.perform(get("/api/v1/eventos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(eventoDTO.getNome()));
    }

    @Test
    void whenGetEventoById_withValidId_thenReturnJsonObject() throws Exception {
        given(eventoService.findById(1L)).willReturn(eventoDTO);

        mockMvc.perform(get("/api/v1/eventos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void whenGetEventoById_withInvalidId_thenReturnNotFound() throws Exception {
        given(eventoService.findById(99L)).willThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/v1/eventos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateEvento_withValidData_thenReturnCreated() throws Exception {
        given(eventoService.create(any(EventoDTO.class))).willReturn(eventoDTO);

        mockMvc.perform(post("/api/v1/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(eventoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(eventoDTO.getNome()));
    }

    @Test
    void whenUpdateEvento_withValidData_thenReturnOk() throws Exception {
        given(eventoService.update(eq(1L), any(EventoDTO.class))).willReturn(eventoDTO);

        mockMvc.perform(put("/api/v1/eventos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(eventoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.local").value("Online"));
    }

    @Test
    void whenDeleteEvento_withValidId_thenReturnNoContent() throws Exception {
        doNothing().when(eventoService).delete(1L);

        mockMvc.perform(delete("/api/v1/eventos/1"))
                .andExpect(status().isNoContent());
    }
}