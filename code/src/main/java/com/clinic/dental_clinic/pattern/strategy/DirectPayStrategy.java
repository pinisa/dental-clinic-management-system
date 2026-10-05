package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.stereotype.Component;

@Component("DIRECT_PAY")
public class DirectPayStrategy implements PricingStrategy {

    @Override
    public double calculateFinalPrice(double basePrice) {
        // ไม่มีส่วนลด
        return basePrice;
    }
}