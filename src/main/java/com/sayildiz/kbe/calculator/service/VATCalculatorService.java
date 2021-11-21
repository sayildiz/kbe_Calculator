package com.sayildiz.kbe.calculator.service;

import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import com.sayildiz.kbe.calculator.model.Price;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VATCalculatorService implements CalculatorService{
    public VATCalculatorService(){}

    @Override
    public Price calculateVAT(BigDecimal price) {
        BigDecimal bigVAT = price.multiply(VAT).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        double vat = bigVAT.setScale(2, RoundingMode.HALF_UP).doubleValue() ;
        double gross = bigVAT.add(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double roundedPrice = price.setScale(2, RoundingMode.HALF_UP).doubleValue();
        return new Price(gross, vat, roundedPrice);
    }

    @Override
    public void checkPrecision(BigDecimal price) throws TooManyDecimalsException {
        Pattern pattern = Pattern.compile("^\\d*.\\d\\d{0,1}$");
        Matcher matcher = pattern.matcher(price.toString());
        if(!matcher.find()) throw new TooManyDecimalsException(price);
    }
}
