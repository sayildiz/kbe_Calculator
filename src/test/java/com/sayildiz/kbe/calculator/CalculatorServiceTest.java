package com.sayildiz.kbe.calculator;

import com.sayildiz.kbe.calculator.model.Price;
import com.sayildiz.kbe.calculator.service.CalculatorService;
import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import com.sayildiz.kbe.calculator.service.VATCalculatorService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {
    CalculatorService service = new VATCalculatorService();
    String tooManyDecimalMessage = "Value should have maximal two decimals ";

    @Test
    public void testVATCalculation1(){
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

    @Test
    public void testCheckDecimalWith3Decimals(){
        double price = 3.123;
        Exception exception = assertThrows(TooManyDecimalsException.class, () -> service.checkPrecision(price));

        String expectedMessage = tooManyDecimalMessage + price;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCheckDecimalWith5Decimals(){
        double price = 3.12366;
        Exception exception = assertThrows(TooManyDecimalsException.class, () -> service.checkPrecision(price));

        String expectedMessage = tooManyDecimalMessage + price;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCheckDecimalWithNoDecimals(){
        double price = 3.;
        service.checkPrecision(price);
    }

    @Test
    public void testCheckDecimalWithOneDecimals(){
        double price = 325.1;
        service.checkPrecision(price);
    }

    @Test
    public void testCheckDecimalWithTwoDecimals(){
        double price = 334.77;
        service.checkPrecision(price);
    }
}
