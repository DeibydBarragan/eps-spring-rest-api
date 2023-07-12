package com.eps.epsspringrestapi.doctors.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findDoctorByCedula(Long cedula);
    Optional<Doctor> findDoctorByEmail(String email);

    List<Doctor> findDoctorsBySpecialty(String specialty);
}
