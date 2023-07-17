package com.eps.epsspringrestapi.appointments.domain;
import com.eps.epsspringrestapi.doctors.domain.Doctor;
import com.eps.epsspringrestapi.patients.domain.Patient;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@SQLDelete(sql = "UPDATE appointments SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @Transient
    private Long patientId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @Transient
    private Long doctorId;

    @Column(nullable = false)
    private int office;

    @Column(nullable = false)
    private String specialty;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime date;
    private LocalDateTime deletedAt = null;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Appointment() {
    }

    public Appointment(Long id, Patient patient, Doctor doctor, int office, String specialty, LocalDateTime date, LocalDateTime deletedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.office = office;
        this.specialty = specialty;
        this.date = date;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Appointment(Long id, Patient patient, Doctor doctor, int office, String specialty, LocalDateTime date, LocalDateTime deletedAt) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.office = office;
        this.specialty = specialty;
        this.date = date;
    }

    public Appointment(Patient patient, Doctor doctor, int office, String specialty, LocalDateTime date) {
        this.patient = patient;
        this.doctor = doctor;
        this.office = office;
        this.specialty = specialty;
        this.date = date;
    }

    public Appointment(Long patientId, Long doctorId, LocalDateTime date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}