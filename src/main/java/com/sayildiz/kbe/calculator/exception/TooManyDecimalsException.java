package com.sayildiz.kbe.calculator.exception;

import java.math.BigDecimal;

public class TooManyDecimalsException extends RuntimeException {
    public TooManyDecimalsException(BigDecimal price){
        super("Value should have maximal two decimals " + price);
    }
}
