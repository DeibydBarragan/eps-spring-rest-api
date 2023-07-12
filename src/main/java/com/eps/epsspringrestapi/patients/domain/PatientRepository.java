package com.eps.epsspringrestapi.patients.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findPatientByCedula(Long cedula);
    Optional<Patient> findPatientByEmail(String email);
}
