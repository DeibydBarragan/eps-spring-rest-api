package com.eps.epsspringrestapi.doctors.domain;
import com.eps.epsspringrestapi.appointments.domain.Appointment;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE doctors SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private String name;
    @NotNull
    @NotBlank
    @Length(min = 3, max = 100)
    private String lastname;
    @NotNull
    @Min(1)
    @Max(99999999999L)
    @Column(unique = true)
    private Long cedula;
    @NotNull
    @NotBlank
    @Pattern(regexp = "Medicina general|Cardiología|Medicina interna|Dermatología|Rehabilitación física|Psicología|Odontología|Radiología", message = "Specialty must be one of the following: Medicina general, Cardiología, Medicina interna, Dermatología, Rehabilitación física, Psicología, Odontología, Radiología")
    private String specialty;
    @Min(100)
    @Max(999)
    private int office;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @Min(1000000000)
    @Max(9999999999L)
    private long phone;
    private Date deletedAt = null;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public Doctor() {
    }

    public Doctor(Long id,@NotNull String name, @NotNull String lastname, long cedula, @NotNull String specialty, int office, @NotNull String email, long phone, Date deletedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cedula = cedula;
        this.specialty = specialty;
        this.office = office;
        this.email = email;
        this.phone = phone;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Doctor(@NotNull String name, @NotNull String lastname, long cedula, @NotNull String specialty, int office, @NotNull String email, long phone) {
        this.name = name;
        this.lastname = lastname;
        this.cedula = cedula;
        this.specialty = specialty;
        this.office = office;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getLastname() {
        return lastname;
    }

    public void setLastname(@NotNull String lastName) {
        this.lastname = lastName;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Date isDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public @NotNull String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(@NotNull String specialty) {
        this.specialty = specialty;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}