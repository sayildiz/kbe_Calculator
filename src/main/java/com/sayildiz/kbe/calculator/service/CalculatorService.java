package com.sayildiz.kbe.calculator.service;

import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import com.sayildiz.kbe.calculator.model.Price;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public interface CalculatorService {
    BigDecimal VAT = BigDecimal.valueOf(19.); // germany tax 19%

    Price calculateVAT(BigDecimal price);
    void checkPrecisionHasTwoDecimals(BigDecimal price) throws TooManyDecimalsException;
}
