package com.eps.epsspringrestapi.appointments.application;

import com.eps.epsspringrestapi.appointments.domain.Appointment;
import com.eps.epsspringrestapi.appointments.domain.AppointmentRepository;
import com.eps.epsspringrestapi.doctors.domain.Doctor;
import com.eps.epsspringrestapi.doctors.domain.DoctorRepository;
import com.eps.epsspringrestapi.patients.domain.Patient;
import com.eps.epsspringrestapi.patients.domain.PatientRepository;
import com.eps.epsspringrestapi.appointments.infraestructure.DateValidator;
import com.eps.epsspringrestapi.utils.ResponseBuilder;
import com.eps.epsspringrestapi.utils.SpecialtyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository){
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    // Get all appointments
    public Page<Appointment> getAppointments(Pageable pageable){
        return appointmentRepository.findAllByOrderByDateAsc(pageable);
    }

    // Filter appointments by specialty
    public Object filterAppointmentsBySpecialty(Integer specialty, Pageable pageable) {
        if(specialty == null){
            return appointmentRepository.findAllByOrderByDateAsc(pageable);
        } else if(!SpecialtyUtil.isValidSpecialty(specialty)) {
            return new ResponseBuilder(
                    true,
                    "specialty",
                    "Specialty " + specialty + " does not exist",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        String specialtyString = SpecialtyUtil.getSpecialty(specialty);
        return appointmentRepository.getAppointmentBySpecialtyOrderByDateAsc(specialtyString, pageable);
    }

    // Get appointments by doctor cedula
    public Object getAppointmentsByDoctorCedula(Pageable pageable, Long cedula){
        // Check if doctor exists
        Optional<Doctor> doctor = doctorRepository.findByCedula(cedula);
        if(doctor.isPresent()) {
            return appointmentRepository.getAppointmentByDoctorOrderByDateAsc(doctor.get(), pageable);
        } else {
            return new ResponseBuilder(
                    true,
                    "doctor",
                    "Doctor with cedula " + cedula + " does not exist",
                    HttpStatus.NOT_FOUND
            ).send();
        }
    }

    // Get appointments by patient cedula
    public Object getAppointmentsByPatientCedula(Pageable pageable, Long cedula){
        // Check if patient exists
        Optional<Patient> patient = patientRepository.findByCedula(cedula);
        if(patient.isPresent()) {
            return appointmentRepository.getAppointmentByPatientOrderByDateAsc(patient.get(), pageable);
        } else {
            return new ResponseBuilder(
                    true,
                    "patient",
                    "Patient with cedula " + cedula + " does not exist",
                    HttpStatus.NOT_FOUND
            ).send();
        }
    }

    public ResponseEntity<Object> createAppointment(Appointment appointment){
        // Validate date
        if(appointment.getDate() == null){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date is required",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is valid format
        if(!DateValidator.isValidFormat(appointment.getDate().toString())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be in format yyyy-MM-dd HH:mm:ss",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is in the future
        if(!DateValidator.isDateInFuture(appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be in the future",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is in 30 minutes interval
        if(!DateValidator.isTimeIn30MinutesInterval(appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be in 30 minutes interval",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is in next 6 months
        if(!DateValidator.isInNext6Months(appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be before 6 months",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if doctor exists
        Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctorId());
        if(doctor.isEmpty()){
            return new ResponseBuilder(
                    true,
                    "doctorId",
                    "Doctor not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        // Check if patient exists
        Optional<Patient> patient = patientRepository.findById(appointment.getPatientId());
        if(patient.isEmpty()) {
            return new ResponseBuilder(
                    true,
                    "patientId",
                    "Patient not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        // Check if doctor has is available at this date
        if(appointmentRepository.getAppointmentByDoctor(doctor.get()).size() >= 15){
            return new ResponseBuilder(
                    true,
                    "doctorId",
                    "Doctor is not available at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if doctor has an appointment at this date
        if(appointmentRepository.existsAppointmentByDoctorAndDate(doctor.get(), appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Doctor has an appointment at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if patient is available at this date
        if(appointmentRepository.getAppointmentByPatient(patient.get()).size() >= 10){
            return new ResponseBuilder(
                    true,
                    "patientId",
                    "Patient is not available at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if patient has an appointment at this date
        if(appointmentRepository.existsAppointmentByPatientAndDate(patient.get(), appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Patient already has an appointment at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }

        appointment.setDoctor(doctor.get());
        appointment.setOffice(doctor.get().getOffice());
        appointment.setSpecialty(doctor.get().getSpecialty());
        appointment.setPatient(patient.get());
        appointment = appointmentRepository.save(appointment);
        return new ResponseBuilder(
                appointment,
                "Appointment created successfully",
                HttpStatus.CREATED
        ).send();
    }

    public Object updateAppointment(Appointment appointment, Long appointmentId){
        // Check if appointment exists
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if(appointmentOptional.isEmpty()) {
            return new ResponseBuilder(
                    true,
                    "appointmentId",
                    "Appointment not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        // Validate date
        if(appointment.getDate() == null){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date is required",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is valid format
        if(!DateValidator.isValidFormat(appointment.getDate().toString())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be in format yyyy-MM-dd HH:mm:ss",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is in the future
        if(!DateValidator.isDateInFuture(appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be in the future",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is in 30 minutes interval
        if(!DateValidator.isTimeIn30MinutesInterval(appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be in 30 minutes interval",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if date is in next 6 months
        if(!DateValidator.isInNext6Months(appointment.getDate())){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Date must be before 6 months",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if doctor exists
        Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctorId());
        if(doctor.isEmpty()){
            return new ResponseBuilder(
                    true,
                    "doctorId",
                    "Doctor not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        // Check if patient exists
        Optional<Patient> patient = patientRepository.findById(appointment.getPatientId());
        if(patient.isEmpty()) {
            return new ResponseBuilder(
                    true,
                    "patientId",
                    "Patient not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        // Check if doctor has is available at this date
        if(appointmentRepository.getAppointmentByDoctor(doctor.get()).size() >= 15){
            return new ResponseBuilder(
                    true,
                    "doctorId",
                    "Doctor is not available at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if doctor has an appointment at this date
        if(appointmentRepository.existsAppointmentByDoctorAndDate(doctor.get(), appointment.getDate())
            && !appointmentRepository.getAppointmentByDoctorAndDate(doctor.get(), appointment.getDate()).getId().equals(appointmentId)){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Doctor has an appointment at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if patient is available at this date
        if(appointmentRepository.getAppointmentByPatient(patient.get()).size() >= 10){
            return new ResponseBuilder(
                    true,
                    "patientId",
                    "Patient is not available at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }
        // Check if patient has an appointment at this date
        if(appointmentRepository.existsAppointmentByPatientAndDate(patient.get(), appointment.getDate())
            && !appointmentRepository.getAppointmentByPatientAndDate(patient.get(), appointment.getDate()).getId().equals(appointmentId)){
            return new ResponseBuilder(
                    true,
                    "date",
                    "Patient already has an appointment at this date",
                    HttpStatus.UNPROCESSABLE_ENTITY
            ).send();
        }

        Appointment appointmentToUpdate = appointmentOptional.get();
        appointmentToUpdate.setDoctor(doctor.get());
        appointmentToUpdate.setOffice(doctor.get().getOffice());
        appointmentToUpdate.setSpecialty(doctor.get().getSpecialty());
        appointmentToUpdate.setPatient(patient.get());
        appointmentToUpdate.setDate(appointment.getDate());
        appointmentToUpdate = appointmentRepository.save(appointmentToUpdate);
        return new ResponseBuilder(
                appointmentToUpdate,
                "Appointment updated successfully",
                HttpStatus.CREATED
        ).send();
    }

    public ResponseEntity<Object> deleteAppointment(Long appointmentId){
        boolean res = appointmentRepository.existsById(appointmentId);
        if(!res){
            return new ResponseBuilder(
                    true,
                    "appointmentId",
                    "Appointment not found",
                    HttpStatus.NOT_FOUND
            ).send();
        }
        appointmentRepository.deleteById(appointmentId);
        return new ResponseBuilder(
                "Appointment deleted",
                HttpStatus.ACCEPTED
        ).send();
    }
}
