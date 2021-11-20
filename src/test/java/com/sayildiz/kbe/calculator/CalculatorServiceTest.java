package com.sayildiz.kbe.calculator;

import com.sayildiz.kbe.calculator.model.Price;
import com.sayildiz.kbe.calculator.service.CalculatorService;
import com.sayildiz.kbe.calculator.service.VATCalculatorService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {
    CalculatorService service = new VATCalculatorService();

    @Test
    public void testTax1(){
        double price = 15.49;
        double expectedVAT = 2.94;
        double gross = price + expectedVAT;
        Price expectedPrice = new Price(gross, expectedVAT, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }

    @Test
    public void testTax2(){
        double price = 13.33;
        double expectedTax = 2.53;
        double gross = price + expectedTax;
        Price expectedPrice = new Price(gross, expectedTax, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }

    @Test
    public void testTax3(){
        double price = 1767;
        double expectedTax = 335.73;
        double gross = price + expectedTax;
        Price expectedPrice = new Price(gross, expectedTax, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }
}
