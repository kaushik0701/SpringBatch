package com.ivrapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import com.ivrapi.service.RepeatCallerService;
import com.ivrapi.dto.StatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RepeatCallerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RepeatCallerService repeatCallerService;

    @InjectMocks
    private RepeatCallerController repeatCallerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repeatCallerController).build();
    }

    @Test
    void testGetRepeatCaller_Success() throws Exception {
        // Given
        String relId = "12345";
        String repeatStatus = "repeat";

        when(repeatCallerService.findByXRelId(relId)).thenReturn(repeatStatus);

        // When & Then
        mockMvc.perform(post("/repeatCaller/getRepeatStatus")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(repeatStatus));

        verify(repeatCallerService, times(1)).findByXRelId(relId);
    }

    @Test
    void testGetRepeatCaller_NotFound() throws Exception {
        // Given
        String relId = "12345";
        String repeatStatus = null;

        when(repeatCallerService.findByXRelId(relId)).thenReturn(repeatStatus);

        // When & Then
        mockMvc.perform(post("/repeatCaller/getRepeatStatus")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").isEmpty());

        verify(repeatCallerService, times(1)).findByXRelId(relId);
    }
}
