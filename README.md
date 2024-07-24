package com.ivrapi.controller;

import com.ivrapi.service.RepeatCallerService;
import com.ivrapi.dto.StatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@WebMvcTest(RepeatCallerController.class)
public class RepeatCallerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepeatCallerService repeatCallerService;

    @BeforeEach
    void setUp() {
        // Set up any required data here
        // repeatCallerService.save(new RepeatCaller(...));
    }

    @Test
    void testGetRepeatCaller() throws Exception {
        String relId = "12345";
        String expectedStatus = "someStatus";
        
        // Assuming the findByXRelId method sets up necessary data.
        // repeatCallerService.findByXRelId(relId);

        mockMvc.perform(MockMvcRequestBuilders.post("/repeatCaller/getRepeatStatus")
                .param("relId", relId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(expectedStatus)));
    }
}
