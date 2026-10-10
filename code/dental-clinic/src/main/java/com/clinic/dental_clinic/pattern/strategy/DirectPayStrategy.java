package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.stereotype.Component;

@Component("DIRECT_PAY")
public class DirectPayStrategy implements PricingStrategy {
    @Override
    public Double calculatePrice(Double basePrice) {
        return basePrice;
    }
}