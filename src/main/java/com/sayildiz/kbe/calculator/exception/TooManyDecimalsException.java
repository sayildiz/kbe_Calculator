package com.sayildiz.kbe.calculator.exception;

public class TooManyDecimalsException extends RuntimeException {
    public TooManyDecimalsException(double price){
        super("Value should have maximal two decimals " + price);
    }
}
