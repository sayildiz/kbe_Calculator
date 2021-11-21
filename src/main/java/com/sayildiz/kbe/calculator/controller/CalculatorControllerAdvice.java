package com.sayildiz.kbe.calculator.controller;

import com.sayildiz.kbe.calculator.exception.TooManyDecimalsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CalculatorControllerAdvice {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String tooManyDecimalsHandler(TooManyDecimalsException exception){
        return exception.getMessage();
    }
}
