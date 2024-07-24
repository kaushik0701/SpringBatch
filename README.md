package com.ivrapi.controller;

import com.ivrapi.dto.FeeWaiverDto;
import com.ivrapi.dto.StatusDto;
import com.ivrapi.service.FeeWaiverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeeWaiverController.class)
public class FeeWaiverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeWaiverService feeWaiverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFeeWaiver_Success() throws Exception {
        // Given
        String cardNum = "1234567890";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);
        feeWaiverDto.setWaiverAmount(100.0);

        when(feeWaiverService.findByCardnum(cardNum)).thenReturn(feeWaiverDto);

        // When & Then
        mockMvc.perform(get("/feeWaiver/getFeeWaiver")
                .param("cardNum", cardNum)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNum").value(cardNum))
                .andExpect(jsonPath("$.waiverAmount").value(100.0));

        verify(feeWaiverService, times(1)).findByCardnum(cardNum);
    }

    @Test
    void testGetFeeWaiver_NotFound() throws Exception {
        // Given
        String cardNum = "1234567890";

        when(feeWaiverService.findByCardnum(cardNum)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/feeWaiver/getFeeWaiver")
                .param("cardNum", cardNum)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(feeWaiverService, times(1)).findByCardnum(cardNum);
    }

    @Test
    void testUpdateFeeWaiver_Success() throws Exception {
        // Given
        String cardNum = "1234567890";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);
        feeWaiverDto.setWaiverAmount(100.0);

        when(feeWaiverService.updateFeeWaiver(feeWaiverDto, cardNum)).thenReturn(true);

        // When & Then
        mockMvc.perform(patch("/feeWaiver/updateFeeWaiver")
                .param("cardNum", cardNum)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cardNum\":\"1234567890\",\"waiverAmount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"));

        verify(feeWaiverService, times(1)).updateFeeWaiver(feeWaiverDto, cardNum);
    }

    @Test
    void testUpdateFeeWaiver_Failure() throws Exception {
        // Given
        String cardNum = "1234567890";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);
        feeWaiverDto.setWaiverAmount(100.0);

        when(feeWaiverService.updateFeeWaiver(feeWaiverDto, cardNum)).thenReturn(false);

        // When & Then
        mockMvc.perform(patch("/feeWaiver/updateFeeWaiver")
                .param("cardNum", cardNum)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cardNum\":\"1234567890\",\"waiverAmount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Failure"));

        verify(feeWaiverService, times(1)).updateFeeWaiver(feeWaiverDto, cardNum);
    }
}
