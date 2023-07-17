package com.eps.epsspringrestapi.patients.domain;
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
@SQLDelete(sql = "UPDATE patients SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Table(name = "patients")
public class Patient {
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
    @Min(0)
    @Max(130)
    private int age;
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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(Long id,@NotNull String name, @NotNull String lastname, long cedula, int age, @NotNull String email, long phone, Date deletedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cedula = cedula;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Patient(@NotNull String name, @NotNull String lastname, long cedula, int age, @NotNull String email, long phone) {
        this.name = name;
        this.lastname = lastname;
        this.cedula = cedula;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
