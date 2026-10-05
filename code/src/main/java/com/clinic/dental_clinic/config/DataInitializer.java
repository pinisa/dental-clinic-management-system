package com.clinic.dental_clinic.config;

import com.clinic.dental_clinic.model.Dentist;
import com.clinic.dental_clinic.repository.DentistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(DentistRepository dentistRepository) {
        return args -> {
            if (dentistRepository.count() == 0) {
                Dentist d1 = new Dentist();
                d1.setName("ทพ. สมชาย ใจดี");
                d1.setSpecialization("ทันตกรรมทั่วไป");
                dentistRepository.save(d1);

                Dentist d2 = new Dentist();
                d2.setName("ทพญ. สมหญิง รักยิ้ม");
                d2.setSpecialization("ศัลยศาสตร์ช่องปาก (ผ่าฟันครุฑ)");
                dentistRepository.save(d2);
            }
        };
    }
}