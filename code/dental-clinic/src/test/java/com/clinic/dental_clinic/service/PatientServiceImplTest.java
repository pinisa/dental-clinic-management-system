package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.dto.request.PatientRequest;
import com.clinic.dental_clinic.domain.enums.PatientType;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {
    @Mock PatientRepository patientRepository;

    @Test
    void createPatient_shouldRejectDuplicatePhone() {
        PatientServiceImpl service = new PatientServiceImpl(patientRepository);
        PatientRequest request = new PatientRequest("Test Patient", "0812345678", PatientType.DIRECT_PAY, null, null, "0811111111");
        when(patientRepository.existsByPhone(request.getPhone())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.createPatient(request));
        verify(patientRepository, never()).save(any());
    }

    @Test
    void createPatient_shouldSaveNewPatient() {
        PatientServiceImpl service = new PatientServiceImpl(patientRepository);
        PatientRequest request = new PatientRequest("Test Patient", "0812345678", PatientType.DIRECT_PAY, null, null, "0811111111");
        when(patientRepository.existsByPhone(request.getPhone())).thenReturn(false);
        when(patientRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        var response = service.createPatient(request);
        assertEquals("Test Patient", response.getName());
        ArgumentCaptor<com.clinic.dental_clinic.domain.entity.Patient> captor = ArgumentCaptor.forClass(com.clinic.dental_clinic.domain.entity.Patient.class);
        verify(patientRepository).save(captor.capture());
        assertEquals("0812345678", captor.getValue().getPhone());
    }
}
