package com.clinic.dental_clinic.service;

import com.clinic.dental_clinic.domain.entity.Dentist;
import com.clinic.dental_clinic.domain.entity.Patient;
import com.clinic.dental_clinic.domain.enums.PatientType;
import com.clinic.dental_clinic.dto.request.AppointmentRequest;
import com.clinic.dental_clinic.repository.AppointmentRepository;
import com.clinic.dental_clinic.repository.DentistRepository;
import com.clinic.dental_clinic.repository.PatientRepository;
import com.clinic.dental_clinic.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    PatientRepository patientRepository;

    @Mock
    DentistRepository dentistRepository;

    @Test
    void create_shouldReturnCreatedAppointment() {

        AppointmentServiceImpl service =
                new AppointmentServiceImpl(
                        appointmentRepository,
                        patientRepository,
                        dentistRepository
                );

        AppointmentRequest request = new AppointmentRequest();
        request.setPatientId(1L);
        request.setDentistId(2L);
        request.setAppointmentDateTime(
                LocalDateTime.now().plusDays(1)
        );
        request.setServiceType("CLEANING");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Patient");
        patient.setCoverageType(PatientType.DIRECT_PAY);

        Dentist dentist = new Dentist();
        dentist.setId(2L);
        dentist.setName("Dentist");

        when(
                appointmentRepository
                        .existsByDentistIdAndAppointmentDateTimeAndStatusNot(
                                2L,
                                request.getAppointmentDateTime(),
                                com.clinic.dental_clinic.domain.enums.QueueStatus.CANCELLED
                        )
        ).thenReturn(false);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(dentistRepository.findById(2L))
                .thenReturn(Optional.of(dentist));

        when(appointmentRepository.save(any()))
                .thenAnswer(invocation -> {
                    var appointment = invocation.getArgument(
                            0,
                            com.clinic.dental_clinic.domain.entity.Appointment.class
                    );

                    appointment.setId(10L);
                    return appointment;
                });

        var response = service.create(request);

        assertEquals(10L, response.getId());
        assertEquals("CLEANING", response.getServiceType());
    }

    @Test
    void create_shouldRejectDentistConflict() {

        AppointmentServiceImpl service =
                new AppointmentServiceImpl(
                        appointmentRepository,
                        patientRepository,
                        dentistRepository
                );

        AppointmentRequest request = new AppointmentRequest();
        request.setPatientId(1L);
        request.setDentistId(2L);
        request.setAppointmentDateTime(
                LocalDateTime.now().plusDays(1)
        );
        request.setServiceType("CLEANING");

        when(
                appointmentRepository
                        .existsByDentistIdAndAppointmentDateTimeAndStatusNot(
                                2L,
                                request.getAppointmentDateTime(),
                                com.clinic.dental_clinic.domain.enums.QueueStatus.CANCELLED
                        )
        ).thenReturn(true);

        assertThrows(
                com.clinic.dental_clinic.exception.ResourceConflictException.class,
                () -> service.create(request)
        );

        verify(
                patientRepository,
                never()
        ).findById(any());
    }
}