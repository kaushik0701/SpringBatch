package com.ivrapi.controller;

import com.ivrapi.dto.NtbCallBackDto;
import com.ivrapi.service.NtbCallbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class NtbCallbackControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private NtbCallbackService ntbCallbackService;

    @InjectMocks
    private NtbCallbackController ntbCallbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testInsertNtbCallBack_Success() throws Exception {
        // Given
        NtbCallBackDto ntbCallBackDto = new NtbCallBackDto();
        ntbCallBackDto.setCli("1234567890");
        ntbCallBackDto.setNtbMenu("Menu1");
        ntbCallBackDto.setCallBackNumber("0987654321");
        ntbCallBackDto.setSmsTriggered("Yes");
        ntbCallBackDto.setInteractionId("INT12345");
        ntbCallBackDto.setDnis(123456789L);
        ntbCallBackDto.setCallDateTime(new Date());
        Boolean created = true;

        when(ntbCallbackService.insertNtbCallBack(ntbCallBackDto)).thenReturn(created);

        // When & Then
        mockMvc.perform(post("/insertCallBack")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cli\":\"1234567890\",\"ntbMenu\":\"Menu1\",\"callBackNumber\":\"0987654321\",\"smsTriggered\":\"Yes\",\"interactionId\":\"INT12345\",\"dnis\":123456789,\"callDateTime\":\"2024-07-24T10:15:30.000+00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"));

        verify(ntbCallbackService, times(1)).insertNtbCallBack(ntbCallBackDto);
    }

    @Test
    void testInsertNtbCallBack_Failure() throws Exception {
        // Given
        NtbCallBackDto ntbCallBackDto = new NtbCallBackDto();
        ntbCallBackDto.setCli("1234567890");
        ntbCallBackDto.setNtbMenu("Menu1");
        ntbCallBackDto.setCallBackNumber("0987654321");
        ntbCallBackDto.setSmsTriggered("Yes");
        ntbCallBackDto.setInteractionId("INT12345");
        ntbCallBackDto.setDnis(123456789L);
        ntbCallBackDto.setCallDateTime(new Date());
        Boolean created = false;

        when(ntbCallbackService.insertNtbCallBack(ntbCallBackDto)).thenReturn(created);

        // When & Then
        mockMvc.perform(post("/insertCallBack")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cli\":\"1234567890\",\"ntbMenu\":\"Menu1\",\"callBackNumber\":\"0987654321\",\"smsTriggered\":\"Yes\",\"interactionId\":\"INT12345\",\"dnis\":123456789,\"callDateTime\":\"2024-07-24T10:15:30.000+00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"));

        verify(ntbCallbackService, times(1)).insertNtbCallBack(ntbCallBackDto);
    }
}
