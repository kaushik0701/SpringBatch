package com.ivrapi.controller;

import com.ivrapi.dto.NtbCallBackDto;
import com.ivrapi.dto.StatusDto;
import com.ivrapi.service.NtbCallbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NtbCallbackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NtbCallbackService ntbCallbackService;

    @InjectMocks
    private NtbCallbackController ntbCallbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ntbCallbackController).build();
    }

    @Test
    void testInsertNtbCallBack_Success() throws Exception {
        // Given
        NtbCallBackDto ntbCallBackDto = new NtbCallBackDto();
        ntbCallBackDto.setSomeField("value"); // Set the necessary fields in NtbCallBackDto
        Boolean created = true;

        when(ntbCallbackService.insertNtbCallBack(ntbCallBackDto)).thenReturn(created);

        // When & Then
        mockMvc.perform(post("/insertCallBack")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"someField\":\"value\"}")) // Adjust the JSON content to match the NtbCallBackDto structure
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"));

        verify(ntbCallbackService, times(1)).insertNtbCallBack(ntbCallBackDto);
    }

    @Test
    void testInsertNtbCallBack_Failure() throws Exception {
        // Given
        NtbCallBackDto ntbCallBackDto = new NtbCallBackDto();
        ntbCallBackDto.setSomeField("value"); // Set the necessary fields in NtbCallBackDto
        Boolean created = false;

        when(ntbCallbackService.insertNtbCallBack(ntbCallBackDto)).thenReturn(created);

        // When & Then
        mockMvc.perform(post("/insertCallBack")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"someField\":\"value\"}")) // Adjust the JSON content to match the NtbCallBackDto structure
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"));

        verify(ntbCallbackService, times(1)).insertNtbCallBack(ntbCallBackDto);
    }
}
