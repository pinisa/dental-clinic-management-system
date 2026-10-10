package com.clinic.dental_clinic.domain.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dentist")
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String name;
    private String specialization;

    @Column(name = "license_number")
    private String licenseNumber;

    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AppointmentQueue> queues;

    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments;

    public Dentist() {
    }

    public Dentist(Long id, String name, String specialization, String licenseNumber, List<AppointmentQueue> queues) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.queues = queues;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public List<AppointmentQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<AppointmentQueue> queues) {
        this.queues = queues;
    }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}