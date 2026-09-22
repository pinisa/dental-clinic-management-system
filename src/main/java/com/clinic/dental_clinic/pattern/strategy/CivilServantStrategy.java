package com.clinic.dental_clinic.pattern.strategy;

import org.springframework.stereotype.Component;

@Component("CIVIL_SERVANT")
public class CivilServantStrategy implements PricingStrategy {

    @Override
    public double calculateFinalPrice(double basePrice) {
        // สิทธิข้าราชการ: จ่ายเพียง 70% (รับส่วนลด 30%)
        return basePrice * 0.70;
    }
}