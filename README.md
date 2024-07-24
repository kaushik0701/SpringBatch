package com.ivrapi.controller;

import com.ivrapi.service.RepeatCallerService;
import com.ivrapi.dto.StatusDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RepeatCallerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepeatCallerService repeatCallerService;

    @Test
    public void testGetRepeatCaller() throws Exception {
        // Assuming findByXRelId method returns "active" for the given relId "12345"
        String relId = "12345";
        String expectedResponse = "{\"status\":\"active\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/repeatCaller/getRepeatStatus")
                        .param("relId", relId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }
}
