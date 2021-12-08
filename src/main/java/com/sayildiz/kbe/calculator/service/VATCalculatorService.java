package com.sayildiz.kbe.calculator.service;

import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import com.sayildiz.kbe.calculator.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VATCalculatorService implements CalculatorService{
    Logger logger = LoggerFactory.getLogger(VATCalculatorService.class);
    @Override
    public Price calculateVAT(BigDecimal price) {
        BigDecimal bigVAT = price.multiply(VAT).divide(BigDecimal.valueOf(100));
        BigDecimal vat = bigVAT.setScale(2, RoundingMode.HALF_UP) ;
        BigDecimal gross = bigVAT.add(price).setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundedPrice = price.setScale(2, RoundingMode.HALF_UP);
        Price calculatedPrice = new Price(gross, vat, roundedPrice);
        logger.info(calculatedPrice.toString());
        return calculatedPrice;
    }

    @Override
    public void checkPrecisionHasTwoDecimals(BigDecimal price) throws TooManyDecimalsException {
        Pattern pattern = Pattern.compile("^\\d*.\\d\\d{0,1}$|^\\d$");
        Matcher matcher = pattern.matcher(price.toString());
        if(!matcher.find()) {
            logger.warn("value has too many decimals " + price.toPlainString());
            throw new TooManyDecimalsException(price);
        }

    }
}
