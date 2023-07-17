package com.eps.epsspringrestapi.appointments.domain;

import com.eps.epsspringrestapi.doctors.domain.Doctor;
import com.eps.epsspringrestapi.patients.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByOrderByDateAsc(Pageable pageable);

    Page<Appointment> getAppointmentBySpecialtyOrderByDateAsc(String specialtyString, Pageable pageable);
    boolean existsAppointmentByDoctorAndDate(Doctor doctor, LocalDateTime date);

    Appointment getAppointmentByDoctorAndDate(Doctor doctor, LocalDateTime date);

    boolean existsAppointmentByPatientAndDate(Patient patient, LocalDateTime date);

    Appointment getAppointmentByPatientAndDate(Patient patient, LocalDateTime date);

    // Get appointments by doctor and sort by date
    Page<Appointment> getAppointmentByDoctorOrderByDateAsc(Doctor doctor, Pageable pageable);

    List<Appointment> getAppointmentByDoctor(Doctor doctor);

    Page<Appointment> getAppointmentByPatientOrderByDateAsc(Patient patient, Pageable pageable);

    List<Appointment> getAppointmentByPatient(Patient patient);
}
