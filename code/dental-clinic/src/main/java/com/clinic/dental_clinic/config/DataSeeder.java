package com.clinic.dental_clinic.config;

import com.clinic.dental_clinic.domain.entity.Dentist;
import com.clinic.dental_clinic.repository.DentistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDentists(DentistRepository dentistRepository) {
        return args -> {
            if (dentistRepository.count() == 0) {
                dentistRepository.save(new Dentist(null, "ดร.นภัสสรณ์ วัฒนกุล", "ทันตกรรมทั่วไป", "DNT-001", null));
                dentistRepository.save(new Dentist(null, "ดร.กิตติพงษ์ รุ่งเรือง", "ศัลยศาสตร์ช่องปาก", "DNT-002", null));
                dentistRepository.save(new Dentist(null, "ดร.สุชาดา ศรีสุข", "ทันตกรรมจัดฟัน", "DNT-003", null));
            }
        };
    }
}
