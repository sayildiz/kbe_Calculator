package com.sayildiz.kbe.calculator.controller;

import com.sayildiz.kbe.calculator.model.Price;
import com.sayildiz.kbe.calculator.service.CalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/tax")
    Price calculateTax(@RequestBody double netPrice){
        return calculatorService.calculateVAT(netPrice);
    }

}
