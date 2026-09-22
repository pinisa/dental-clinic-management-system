package com.clinic.dental_clinic.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String coverageType;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<AppointmentQueue> queues;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<TreatmentRecord> treatmentRecords;

    public Patient() {
    }

    public Patient(Long id, String name, String phone, String coverageType, List<AppointmentQueue> queues, List<TreatmentRecord> treatmentRecords) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.coverageType = coverageType;
        this.queues = queues;
        this.treatmentRecords = treatmentRecords;
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

    public String getCoverageType() {
        return coverageType;
    }

    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
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
}