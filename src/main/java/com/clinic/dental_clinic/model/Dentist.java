package com.clinic.dental_clinic.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;

    @OneToMany(mappedBy = "dentist")
    private List<AppointmentQueue> queues;

    public Dentist() {
    }

    public Dentist(Long id, String name, String specialization, List<AppointmentQueue> queues) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
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

    public List<AppointmentQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<AppointmentQueue> queues) {
        this.queues = queues;
    }
}
