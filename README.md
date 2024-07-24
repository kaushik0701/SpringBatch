package com.ivrapi.controller;

import com.ivrapi.dto.PreferredLanguageDto;
import com.ivrapi.dto.StatusDto;
import com.ivrapi.service.PreferredLanguageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PreferredLanguageControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetPreferredLanguageByRelId() {
        String relId = "C01";
        String url = "http://localhost:" + port + "/preferredLanguage/getLanguage?relId=" + relId;

        ResponseEntity<PreferredLanguageDto> response = restTemplate.postForEntity(url, null, PreferredLanguageDto.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        // Add additional assertions based on your expected response
    }

    @Test
    public void testInsertPreferredLanguage() {
        String url = "http://localhost:" + port + "/preferredLanguage/saveLang";
        PreferredLanguageDto preferredLanguage = new PreferredLanguageDto();
        // Set fields for preferredLanguage based on your DTO definition
        HttpEntity<PreferredLanguageDto> request = new HttpEntity<>(preferredLanguage);

        ResponseEntity<StatusDto> response = restTemplate.postForEntity(url, request, StatusDto.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("ExpectedStatus"); // Adjust based on your service logic
    }

    @Test
    public void testDeletePreferredLanguageByRelId() {
        String relId = "C01";
        String url = "http://localhost:" + port + "/preferredLanguage/delete?relId=" + relId;

        ResponseEntity<StatusDto> response = restTemplate.exchange(url, HttpMethod.DELETE, null, StatusDto.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("ExpectedStatus"); // Adjust based on your service logic
    }
}
