package com.sayildiz.kbe.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayildiz.kbe.calculator.controller.CalculatorController;
import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import com.sayildiz.kbe.calculator.model.Price;
import com.sayildiz.kbe.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

    @Autowired
    private MockMvc mockController;

    @MockBean
    private CalculatorService mockService;


    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void VATCalculationTest() throws Exception{
        Price expectedPrice = new Price(41.65, 6.65, 35.0);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/tax")
                .accept(MediaType.APPLICATION_JSON)
                .content(Double.toString(expectedPrice.getNet()))
                .contentType(MediaType.APPLICATION_JSON);

        when(mockService.calculateVAT(35)).thenReturn(expectedPrice);

        this.mockController.perform(request).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.net", is(35.0)))
                .andExpect(jsonPath("$.gross", is(41.65)))
                .andExpect(jsonPath("$.vat", is(6.65)));
    }

    @Test
    public void checkPrecisionTest() throws Exception{
        double price = 35.1234;
        RequestBuilder request = MockMvcRequestBuilders
                .post("/tax")
                .accept(MediaType.APPLICATION_JSON)
                .content(Double.toString(price))
                .contentType(MediaType.APPLICATION_JSON);
        doThrow(new TooManyDecimalsException(price)).when(mockService).checkPrecision(price);
        this.mockController.perform(request).
                andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Value should have maximal two decimals "+ price)));
    }
}
