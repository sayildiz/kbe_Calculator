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

import java.math.BigDecimal;

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
        Price expectedPrice = new Price(BigDecimal.valueOf(41.65), BigDecimal.valueOf(6.65), BigDecimal.valueOf(35.0));
        RequestBuilder request = MockMvcRequestBuilders
                .post("/vat")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedPrice.getNet().toPlainString())
                .contentType(MediaType.APPLICATION_JSON);

        when(mockService.calculateVAT(BigDecimal.valueOf(35.0))).thenReturn(expectedPrice);

        this.mockController.perform(request).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.net", is(35.0)))
                .andExpect(jsonPath("$.gross", is(41.65)))
                .andExpect(jsonPath("$.vat", is(6.65)));
    }

    @Test
    public void VATCalculationTestWithBigNumbers() throws Exception{
        Price expectedPrice = new Price(BigDecimal.valueOf(6054295567.19), BigDecimal.valueOf(966652233.42), BigDecimal.valueOf(5087643333.77));
        RequestBuilder request = MockMvcRequestBuilders
                .post("/vat")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedPrice.getNet().toPlainString())
                .contentType(MediaType.APPLICATION_JSON);

        when(mockService.calculateVAT(BigDecimal.valueOf(5087643333.77))).thenReturn(expectedPrice);

        this.mockController.perform(request).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gross", is(6054295567.19)))
                .andExpect(jsonPath("$.vat", is(966652233.42)))
                .andExpect(jsonPath("$.net", is(5087643333.77)))
                .andExpect(content().string("{\"gross\":6054295567.19,\"vat\":966652233.42,\"net\":5087643333.77}"));

    }
    @Test
    public void checkPrecisionTest() throws Exception{
        BigDecimal price = BigDecimal.valueOf(35.1234);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/vat")
                .accept(MediaType.APPLICATION_JSON)
                .content(price.toString())
                .contentType(MediaType.APPLICATION_JSON);
        doThrow(new TooManyDecimalsException(price)).when(mockService).checkPrecisionHasTwoDecimals(price);
        this.mockController.perform(request).
                andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Value should have maximal two decimals "+ price)));
    }
}
