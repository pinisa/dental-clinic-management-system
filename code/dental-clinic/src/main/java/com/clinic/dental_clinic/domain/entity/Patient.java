package com.clinic.dental_clinic.domain.entity;

import com.clinic.dental_clinic.domain.enums.PatientType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_type", nullable = false)
    private PatientType coverageType;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private PatientProfile profile;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AppointmentQueue> queues = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TreatmentRecord> treatmentRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    public Patient() {
    }

    public Patient(Long id, String name, String phone, PatientType coverageType, PatientProfile profile, List<AppointmentQueue> queues, List<TreatmentRecord> treatmentRecords) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.coverageType = coverageType;
        this.profile = profile;
        this.queues = queues != null ? queues : new ArrayList<>();
        this.treatmentRecords = treatmentRecords != null ? treatmentRecords : new ArrayList<>();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PatientType getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(PatientType coverageType) {
        this.coverageType = coverageType;
    }

    public PatientProfile getProfile() {
        return profile;
    }

    public void setProfile(PatientProfile profile) {
        this.profile = profile;
    }

    public List<AppointmentQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<AppointmentQueue> queues) {
        this.queues = queues;
    }

    public List<TreatmentRecord> getTreatmentRecords() {
        return treatmentRecords;
    }

    public void setTreatmentRecords(List<TreatmentRecord> treatmentRecords) {
        this.treatmentRecords = treatmentRecords;
    }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}