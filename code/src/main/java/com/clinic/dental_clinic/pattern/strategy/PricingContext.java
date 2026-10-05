package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PricingContext {

    // Spring จะ Auto-wire คลาสทั้งหมดที่ implements PricingStrategy เข้า Map โดยใช้ Bean Name เป็น Key
    private final Map<String, PricingStrategy> strategies;

    @Autowired
    public PricingContext(Map<String, PricingStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculate(String coverageType, double basePrice) {
        PricingStrategy strategy = strategies.getOrDefault(coverageType, strategies.get("DIRECT_PAY"));
        return strategy.calculateFinalPrice(basePrice);
    }
}