package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.stereotype.Component;

@Component("CIVIL_SERVANT")
public class CivilServantStrategy implements PricingStrategy {
    @Override
    public Double calculatePrice(Double basePrice) {
        return basePrice * 0.80;
    }
}