package com.clinic.dental_clinic.pattern.strategy;

import com.clinic.dental_clinic.domain.enums.PatientType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PricingContext {

    private final Map<String, PricingStrategy> strategies;

    public PricingContext(Map<String, PricingStrategy> strategies) {
        this.strategies = strategies;
    }

    public Double calculateFinalPrice(PatientType patientType, Double basePrice) {
        if (patientType == null) {
            return strategies.get("DIRECT_PAY").calculatePrice(basePrice);
        }

        PricingStrategy strategy = strategies.get(patientType.name());
        if (strategy == null) {
            strategy = strategies.get("DIRECT_PAY");
        }

        return strategy.calculatePrice(basePrice);
    }
}