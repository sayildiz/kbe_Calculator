package com.sayildiz.kbe.calculator.controller;

import com.sayildiz.kbe.calculator.model.Price;
import com.sayildiz.kbe.calculator.service.CalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/vat")
    Price calculateTax(@RequestBody BigDecimal netPrice){
        calculatorService.checkPrecisionHasTwoDecimals(netPrice);
        return calculatorService.calculateVAT(netPrice);
    }

}
