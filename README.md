package com.ivrapi.controller;

import com.ivrapi.service.RepeatCallerService;
import com.ivrapi.dto.StatusDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RepeatCallerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RepeatCallerService repeatCallerService;

    @Test
    public void testGetRepeatCaller() {
        String relId = "C01"; // sample relId
        String url = "http://localhost:" + port + "/repeatCaller/getRepeatStatus?relId=" + relId;

        ResponseEntity<StatusDto> responseEntity = restTemplate.postForEntity(url, null, StatusDto.class);
        
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        StatusDto responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("YES", responseBody.getStatus()); // Assuming "YES" is the expected response for relId "C01"
    }
}
