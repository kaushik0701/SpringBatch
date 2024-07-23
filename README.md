package com.ivrapi.controller;

import com.ivrapi.dto.StatusDto;
import com.ivrapi.service.CustIndicatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustIndicatorController.class)
public class CustIndicatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustIndicatorService custIndicatorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchDataWithTransferExclusion() throws Exception {
        String relId = "123";
        String expectedResponse = "transferExclusionResponse";
        when(custIndicatorService.fetchTransferExclusion(anyString())).thenReturn(expectedResponse);

        mockMvc.perform(post("/custIndicator/transferExclusion")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(expectedResponse));
    }

    @Test
    public void testFetchDataWithSensitiveCustomer() throws Exception {
        String relId = "123";
        String expectedResponse = "sensitiveCustomerResponse";
        when(custIndicatorService.fetchDataWithSensitiveCustomer(anyString())).thenReturn(expectedResponse);

        mockMvc.perform(post("/custIndicator/sensitiveCustomer")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(expectedResponse));
    }

    @Test
    public void testFetchDataWithKycDeficient() throws Exception {
        String relId = "123";
        String expectedResponse = "kycDeficientResponse";
        when(custIndicatorService.fetchDataWithKycDeficient(anyString())).thenReturn(expectedResponse);

        mockMvc.perform(post("/custIndicator/kycDeficient")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(expectedResponse));
    }
}
