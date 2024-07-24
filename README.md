import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@WebMvcTest(FeeWaiverController.class)
public class FeeWaiverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeWaiverService feeWaiverService;

    @Test
    void testGetFeeWaiver() throws Exception {
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum("num12");
        feeWaiverDto.setLateFeeEligible("y");
        feeWaiverDto.setAnnualFeeRequested("500");
        feeWaiverDto.setAnnualFeeRequestedDate("2024-07-22T00:00:00.000+00:00");
        feeWaiverDto.setLateFeeRequested("Y");
        feeWaiverDto.setLateFeeRequestedDate(null);
        feeWaiverDto.setAnnualFeeEligible("789");

        when(feeWaiverService.findByCardNum("num12")).thenReturn(feeWaiverDto);

        mockMvc.perform(get("/feeWaiver/getFeeWaiver")
                .param("cardNum", "num12")
                .header("Authorization", "Bearer your_token_here")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNum", is("num12")))
                .andExpect(jsonPath("$.lateFeeEligible", is("y")))
                .andExpect(jsonPath("$.annualFeeRequested", is("500")))
                .andExpect(jsonPath("$.annualFeeRequestedDate", is("2024-07-22T00:00:00.000+00:00")))
                .andExpect(jsonPath("$.lateFeeRequested", is("Y")))
                .andExpect(jsonPath("$.lateFeeRequestedDate").doesNotExist())
                .andExpect(jsonPath("$.annualFeeEligible", is("789")));
    }
}
