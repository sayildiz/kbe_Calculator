package com.sayildiz.kbe.calculator;

import com.sayildiz.kbe.calculator.model.Price;
import com.sayildiz.kbe.calculator.service.CalculatorService;
import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import com.sayildiz.kbe.calculator.service.VATCalculatorService;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {
    CalculatorService service = new VATCalculatorService();
    String tooManyDecimalMessage = "Value should have maximal two decimals ";

    @Test
    public void testVATCalculation1(){
        BigDecimal price = BigDecimal.valueOf(15.49);
        BigDecimal expectedVAT = BigDecimal.valueOf(2.94);
        BigDecimal gross = price.add(expectedVAT);
        Price expectedPrice = new Price(gross, expectedVAT, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }

    @Test
    public void testVat2(){
        BigDecimal price = BigDecimal.valueOf(13.33);
        BigDecimal expectedVAT = BigDecimal.valueOf(2.53);
        BigDecimal gross = price.add(expectedVAT);
        Price expectedPrice = new Price(gross, expectedVAT, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }

    @Test
    public void testVatSingleValue(){
        BigDecimal price = BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedVAT = BigDecimal.valueOf(0.95).setScale(2, RoundingMode.HALF_UP);
        BigDecimal gross = price.add(expectedVAT).setScale(2, RoundingMode.HALF_UP);
        Price expectedPrice = new Price(gross, expectedVAT, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }

    @Test
    public void testVat3(){
        BigDecimal price = BigDecimal.valueOf(1767).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedVAT = BigDecimal.valueOf(335.73).setScale(2, RoundingMode.HALF_UP);
        BigDecimal gross = price.add(expectedVAT).setScale(2, RoundingMode.HALF_UP);
        Price expectedPrice = new Price(gross, expectedVAT, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
    }

    @Test
    public void testVat4(){
        BigDecimal price = BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP);
        BigDecimal priceDecimal = BigDecimal.valueOf(5.0).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedVAT = BigDecimal.valueOf(0.95).setScale(2, RoundingMode.HALF_UP);
        BigDecimal gross = price.add(expectedVAT).setScale(2, RoundingMode.HALF_UP);
        Price expectedPrice = new Price(gross, expectedVAT, price);
        Price expectedDecimalPrice = new Price(gross ,expectedVAT, price);
        assertEquals(expectedPrice, service.calculateVAT(price));
        assertEquals(expectedDecimalPrice, service.calculateVAT(priceDecimal));

    }

    @Test
    public void testCheckDecimalWith3Decimals(){
        BigDecimal price = BigDecimal.valueOf(3.123);
        Exception exception = assertThrows(TooManyDecimalsException.class, () -> service.checkPrecisionHasTwoDecimals(price));

        String expectedMessage = tooManyDecimalMessage + price;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCheckDecimalWith5Decimals(){
        BigDecimal price = BigDecimal.valueOf(3.12366);
        Exception exception = assertThrows(TooManyDecimalsException.class, () -> service.checkPrecisionHasTwoDecimals(price));

        String expectedMessage = tooManyDecimalMessage + price;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCheckDecimalWithNoDecimals(){
        BigDecimal price = BigDecimal.valueOf(3.);
        service.checkPrecisionHasTwoDecimals(price);
    }

    @Test
    public void testCheckDecimalWithOneDecimals(){
        BigDecimal price = BigDecimal.valueOf(325.1);
        service.checkPrecisionHasTwoDecimals(price);
    }

    @Test
    public void testCheckDecimalWithTwoDecimals(){
        BigDecimal price = BigDecimal.valueOf(334.77);
        service.checkPrecisionHasTwoDecimals(price);
    }
}
