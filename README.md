package com.ivrapi.controller;

import com.ivrapi.dto.PreferredLanguageDto;
import com.ivrapi.dto.StatusDto;
import com.ivrapi.service.PreferredLanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PreferredLanguageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PreferredLanguageService preferredLanguageService;

    @InjectMocks
    private PreferredLanguageController preferredLanguageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(preferredLanguageController).build();
    }

    @Test
    void testGetPreferredLanguageByRelId_Success() throws Exception {
        // Given
        String relId = "12345";
        PreferredLanguageDto preferredLang = new PreferredLanguageDto();
        preferredLang.setRelId(relId);
        preferredLang.setLangCd("EN");

        when(preferredLanguageService.getPreferredLanguageByRelId(relId)).thenReturn(preferredLang);

        // When & Then
        mockMvc.perform(post("/preferredLanguage/getLanguage")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.relId").value(relId))
                .andExpect(jsonPath("$.langCd").value("EN"));

        verify(preferredLanguageService, times(1)).getPreferredLanguageByRelId(relId);
    }

    @Test
    void testGetPreferredLanguageByRelId_NotFound() throws Exception {
        // Given
        String relId = "12345";

        when(preferredLanguageService.getPreferredLanguageByRelId(relId)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/preferredLanguage/getLanguage")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(preferredLanguageService, times(1)).getPreferredLanguageByRelId(relId);
    }

    @Test
    void testInsertPreferredLanguage_Success() throws Exception {
        // Given
        PreferredLanguageDto preferredLanguage = new PreferredLanguageDto();
        preferredLanguage.setRelId("12345");
        preferredLanguage.setLangCd("EN");
        String response = "Success";

        when(preferredLanguageService.insertPreferredLanguage(preferredLanguage)).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/preferredLanguage/saveLang")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"relId\":\"12345\",\"langCd\":\"EN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(response));

        verify(preferredLanguageService, times(1)).insertPreferredLanguage(preferredLanguage);
    }

    @Test
    void testDeletePreferredLanguageByRelId_Success() throws Exception {
        // Given
        String relId = "12345";
        String response = "Deleted";

        when(preferredLanguageService.deletePreferredLanguageByRelId(relId)).thenReturn(response);

        // When & Then
        mockMvc.perform(delete("/preferredLanguage/delete")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(response));

        verify(preferredLanguageService, times(1)).deletePreferredLanguageByRelId(relId);
    }
}
