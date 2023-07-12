package com.eps.epsspringrestapi.patients.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@SQLDelete(sql = "UPDATE patients SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is null")
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String name;
    private String lastname;
    @Column(unique = true)
    private Long cedula;
    private int age;
    @Column(unique = true)
    private String email;
    private long phone;
    private Date deletedAt = null;

    public Patient() {
    }

    public Patient(Long id, String name, String lastname, long cedula, int age, String email, long phone, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cedula = cedula;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.deletedAt = deletedAt;
    }

    public Patient(String name, String lastname, long cedula, int age, String email, long phone) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

}
