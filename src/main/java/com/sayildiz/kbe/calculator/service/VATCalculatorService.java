package com.sayildiz.kbe.calculator.service;

import com.sayildiz.kbe.calculator.model.Price;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VATCalculatorService implements CalculatorService{
    public VATCalculatorService(){};
    @Override
    public Price calculateVAT(double price) {
        BigDecimal bigPrice = BigDecimal.valueOf(price);
        BigDecimal bigVAT = bigPrice.multiply(VAT).divide(BigDecimal.valueOf(100));
        double vat = bigVAT.setScale(2, RoundingMode.HALF_UP).doubleValue() ;
        double gross = bigVAT.add(bigPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double roundedPrice = bigPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
        return new Price(gross, vat, roundedPrice);
    }
}
