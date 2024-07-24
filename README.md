package com.ivrapi.controller;

import com.ivrapi.service.RepeatCallerService;
import com.ivrapi.dto.StatusDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepeatCallerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void testGetRepeatCaller() {
        String relId = "C01";
        String url = "http://localhost:" + port + "/repeatCaller/getRepeatStatus?relId=" + relId;

        ResponseEntity<StatusDto> response = restTemplate.postForEntity(url, null, StatusDto.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("YES");
    }
}
